package ej3Obra;

public class Obra implements Runnable {
    private Almacen almacen;
    private String nombre;
    private int gastoLote; 
    private int tiempoDescanso;
    private volatile boolean enFuncionamiento = true;
    public Obra(String nombre, int gastoLote, int tiempoDescanso) {
        this.almacen = Almacen.getInstancia();
        this.nombre = nombre;
        this.gastoLote = gastoLote;
        this.tiempoDescanso = tiempoDescanso;
    }

    @Override
    public void run() {
        System.out.println("--- " + this.nombre + " INICIA ---");
        
        try {
            while (enFuncionamiento) {
                almacen.retirar(this.gastoLote, this.nombre);

                Thread.sleep(this.tiempoDescanso);
            }
        } catch (InterruptedException e) {
        }
        
        System.out.println("--- " + this.nombre + " CIERRA (Tiempo agotado) ---");
    }

    public void cerrarObra() {
        this.enFuncionamiento = false;
    }
}
