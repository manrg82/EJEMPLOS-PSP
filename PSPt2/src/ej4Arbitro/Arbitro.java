package ej4Arbitro;
public class Arbitro {

    private int numeroAdivinar;
    private boolean juegoTerminado = false;
    private int turno;
    private int numJugadores;

    public Arbitro(int numJugadores) {
        this.numJugadores = numJugadores;
        this.numeroAdivinar = 1 + (int) (10 * Math.random());
        this.turno = 1 + (int) (numJugadores * Math.random());
        System.out.println("Numero a adivinar: " + this.numeroAdivinar);
        System.out.println("Le toca jugar al jugador " + this.turno);
    }

    public synchronized boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public synchronized void esperarTurno(int id) throws InterruptedException {
        while (turno != id && !juegoTerminado) {
            wait();
        }
    }

    public synchronized void jugar(int id, int jugada) {
        if (!juegoTerminado) {

            System.out.println("Jugador " + id + " dice: " + jugada);

            if (jugada == numeroAdivinar) {
                System.out.println("Jugador " + id + " gana");
                juegoTerminado = true;
            } else {
                this.turno = 1 + (int) (numJugadores * Math.random());
                System.out.println("Le toca jugar al jugador " + this.turno);
            }

            notifyAll();
        }

    }
}