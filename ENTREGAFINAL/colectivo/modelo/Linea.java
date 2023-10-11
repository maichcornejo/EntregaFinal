package modelo;

import java.util.ArrayList;
import java.util.List;



public class Linea {

	private String nombre;
	private List<Parada> paradasIda;
	private List<Parada> paradasRegreso;

	public Linea(String nombre) {
		this.nombre = nombre;
		this.paradasIda = new ArrayList<>();
		this.paradasRegreso = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setParadasIda(List<Parada> paradasIda) {
		this.paradasIda = paradasIda;
	}

	public void setParadasRegreso(List<Parada> paradasRegreso) {
		this.paradasRegreso = paradasRegreso;
	}

	public void agregarParadasIda(Parada parada) {
		paradasIda.add(parada);
	}

	public void agregarParadasRegreso(Parada parada) {
		paradasRegreso.add(parada);
	}

	@Override
	public String toString() {
		return "Linea{" + "nombre='" + nombre + '\'' + ", paradasIda=" + paradasIda + ", paradasRegreso="
				+ paradasRegreso + '}';
	}
}
