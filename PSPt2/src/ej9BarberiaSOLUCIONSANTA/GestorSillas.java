package ej9BarberiaSOLUCIONSANTA;

import java.util.ArrayList;
import java.util.List;

public class GestorSillas {
	private final static int NUM_SILLAS = 10;
	private List<Silla> sillas = new ArrayList<Silla>(NUM_SILLAS);
	
	public GestorSillas() {
		//crear inicialmente todas las sillas libres
		for (int i = 0; i < NUM_SILLAS; i++) {
			sillas.add(new Silla(i+1));
		}
	}
	
	/**
	 * Buscar la primera silla libre
	 * @return devuelve la primera silla libre
	 * null si no hay sillas libres 
	 */
	public synchronized Silla getSillaLibre(String nombreCliente) {
		Silla silla = null;
		boolean booContinuar = true;
		for(int i=0;(booContinuar && (i< sillas.size())); i++) {
			if (!sillas.get(i).isBooOcupada()) {
				silla = sillas.get(i);
				//sentar cliente
				silla.setBooOcupada(nombreCliente);
				booContinuar = false;
			}
		}
		return silla;
	}

	/**
	 * Buscar la primera silla ocupada
	 * @return devuelve la primera silla ocupada
	 * null si no hay sillas ocupadas 
	 */
	public synchronized Silla getSillaOcupada(String barbero) {
		Silla silla = null;
		boolean booContinuar = true;
		for(int i=0;(booContinuar && (i< sillas.size())); i++) {
			if (sillas.get(i).isBooOcupada() && !sillas.get(i).hayBarbero()) {
				silla = sillas.get(i);
				silla.setAtendidaBarbero(barbero);
				booContinuar = false;
			}
		}
		return silla;
	}	
}