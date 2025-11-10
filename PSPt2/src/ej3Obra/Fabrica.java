package ej3Obra;

public class Fabrica implements Runnable {

    private Almacen almacen;
    private final int LOTE = 450;
    private final int PRODUCCION_MAXIMA = 13500; 
    private final int DESCANSO_FABRICA = 3000; 

    public Fabrica(Almacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public void run() {
        System.out.println("--- FÁBRICA INICIA ---");
        int totalProducido = 0;

        try {
            while (totalProducido < PRODUCCION_MAXIMA) {
                almacen.guardar(LOTE);
                totalProducido += LOTE;
                System.out.println("FÁBRICA: Producción total hasta ahora: " + totalProducido + "/" + PRODUCCION_MAXIMA);
                Thread.sleep(DESCANSO_FABRICA);
            }
        } catch (InterruptedException e) {
            System.out.println("Fábrica interrumpida.");
        }
        
        System.out.println("--- FÁBRICA CIERRA (Producción máxima alcanzada) ---");
    }
}
