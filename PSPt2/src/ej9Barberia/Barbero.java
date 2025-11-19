package ej9Barberia;
class Barbero extends Thread {
    private GestorBarberia gestor;
    private String nombre;

    public Barbero(GestorBarberia gestor, int id) {
        this.gestor = gestor;
        this.nombre = "Barbero" + id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Silla silla = gestor.esperarCliente();
                if (silla == null) break;
                gestor.finalizarCorte(silla, nombre);
            }
        } catch (InterruptedException e) {
            
        }
    }
}
