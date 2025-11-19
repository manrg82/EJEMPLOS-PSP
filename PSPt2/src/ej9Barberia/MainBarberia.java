package ej9Barberia;

public class MainBarberia {
    public static void main(String[] args) throws InterruptedException {
        int numSillas = 10;
        int numBarberos = 5;
        int numClientes = 50;

        GestorBarberia gestor = new GestorBarberia(numSillas);

        Barbero[] barberos = new Barbero[numBarberos];
        for (int i = 0; i < numBarberos; i++) {
            barberos[i] = new Barbero(gestor, i + 1);
            barberos[i].start();
        }

        Thread[] clientes = new Thread[numClientes];
        for (int i = 0; i < numClientes; i++) {
            clientes[i] = new Cliente(gestor, i + 1);
            clientes[i].start();
            Thread.sleep(50);
        }

        for (Thread c : clientes) {
            c.join();
        }

        System.out.println("Se han ido todos los clientes.");
        gestor.cerrar();
        System.out.println("Barberia cerrada.");
    }
}
