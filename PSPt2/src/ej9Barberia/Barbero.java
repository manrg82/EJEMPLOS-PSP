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
        while (true) {
            try {
                int idSilla = gestor.siguienteCliente(nombre);
                System.out.println(nombre + " atendiendo silla: " + idSilla);
                Thread.sleep(250);
                gestor.finCorte(idSilla, nombre);

            } catch (InterruptedException e) {
                return; 
            }
        }
    }
}
