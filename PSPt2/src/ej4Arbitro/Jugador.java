package ej4Arbitro;
import java.util.Random;

public class Jugador implements Runnable {

    private int id;
    private Arbitro arbitro;
    private Random rand = new Random();

    public Jugador(int id, Arbitro arbitro) {
        this.id = id;
        this.arbitro = arbitro;
    }

    @Override
    public void run() {
        try {
            while (!arbitro.isJuegoTerminado()) {
                arbitro.esperarTurno(this.id);

                if (arbitro.isJuegoTerminado()) {
                    break;
                }

                int jugada = 1 + rand.nextInt(10);
                arbitro.jugar(this.id, jugada);
            }
        } catch (InterruptedException e) {
            System.out.println("Jugador " + id + " interrumpido.");
        }
        System.out.println("Jugador " + id + " termina de jugar.");
    }
}