package ej5Factura;
import java.util.ArrayList;
import java.util.List;

public class MainFacturas {

    public static void main(String[] args) {
        Tarifa tarifa = new Tarifa();
        List<Empleado> empleados = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Empleado emp = new Empleado(i, tarifa);
            empleados.add(emp);
            Thread t = new Thread(emp);
            hilos.add(t);
            t.start();
        }

        try {
            Thread.sleep(3 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread t : hilos) {
            t.interrupt();
        }

        try {
            for (Thread t : hilos) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- FIN DE JORNADA ---");
        for (Empleado emp : empleados) {
            System.out.println("El Empleado " + emp.getFacturasGeneradas() + " genero " + emp.getFacturasGeneradas() + " facturas");
        }
    }
}