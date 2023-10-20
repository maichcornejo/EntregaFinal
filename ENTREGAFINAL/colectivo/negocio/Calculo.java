package negocio;

import java.util.ArrayList;
import java.util.List;

import aplicacion.Constantes;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;
import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.GraphAlgorithms;
import net.datastructures.Map;
import net.datastructures.Position;
import net.datastructures.PositionalList;
import net.datastructures.ProbeHashMap;
import net.datastructures.TreeMap;
import net.datastructures.Vertex;

public class Calculo {

	private static final int CAMBIO_LINEA = 10000;
	private Graph<ParadaLinea, Integer> red;
	private TreeMap<String, Vertex<ParadaLinea>> vertices;

	private TreeMap<String, Linea> lineaMap;
	private TreeMap<Integer, Parada> paradaMap;
	private TreeMap<String, Tramo> tramoMap;

	public Calculo(TreeMap<Integer, Parada> paradaMap, TreeMap<String, Linea> lineaMap, List<Tramo> tramos) {
	
		// Map parada
		this.paradaMap = paradaMap;
		
		// Map Linea
		this.lineaMap = lineaMap;
		
		// Map tramo
		tramoMap = new TreeMap<String, Tramo>();
		for (Tramo t : tramos)
			if(t!=null) {
			tramoMap.put(t.getParadaInicio().getId() + "-" + t.getParadaFin().getId(), t);
			}

		// Map paradaLinea
		TreeMap<String, ParadaLinea> paradaLinea = new TreeMap<String, ParadaLinea>();
		for (Parada p : paradaMap.values())
			for (Linea l : p.getLineas())
				paradaLinea.put(p.getId() + l.getNombre(), new ParadaLinea(p.getId(), l.getNombre()));

		// Cargar paradas caminando
		for (Tramo t : tramos)
			if (t.getTipo() == Constantes.TRAMO_CAMINANDO) {
				paradaLinea.put(t.getParadaInicio().getId() + "", new ParadaLinea(t.getParadaInicio().getId(), ""));
				paradaLinea.put(t.getParadaFin().getId() + "", new ParadaLinea(t.getParadaFin().getId(), ""));
			}

		red = new AdjacencyMapGraph<>(true);

		// Cargar paradas
		vertices = new TreeMap<String, Vertex<ParadaLinea>>();
		for (Entry<String, ParadaLinea> pl : paradaLinea.entrySet())
			vertices.put(pl.getKey(), red.insertVertex(pl.getValue()));

		// Cargar tramos
		Parada origen, destino;
		for (Linea l : lineaMap.values()) {
			for (int i = 0; i < l.getParadasIda().size() - 1; i++) {
				origen = l.getParadasIda().get(i);
				destino = l.getParadasIda().get(i + 1);
				red.insertEdge(vertices.get(origen.getId() + l.getNombre()),
						vertices.get(destino.getId() + l.getNombre()),
						tramoMap.get(origen.getId() + "-" + destino.getId()).getTiempo());
			}
			for (int i = 0; i < l.getParadasRegreso().size() - 1; i++) {
				origen = l.getParadasRegreso().get(i);
				destino = l.getParadasRegreso().get(i + 1);
				red.insertEdge(vertices.get(origen.getId() + l.getNombre()),
						vertices.get(destino.getId() + l.getNombre()),
						tramoMap.get(origen.getId() + "-" + destino.getId()).getTiempo());
			}
		}

		// Cargar cambio de linea
		for (Parada p : paradaMap.values())
			for (Linea ori : p.getLineas())
				for (Linea des : p.getLineas())
					if (!ori.equals(des))
						if (red.getEdge(vertices.get(p.getId() + ori.getNombre()),
								vertices.get(p.getId() + des.getNombre())) == null)
							red.insertEdge(vertices.get(p.getId() + ori.getNombre()),
									vertices.get(p.getId() + des.getNombre()), CAMBIO_LINEA);

		// Cargar tramos caminando
		for (Tramo t : tramos)
			if (t.getTipo() == Constantes.TRAMO_CAMINANDO) {
				red.insertEdge(vertices.get(t.getParadaInicio().getId() + ""), vertices.get(t.getParadaFin().getId() + ""),
						t.getTiempo());
				for (Linea ori : t.getParadaInicio().getLineas()) {
					red.insertEdge(vertices.get(t.getParadaInicio().getId() + ""),
							vertices.get(t.getParadaInicio().getId() + ori.getNombre()), CAMBIO_LINEA);
					red.insertEdge(vertices.get(t.getParadaInicio().getId() + ori.getNombre()),
							vertices.get(t.getParadaInicio().getId() + ""), 0);
				}
				for (Linea des : t.getParadaFin().getLineas()) {
					red.insertEdge(vertices.get(t.getParadaFin().getId() + ""),
							vertices.get(t.getParadaFin().getId() + des.getNombre()), CAMBIO_LINEA);
					red.insertEdge(vertices.get(t.getParadaFin().getId() + des.getNombre()),
							vertices.get(t.getParadaFin().getId() + ""), 0);
				}
			}
	}

	public List<Tramo> masRapido(Parada paradaOrigen, Parada paradaDestino) {
		// copia grafo
		Graph<ParadaLinea, Integer> copia = new AdjacencyMapGraph<>(true);
		Map<ParadaLinea, Vertex<ParadaLinea>> res = new ProbeHashMap<>();

		for (Vertex<ParadaLinea> result : red.vertices())
			res.put(result.getElement(), copia.insertVertex(result.getElement()));

		Vertex<ParadaLinea>[] vert;

		for (Edge<Integer> result : red.edges()) {
			vert = red.endVertices(result);
			copia.insertEdge(res.get(vert[0].getElement()), res.get(vert[1].getElement()), result.getElement());
		}

		// Agregar vertice inicio y fin
		ParadaLinea plOrigen = new ParadaLinea(0, "Inicio");
		Vertex<ParadaLinea> origen = copia.insertVertex(plOrigen);
		res.put(plOrigen, origen);
		for (Linea l : paradaOrigen.getLineas())
			if (copia.getEdge(origen, res.get(vertices.get(paradaOrigen.getId() + l.getNombre()).getElement())) == null)
				copia.insertEdge(origen, res.get(vertices.get(paradaOrigen.getId() + l.getNombre()).getElement()), 0);

		ParadaLinea plDestino = new ParadaLinea(0, "Fin");
		Vertex<ParadaLinea> destino = copia.insertVertex(plDestino);
		res.put(plDestino, destino);
		for (Linea l : paradaDestino.getLineas())
			if (copia.getEdge(res.get(vertices.get(paradaDestino.getId() + l.getNombre()).getElement()), destino) == null)
				copia.insertEdge(res.get(vertices.get(paradaDestino.getId() + l.getNombre()).getElement()), destino, 0);

		// Calcular camino mas corto
		PositionalList<Vertex<ParadaLinea>> lista = GraphAlgorithms.shortestPathList(copia, origen, destino);

		List<Tramo> tramos = new ArrayList<Tramo>();
		List<Integer> tiempos = new ArrayList<Integer>();
		List<Integer> paradas = new ArrayList<Integer>();
		List<String> lineas = new ArrayList<String>();

		Vertex<ParadaLinea> v1, v2 = null;
		Position<Vertex<ParadaLinea>> aux = lista.first();
		while (lista.after(aux) != null) {
			v1 = aux.getElement();
			aux = lista.after(aux);
			v2 = aux.getElement();
			tiempos.add(copia.getEdge(res.get(v1.getElement()), res.get(v2.getElement())).getElement());
			paradas.add(v1.getElement().getParada());
			lineas.add(v1.getElement().getLinea());

		}

		// System.out.println(copia);
		// System.out.println(tiempos);
		// System.out.println(paradas);
		// System.out.println(lineas);

		Tramo t;
		TreeMap<Integer, Parada> pMap = new TreeMap<Integer, Parada>();
		for (int i = 1; i < paradas.size(); i++) {
			if (pMap.get(paradas.get(i)) == null) {
				Parada p = paradaMap.get(paradas.get(i));
				pMap.put(p.getId(), new Parada(p.getId(), p.getDireccion()));
			}
			pMap.get(paradas.get(i)).agregarLinea(lineaMap.get((lineas.get(i))));
		}
		for (int i = 1; i < paradas.size() - 1; i++)
			if ((t = tramoMap.get(paradas.get(i) + "-" + paradas.get(i + 1))) != null)
				tramos.add(
						new Tramo(pMap.get(paradas.get(i)), pMap.get(paradas.get(i + 1)), t.getTipo(), t.getTiempo()));

		return tramos;
	}

	private class ParadaLinea {
		private int parada;
		private String linea;

		public ParadaLinea(int parada, String linea) {
			this.parada = parada;
			this.linea = linea;
		}

		public int getParada() {
			return parada;
		}

		public String getLinea() {
			return linea;
		}

		@Override
		public String toString() {
			return parada + linea;
		}

	}
}
