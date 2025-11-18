package ej7Casino;

public class Ruleta {
    public static void main(String[] args) throws InterruptedException {
        Ruleta ruleta = new Ruleta();
        ruleta.comenzarJuego();
    }
    
    public void comenzarJuego() throws InterruptedException {
            
    	// Inicializamos la banca 
        Banca banca = new Banca();

        // Inicializamos los 4 jugadores
        Jugador[] jugadores = new Jugador[4];
        for (int i = 0; i < 4; i++) {
            jugadores[i] = new Jugador(i+1, banca);
        }

        for (Jugador jugador : jugadores) {
            jugador.start();
        }
        
        // Inicializamos el croupier
        Croupier croupier = new Croupier(banca);
        croupier.start();

        // Esperamos que todos los hilos terminen
        for (Jugador jugador : jugadores) {
            jugador.join();
        }
        //cuando terminan los jugadores, terminar croupier
        croupier.interrupt();

        // Resultado final
        System.out.println("Simulación terminada.");
        System.out.println("Saldo final de la banca: " + banca.getSaldo());
        for (int i = 0; i < 4; i++) {
            System.out.println("Saldo final del jugador " + (i + 1) + ": " + jugadores[i].getSaldo());
        }
    }
}