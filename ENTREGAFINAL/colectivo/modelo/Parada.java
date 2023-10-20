package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parada {

	private int id;
	private String direccion;
	private List<Linea> lineas;

	public Parada (int id, String direccion) {
		super();
		this.id = id;
		this.direccion = direccion;
		lineas = new ArrayList<Linea>();
	}

	public void agregarLinea(Linea linea) {
		lineas.add(linea);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return direccion;
	}

	public void setNombre(String direccion) {
		this.direccion = direccion;
	}

	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Parada [id=" + id + ", direccion=" + direccion + "]";
	}
	/*
	 * hacer un get que devuelva un clon
	 */

	public List<Linea> getLineas() {
		return lineas;
	}

	public void setLineas(List<Linea> lineas) {
		this.lineas = lineas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parada other = (Parada) obj;
		return Objects.equals(id, other.id);
	}

	

}
