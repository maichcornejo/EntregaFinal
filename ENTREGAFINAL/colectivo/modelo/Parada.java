package modelo;

import java.util.ArrayList;
import java.util.List;

public class Parada {

	private String id;
	private String direccion;
	private List<Linea> lineas;

	public Parada (String id, String direccion) {
		super();
		this.id = id;
		this.direccion = direccion;
		lineas = new ArrayList<Linea>();
	}

	public void agregarLinea(Linea linea) {
		lineas.add(linea);
	}

	public String getCodigo() {
		return id;
	}

	public void setCodigo(String id) {
		this.id = id;
	}

	public String getNombre() {
		return direccion;
	}

	public void setNombre(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Parada [id=" + id + ", direccion=" + direccion + "]";
	}

}
