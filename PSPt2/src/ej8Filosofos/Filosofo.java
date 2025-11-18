package ej8Filosofos;

import java.util.Random;

class Filosofo extends Thread {
	 private int id;
	 private Mesa mesa;
	 private Random random = new Random();

	 public Filosofo(int id, Mesa mesa) {
	     this.id = id;
	     this.mesa = mesa;
	 }

	 @Override
	 public void run() {
	     try {
	         while (true) {
	             System.out.println("El filósofo " + id + " está pensando.");
	             Thread.sleep(random.nextInt(4000) + 1000);
	             mesa.cogerCubiertos(id);
	             System.out.println("Filosofo " + id + " está comiendo.");
	             Thread.sleep(2000);
	             mesa.soltarCubiertos(id);
	         }
	     } catch (InterruptedException e) {
	         System.out.println("Filósofo " + id + " interrumpido.");
	     }
	 }
}
