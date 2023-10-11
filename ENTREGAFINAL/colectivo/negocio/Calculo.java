package negocio;

import java.util.ArrayList;
import java.util.List;

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
import modelo.Parada;
import modelo.Tramo;

public class Calculo {

	private Graph<Parada, Tramo> colectivo;
	private TreeMap<String, Vertex<Parada>> vertices;

	public Calculo(TreeMap<String, Parada> paradas, List<Tramo> tramos) {

		colectivo = new AdjacencyMapGraph<>(false);

		// Cargar paradas
		vertices = new TreeMap<String, Vertex<Parada>>();
		for (Entry<String, Parada> parada : paradas.entrySet())
			vertices.put(parada.getKey(), colectivo.insertVertex(parada.getValue()));

		// Cargar tramos
		for (Tramo tramo : tramos)
			colectivo.insertEdge(vertices.get(tramo.getParadaInicio().getCodigo()),
					vertices.get(tramo.getParadaFin().getCodigo()), tramo);
	}

	public List<Tramo> rapido(Parada paradaInicio, Parada paradaFin) {
		// copia grafo
		Graph<Parada, Integer> rapido = new AdjacencyMapGraph<>(false);
		Map<Parada, Vertex<Parada>> res = new ProbeHashMap<>();

		for (Vertex<Parada> result : colectivo.vertices())
			res.put(result.getElement(), rapido.insertVertex(result.getElement()));

		Vertex<Parada>[] vert;

		for (Edge<Tramo> result : colectivo.edges()) {
			vert = colectivo.endVertices(result);
			rapido.insertEdge(res.get(vert[0].getElement()), res.get(vert[1].getElement()),
					result.getElement().getTiempo());
		}
		PositionalList<Vertex<Parada>> lista = GraphAlgorithms.shortestPathList(rapido, res.get(paradaInicio),
				res.get(paradaFin));

		List<Tramo> tramos = new ArrayList<Tramo>();

		Vertex<Parada> v1, v2;
		Position<Vertex<Parada>> aux = lista.first();
		while (lista.after(aux) != null) {
			v1 = aux.getElement();
			aux = lista.after(aux);
			v2 = aux.getElement();
			tramos.add(
					colectivo.getEdge(vertices.get(v1.getElement().getCodigo()), vertices.get(v2.getElement().getCodigo()))
							.getElement());
		}
		return tramos;
	}

}
