package comun;

public class Solicitud {
	private boolean isPeticionLista;
	private String nmEvento;
	private int nmEntradas;
	public Solicitud(boolean tipo, String evento, int nmEntradas) {
		this.isPeticionLista=tipo;
		this.nmEntradas=nmEntradas;
		this.nmEvento=evento;
	}
	public boolean getIsLista() {
		return isPeticionLista;
	}
	public String getNmEvento() {
		return nmEvento;
	}
	public int getNmEntradas() {
		return nmEntradas;
	}
}
