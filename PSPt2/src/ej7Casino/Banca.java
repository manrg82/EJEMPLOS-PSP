package ej7Casino;

import java.util.Random;

class Banca {
	private static final int SALDO_INICIAL = 5000;
    private int saldo;
    private int numeroGanador;
    
    public Banca() {
        this.saldo = SALDO_INICIAL;
    }
    
    public synchronized void indicarNumeroGanador(int numeroGanador) {
    	this.numeroGanador = numeroGanador;
    	notifyAll();
    }

    public synchronized void recibirApuesta(int cantidad) {
        saldo = saldo + cantidad;
    }

    public synchronized int getSaldo() {
        return saldo;
    }
    
    public synchronized boolean pagar(int cantidad) {
		boolean booPagar = false;
        // La banca solo paga si tiene suficiente dinero
        if ((saldo - cantidad) >= 0) {
            saldo = saldo - cantidad;
            booPagar= true;
        }
        return booPagar;
    }
    
    public synchronized void comprobarResultado(Jugador jugador) throws InterruptedException {
    	//esperar al croupier para comprobar si ha ganado
    	this.wait();
    	
    	if (jugador.getNumeroApostado() == numeroGanador) {
            // Si el jugador gana, recibe 5 veces lo apostado
            int ganancia = Jugador.APUESTA * 5;
            if (pagar(ganancia)) {
            	jugador.incrementarSaldo(ganancia);
            	System.out.println("+++"+jugador.getName() + " ha ganado! Su saldo ahora es " + jugador.getSaldo()+" (saldo banca:"+getSaldo()+")");
            } else {
                System.out.println("La banca no tiene suficiente dinero para pagar "+ganancia+" a " + Thread.currentThread().getName());
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " ha perdido! Su saldo es " + jugador.getSaldo());
        }
    }
    
    
    
    
}

