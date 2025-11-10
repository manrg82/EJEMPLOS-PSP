package ej4Arbitro;
import java.util.ArrayList;
import java.util.List;

public class MainJuego {

    public static void main(String[] args) {
        int numJugadores = 3;
        Arbitro arbitro = new Arbitro(numJugadores);
        List<Thread> hilos = new ArrayList<>();

        for (int i = 1; i <= numJugadores; i++) {
            Jugador j = new Jugador(i, arbitro);
            Thread t = new Thread(j);
            hilos.add(t);
            t.start();
        }

        try {
            for (Thread t : hilos) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("El juego ha finalizado.");
    }
}