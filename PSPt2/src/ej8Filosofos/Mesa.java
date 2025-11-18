package ej8Filosofos;

import java.util.Random;

class Mesa {
 private boolean[] tenedores; 

 public Mesa(int numFilosofos) {
     tenedores = new boolean[numFilosofos];
 }
 public synchronized void cogerCubiertos(int idFilosofo) throws InterruptedException {
     int izq = idFilosofo;
     int der = (idFilosofo + 1) % tenedores.length;

     while (tenedores[izq] || tenedores[der]) {
         wait(); 
     }
     tenedores[izq] = true;
     tenedores[der] = true;

}
 public synchronized void soltarCubiertos(int idFilosofo) {
	    int izq = idFilosofo;
	    int der = (idFilosofo + 1) % tenedores.length;

	    tenedores[izq] = false;
	    tenedores[der] = false;
	    notifyAll(); 
	}

 public static void main(String[] args) {
     int numFilosofos = 5;
     Mesa mesa = new Mesa(numFilosofos);
     for (int i = 0; i < numFilosofos; i++) {
         new Filosofo(i, mesa).start();
     }
 }
}