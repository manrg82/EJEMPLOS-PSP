package ej2;

class HiloJugadores implements Runnable {
    private String nombreEquipo;
    private int cantidad;
    private boolean esConexion; // true para conectar, false para desconectar

    public HiloJugadores(String nombreEquipo, int cantidad, boolean esConexion) {
        this.nombreEquipo = nombreEquipo;
        this.cantidad = cantidad;
        this.esConexion = esConexion;
    }

    @Override
    public void run() {
        // Obtenemos la instancia única del servidor
        ServidorCS2 servidor = ServidorCS2.getInstancia();

        if (esConexion) {
            servidor.conectarJugadores(cantidad, nombreEquipo);
        } else {
            servidor.desconectarJugadores(cantidad, nombreEquipo);
        }
    }
}
