package ej5Factura;
import java.util.Random;

public class Empleado implements Runnable {

    private int id;
    private Tarifa tarifa;
    private int facturasGeneradas = 0;
    private Random rand = new Random();

    public Empleado(int id, Tarifa tarifa) {
        this.id = id;
        this.tarifa = tarifa;
    }

    public int getFacturasGeneradas() {
        return facturasGeneradas;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                double consumo = 1 + (rand.nextDouble() * 49);
                Factura factura = tarifa.generarFactura(consumo);
                facturasGeneradas++;

                System.out.printf("Factura %s con importe %.2f€ generada por el Empleado %d.\n",
                        factura.getNumero(), factura.getImporte(), this.id);

                Thread.sleep(this.id * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Empleado " + this.id + " termina su turno.");
        }
    }
}