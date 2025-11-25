package ej9BarberiaSOLUCIONSANTA;

public class Silla {
	
	private int numSilla;
	private boolean booOcupada;
	private String nombreCliente;
	private boolean booBarbero;
	private String barbero;
	
	public Silla(int numSilla) {
		this.numSilla = numSilla;
		this.booOcupada = false;
		this.nombreCliente = null;
		this.booBarbero = false;
		this.barbero = null;
	}

	public int getNumSilla() {
		return numSilla;
	}

	public boolean isBooOcupada() {
		return booOcupada;
	}

	public void setBooOcupada(String nombreCliente) {
		this.booOcupada = true;
		this.nombreCliente = nombreCliente;
		System.out.println("El cliente "+nombreCliente
				+ " se ha sentado en la silla: "+ numSilla);
	}
	
	public boolean hayBarbero() {
		return booBarbero;
	}
	
	public String getBarbero() {
		return this.barbero;
	}
	
	public void setAtendidaBarbero(String barbero) {
		this.barbero = barbero;
		this.booBarbero = true;
	}
	
	public void setLibre() {
		System.out.println("El cliente "+nombreCliente
				+ " ha dejado libre la silla: "
				+ numSilla+" que estaba atendiendo "+this.barbero);
		
		this.booOcupada = false; 
		this.nombreCliente = null;
		this.booBarbero = false;
		this.barbero = null;
	}

}
