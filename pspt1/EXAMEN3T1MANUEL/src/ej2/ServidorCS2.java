package ej2;

public class ServidorCS2 {

    // PATRÓN SINGLETON: Instancia única y privada
    private static ServidorCS2 instanciaUnica = null;
    
    // Variables de estado del servidor
    private final int CAPACIDAD_MAXIMA = 10;
    private int jugadoresActuales = 0;

    // Constructor privado para evitar que se hagan "new ServidorCS2()" desde fuera
    private ServidorCS2() {
        System.out.println("Servidor CS2 Inicializado. Capacidad: " + CAPACIDAD_MAXIMA);
    }

    // Método sincronizado para obtener la instancia única
    public static synchronized ServidorCS2 getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ServidorCS2();
        }
        return instanciaUnica;
    }

    // --- MÉTODOS SINCRONIZADOS DE ACCESO ---

    public synchronized void conectarJugadores(int cantidad, String nombreEquipo) {
        if (jugadoresActuales + cantidad <= CAPACIDAD_MAXIMA) {
            jugadoresActuales += cantidad;
            System.out.println("[+] " + nombreEquipo + " ha conectado " + cantidad + " jugadores. Total: " + jugadoresActuales + "/" + CAPACIDAD_MAXIMA);
        } else {
            System.out.println("[RECHAZADO] " + nombreEquipo + " intentó conectar " + cantidad + " jugadores, pero el servidor está lleno. Total: " + jugadoresActuales + "/" + CAPACIDAD_MAXIMA);
        }
    }

    public synchronized void desconectarJugadores(int cantidad, String nombreEquipo) {
        if (jugadoresActuales >= cantidad) {
            jugadoresActuales -= cantidad;
            System.out.println("[-] " + nombreEquipo + " ha desconectado " + cantidad + " jugadores. Total: " + jugadoresActuales + "/" + CAPACIDAD_MAXIMA);
        } else {
            System.out.println("[ERROR] No se pueden desconectar " + cantidad + " jugadores. Solo hay " + jugadoresActuales + " dentro.");
        }
    }

    public synchronized int getJugadoresActuales() {
        return jugadoresActuales;
    }
}