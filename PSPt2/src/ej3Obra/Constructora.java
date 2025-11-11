package ej3Obra;

public class Constructora {

    public static void main(String[] args) {
        Almacen almacen = Almacen.getInstancia();
        Fabrica fabrica = new Fabrica(almacen);

        Obra obra1 = new Obra("Obra1", 200, 4000);
        Obra obra2 = new Obra("Obra2", 400, 2000);
        Thread hiloFabrica = new Thread(fabrica);
        Thread hiloObra1 = new Thread(obra1);
        Thread hiloObra2 = new Thread(obra2);
        
        System.out.println("=== LA CONSTRUCTORA ABRE ===");
        System.out.println("Stock inicial: 0. Capacidad maxima: 6000.");
        hiloFabrica.start();
        hiloObra1.start();
        hiloObra2.start();

        try {
            long tiempoLimite = 120 * 1000;
            System.out.println("\nCONSTRUCTORA: Las obras trabajaron por 2 minutos...\n");
            Thread.sleep(tiempoLimite);
            System.out.println("\nCONSTRUCTORA: Se cumplen 2 minutos. Cerrando obras...\n");
            obra1.cerrarObra(); 
            obra2.cerrarObra(); 
            
            hiloObra1.interrupt();
            hiloObra2.interrupt();
            System.out.println("CONSTRUCTORA: Esperando a que la fabrica y las obras finalicen...");
            
            hiloFabrica.join();
            hiloObra1.join();
            hiloObra2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=== LA CONSTRUCTORA CIERRA (Todos los hilos han terminado) ===");
    }
}
