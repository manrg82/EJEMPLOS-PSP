package ej6Boxeo;

public class Boxeador implements Runnable {

    private String nombre;
    private Ring ring;
    private Boxeador rival;

    private volatile boolean noqueado = false;
    private int golpesRecibidosParaKO = 0;
    private int golpesDadosTotales = 0;
    private int golpesRecibidosTotales = 0;

    public Boxeador(String nombre, Ring ring) {
        this.nombre = nombre;
        this.ring = ring;
    }

    public void setRival(Boxeador rival) {
        this.rival = rival;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        try {
            while (ring.hayCombate()) {
                if (noqueado) {
                    System.out.println("-- " + this.nombre + " está noqueado.");
                    Thread.sleep(1000);
                    this.noqueado = false;
                    this.golpesRecibidosParaKO = 0;
                } else {
                    combatir();
                    Thread.sleep(2000);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this.nombre + " ha sido interrumpido.");
        }
    }

    private void combatir() {
        boolean haCombatido = ring.realizarCombate(this.nombre, this.rival.getNombre());
        if (haCombatido) {
            this.darGolpe();
            this.rival.recibirGolpe();
        }
    }

    public synchronized void recibirGolpe() {
        this.golpesRecibidosTotales++;
        this.golpesRecibidosParaKO++;
        if (this.golpesRecibidosParaKO >= 3) {
            this.noqueado = true;
            this.golpesRecibidosParaKO = 0;
        }
    }

    public synchronized void darGolpe() {
        this.golpesDadosTotales++;
    }

    public void mostrarResultados() {
        System.out.println(
                this.nombre + " [golpesDados=" + this.golpesDadosTotales + ", golpesRecibidos=" + this.golpesRecibidosTotales + "]");
    }
}