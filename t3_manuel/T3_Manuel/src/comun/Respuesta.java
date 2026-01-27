package comun;

import java.util.ArrayList;

public class Respuesta {
	private boolean isPeticionLista;
	private String nmEvento;
	private int nmEntradas;
	public ArrayList<String> lista;
	public Respuesta(boolean tipo, String evento, int nmEntradas) {
		this.isPeticionLista=tipo;
		this.nmEntradas=nmEntradas;
		this.nmEvento=evento;
	}
	public Respuesta(boolean tipo, ArrayList<String> lista) {
		this.isPeticionLista=tipo;
		this.lista=lista;
	}
	public String getNmEvento() {
		return nmEvento;
	}
	public int getNmEntradas() {
		return nmEntradas;
	}
}
