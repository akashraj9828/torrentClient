package org.hamisto.filmista;

public class Stagione {
	
	String numero;
	public Stagione(String numero) {
		
		this.numero = numero;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Override
	public String toString() {
		return this.getNumero();
	}
}
