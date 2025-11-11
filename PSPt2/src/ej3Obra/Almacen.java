package ej3Obra;

public class Almacen {

    private static Almacen instancia;

    private final int CAPACIDAD_MAXIMA = 6000;
    private int stock = 0;

    private Almacen() {
    }

    public static synchronized Almacen getInstancia() {
        if (instancia == null) {
            instancia = new Almacen();
        }
        return instancia;
    }

    public synchronized void guardar(int cantidad) throws InterruptedException {
        while (stock + cantidad > CAPACIDAD_MAXIMA) {
            System.out.println("ALMACEN: No hay espacio para " + cantidad + " ladrillos (Stock: " + stock + "). Fábrica espera.");
            wait();
        }

        stock += cantidad;
        System.out.println("FÁBRICA: Guarda " + cantidad + " ladrillos. Stock actual: " + stock);
        notifyAll();
    }

    public synchronized boolean retirar(int cantidad, String nombreObra) {
        if (stock >= cantidad) {
            stock -= cantidad;
            System.out.println(nombreObra + ": Retira " + cantidad + " ladrillos. Stock actual: " + stock);
            notifyAll();
            return true;
        } else {
            System.out.println(nombreObra + ": INTENTO FALLIDO. Queria " + cantidad + " pero solo hay " + stock + ".");
            return false;
        }
    }
}
