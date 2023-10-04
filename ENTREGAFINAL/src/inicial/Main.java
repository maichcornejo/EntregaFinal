package inicial;

import java.util.*;

//Clase para representar una parada de colectivo
class Parada {
	private String nombre;

	public Parada(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}
}

//Clase para representar un tramo entre paradas
class Tramo {
	private Parada inicio;
	private Parada destino;
	private int distancia;

	public Tramo(Parada inicio, Parada destino, int distancia) {
		this.inicio = inicio;
		this.destino = destino;
		this.distancia = distancia;
	}

	public Parada getInicio() {
		return inicio;
	}

	public Parada getDestino() {
		return destino;
	}

	public int getDistancia() {
		return distancia;
	}
}

//Clase para representar la red de colectivos como un grafo
class RedColectivos {
	private Map<Parada, List<Tramo>> grafo;

	public RedColectivos() {
		grafo = new HashMap<>();
	}

	// Agregar una parada al grafo
	public void agregarParada(Parada parada) {
		grafo.put(parada, new ArrayList<>());
	}

	// Agregar un tramo entre paradas al grafo
	public void agregarTramo(Parada inicio, Parada destino, int distancia) {
		List<Tramo> tramos = grafo.get(inicio);
		tramos.add(new Tramo(inicio, destino, distancia));
		grafo.put(inicio, tramos);
	}

	// Obtener los tramos desde una parada
	public List<Tramo> obtenerTramosDesdeParada(Parada parada) {
		return grafo.getOrDefault(parada, new ArrayList<>());
	}
}

public class Main {
	public static void main(String[] args) {
		// Crear una instancia de la red de colectivos
		RedColectivos red = new RedColectivos();

		// Agregar paradas al grafo
		Parada paradaA = new Parada("Parada A");
		Parada paradaB = new Parada("Parada B");
		Parada paradaC = new Parada("Parada C");
		red.agregarParada(paradaA);
		red.agregarParada(paradaB);
		red.agregarParada(paradaC);

		// Agregar tramos entre paradas
		red.agregarTramo(paradaA, paradaB, 5);
		red.agregarTramo(paradaB, paradaC, 7);

		// Consultar tramos desde una parada
		List<Tramo> tramosDesdeParadaA = red.obtenerTramosDesdeParada(paradaA);
		System.out.println("Tramos desde Parada A:");
		for (Tramo tramo : tramosDesdeParadaA) {
			System.out.println("Destino: " + tramo.getDestino() + ", Distancia: " + tramo.getDistancia());
		}
	}
}
