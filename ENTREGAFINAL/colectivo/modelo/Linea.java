package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	public List<Parada> getParadasIda() {
		return new ArrayList<>(paradasIda);
	}

	public List<Parada> getParadasRegreso() {
		return new ArrayList<>(paradasRegreso);
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
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linea other = (Linea) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Linea{" + "nombre='" + nombre + '\'' + ", paradasIda=" + paradasIda + ", paradasRegreso="
				+ paradasRegreso + '}';
	}
}
