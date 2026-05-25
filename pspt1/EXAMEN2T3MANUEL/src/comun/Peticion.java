package comun;

import java.io.Serializable;

public class Peticion implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tipo;
	private String evento;
	private int numEntradas;
	public Peticion(String tipo) {
		this.tipo=tipo;
	}
	public Peticion(String tipo, String evento, int numEntradas) {
		this.tipo=tipo;
		this.evento=evento;
		this.numEntradas=numEntradas;
	}
	public String getTipo() {return tipo;}
	public String getEvento(){return evento;}
	public int getNumEntradas() {return numEntradas;}
	@Override
	public String toString() {
		if(tipo.equals("EVENTOS")) {
			return("Peticion[]tipo=EVENTOS");
		}
		return "Peticion[tipo=ENTRADAS, Entradas[evento ="+evento+", numEntradas= "+numEntradas+"]]";
	}
	
	
}
