package modelo;

import java.util.Objects;

public class Tramo {

	private Parada inicio;
	private Parada fin;
	private int tiempo;
	private int tipo;
	
	public Tramo(Parada inicio, Parada fin, int tiempo, int tipo) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.tiempo = tiempo;
		this.tipo = tipo;
	}

	public Parada getParadaInicio() {
		return inicio;
	}

	public void setParadaInicio(Parada inicio) {
		this.inicio = inicio;
	}

	public Parada getParadaFin() {
		return fin;
	}

	public void setParadaFin(Parada fin) {
		this.fin = fin;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(fin, inicio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tramo other = (Tramo) obj;
		return Objects.equals(fin, other.fin) && Objects.equals(inicio, other.inicio);
	}

	@Override
	public String toString() {
		return "Tramo [parada inicial=" + inicio + ", parada final=" + fin
				+ ", tiempo=" + tiempo + ", tipo=" + tipo + "]";
	}
	
	
}
