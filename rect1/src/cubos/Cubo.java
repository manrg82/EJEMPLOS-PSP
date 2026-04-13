package cubos;

public class Cubo {

    private static Cubo instancia = null;
    
    // Capacidad y estado actual
    private final int CAPACIDAD_MAXIMA = 5;
    private int aguaActual = 0;

    private Cubo() {
    }

    public static synchronized Cubo getInstance() {
        if (instancia == null) {
            instancia = new Cubo();
        }
        return instancia;
    }

    /**
     * Echar agua: Solo si coge toda la cantidad sin rebosar.
     */
    public synchronized void echarAgua(int cantidad) {
        if (aguaActual + cantidad <= CAPACIDAD_MAXIMA) {
            aguaActual += cantidad;
            System.out.println("Se han añadido " + cantidad + " litros. Total: " + aguaActual);
        } else {
            System.out.println("No se pueden añadir " + cantidad + " litros. ¡El cubo rebosaría!");
        }
    }

    /**
     * Quitar agua: Solo si el cubo tiene la cantidad total que se quiere quitar.
     */
    public synchronized void quitarAgua(int cantidad) {
        if (aguaActual >= cantidad) {
            aguaActual -= cantidad;
            System.out.println("Se han quitado " + cantidad + " litros. Quedan: " + aguaActual);
        } else {
            System.out.println("No hay suficiente agua para quitar " + cantidad + " litros.");
        }
    }

    /**
     * Saber la cantidad de agua que tiene el cubo.
     */
    public synchronized int getCantidadAgua() {
        return aguaActual;
    }
}
