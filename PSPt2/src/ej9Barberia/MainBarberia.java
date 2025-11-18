package ej9Barberia;

public class MainBarberia {
    public static void main(String[] args) {
        final int NUM_BARBEROS = 5;
        final int NUM_SILLAS = 10;
        final int NUM_CLIENTES = 50;

        GestorBarberia gestor = new GestorBarberia(NUM_SILLAS);
        Barbero[] barberos = new Barbero[NUM_BARBEROS];
        for (int i = 0; i < NUM_BARBEROS; i++) {
            barberos[i] = new Barbero(gestor, (i + 1));
            barberos[i].start();
        }
        Thread[] clientes = new Thread[NUM_CLIENTES];
        for (int i = 0; i < NUM_CLIENTES; i++) {
            clientes[i] = new Cliente(gestor, (i + 1));
            clientes[i].start();
            try {
                Thread.sleep((long)(Math.random() * 100));
            } catch (InterruptedException e) {}
        }
        for (int i = 0; i < NUM_CLIENTES; i++) {
            try {
                clientes[i].join();
            } catch (InterruptedException e) {}
        }

        System.out.println("Se han ido todos los clientes.");
        for (int i = 0; i < NUM_BARBEROS; i++) {
            barberos[i].interrupt(); 
        }
        System.out.println("Barberia cerrada.");
    }
}
