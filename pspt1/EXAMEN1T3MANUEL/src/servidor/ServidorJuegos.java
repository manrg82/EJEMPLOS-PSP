package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ServidorJuegos {
    // Usamos ConcurrentHashMap para evitar problemas si dos clientes reservan a la vez
    public static ConcurrentHashMap<String, Integer> inventario = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        // Inicializamos el inventario de servidores
        inventario.put("Minecraft (Survival)", 20);
        inventario.put("Counter-Strike 2 (Competitivo)", 10);
        inventario.put("Metro Exodus (Co-op)", 4);
        inventario.put("Europa Universalis V (Pública)", 32);

        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            System.out.println("Servidor iniciado en el puerto 6666. Esperando conexiones...");

            while (true) {
                // El servidor se bloquea hasta aceptar una conexión
                Socket conexion = serverSocket.accept();
                System.out.println("Nueva conexión recibida desde: " + conexion.getInetAddress());
                
                // Creamos un nuevo hilo para manejar al cliente y lo iniciamos
                ManejadorCliente manejador = new ManejadorCliente(conexion);
                Thread hilo = new Thread(manejador);
                hilo.start();
            }
        } catch (IOException e) {
            System.out.println("Error al arrancar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}