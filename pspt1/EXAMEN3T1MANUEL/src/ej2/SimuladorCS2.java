package ej2;
public class SimuladorCS2 {
    public static void main(String[] args) {
        // Creamos varios hilos intentando entrar al servidor simultáneamente
        Thread t1 = new Thread(new HiloJugadores("Equipo Alpha", 5, true));
        Thread t2 = new Thread(new HiloJugadores("Equipo Bravo", 4, true));
        Thread t3 = new Thread(new HiloJugadores("Equipo Charlie", 3, true)); // Este debería ser rechazado (5+4+3 = 12 > 10)
        Thread t4 = new Thread(new HiloJugadores("Equipo Alpha", 5, false)); // Alpha se va
        Thread t5 = new Thread(new HiloJugadores("Equipo Delta", 6, true)); // Ahora Delta tiene espacio para entrar

        // Los lanzamos todos a la vez para forzar la concurrencia
        t1.start();
        t2.start();
        t3.start();
        
        try {
            // Damos una pausa minúscula para que dé tiempo a que se llenen los primeros
            Thread.sleep(100); 
            t4.start();
            t5.start();
            
            // Esperamos a que todos terminen
            t1.join(); t2.join(); t3.join(); t4.join(); t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulación terminada. Jugadores finales: " + ServidorCS2.getInstancia().getJugadoresActuales());
    }
}