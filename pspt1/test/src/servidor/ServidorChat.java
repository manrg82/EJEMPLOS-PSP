package servidor;

import modelo.Mensaje;
import utils.ConfigDB;

import java.io. IOException;
import java.net. ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servidor principal del chat multihilo. 
 * Acepta conexiones de clientes y crea un hilo ManejadorCliente para cada uno. 
 * Mantiene registro de clientes conectados para envío de mensajes.
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class ServidorChat {
    
    // Socket del servidor
    private ServerSocket serverSocket;
    
    // Mapa de clientes conectados:  idUsuario -> ManejadorCliente
    private Map<Integer, ManejadorCliente> clientesConectados;
    
    // Estado del servidor
    private boolean ejecutando;
    
    /**
     * Constructor del servidor
     */
    public ServidorChat() {
        // Usamos ConcurrentHashMap para thread-safety
        this.clientesConectados = new ConcurrentHashMap<>();
    }
    
    /**
     * Inicia el servidor y comienza a aceptar conexiones
     */
    public void iniciar() {
        try {
            serverSocket = new ServerSocket(ConfigDB. PUERTO_SERVIDOR);
            ejecutando = true;
            
            System.out.println("========================================");
            System.out.println("   SERVIDOR DE CHAT INICIADO");
            System.out. println("   Puerto: " + ConfigDB.PUERTO_SERVIDOR);
            System. out.println("   Esperando conexiones...");
            System.out.println("========================================");
            
            // Bucle principal:  aceptar conexiones
            while (ejecutando) {
                try {
                    // Esperar nueva conexión
                    Socket socketCliente = serverSocket.accept();
                    
                    // Crear manejador para el cliente
                    ManejadorCliente manejador = new ManejadorCliente(socketCliente, this);
                    
                    // Iniciar hilo del manejador
                    manejador.start();
                    
                } catch (IOException e) {
                    if (ejecutando) {
                        System.err.println("[Servidor] Error al aceptar conexión: " + e.getMessage());
                    }
                }
            }
            
        } catch (IOException e) {
            System.err.println("[Servidor] Error al iniciar servidor: " + e.getMessage());
        }
    }
    
    /**
     * Detiene el servidor
     */
    public void detener() {
        ejecutando = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err. println("[Servidor] Error al detener servidor: " + e.getMessage());
        }
        System.out.println("[Servidor] Servidor detenido");
    }
    
    // ==================== GESTIÓN DE CLIENTES CONECTADOS ====================
    
    /**
     * Registra un cliente como conectado
     * @param idUsuario ID del usuario
     * @param manejador Manejador del cliente
     */
    public void registrarClienteConectado(int idUsuario, ManejadorCliente manejador) {
        clientesConectados.put(idUsuario, manejador);
        System.out.println("[Servidor] Usuarios conectados: " + clientesConectados.size());
    }
    
    /**
     * Elimina un cliente de la lista de conectados
     * @param idUsuario ID del usuario
     */
    public void eliminarClienteConectado(int idUsuario) {
        clientesConectados.remove(idUsuario);
        System.out.println("[Servidor] Usuarios conectados: " + clientesConectados.size());
    }
    
    /**
     * Verifica si un usuario está conectado
     * @param idUsuario ID del usuario
     * @return true si está conectado
     */
    public boolean estaConectado(int idUsuario) {
        return clientesConectados.containsKey(idUsuario);
    }
    
    // ==================== ENVÍO DE MENSAJES ====================
    
    /**
     * Envía un mensaje a todos los clientes conectados (excepto el emisor)
     * @param mensaje Mensaje a enviar
     * @param idEmisor ID del emisor (para excluirlo)
     */
    public void enviarMensajeGlobal(Mensaje mensaje, int idEmisor) {
        for (Map.Entry<Integer, ManejadorCliente> entry : clientesConectados.entrySet()) {
            if (entry.getKey() != idEmisor) {
                entry.getValue().enviarNotificacion(mensaje);
            }
        }
    }
    
    /**
     * Envía un mensaje solo a los amigos conectados
     * @param mensaje Mensaje a enviar
     * @param idsAmigos Lista de IDs de amigos
     */
    public void enviarMensajeAAmigos(Mensaje mensaje, List<Integer> idsAmigos) {
        for (Integer idAmigo : idsAmigos) {
            ManejadorCliente manejador = clientesConectados.get(idAmigo);
            if (manejador != null) {
                manejador.enviarNotificacion(mensaje);
            }
        }
    }
    
    /**
     * Envía un mensaje privado a un usuario específico
     * @param mensaje Mensaje a enviar
     * @param idReceptor ID del receptor
     */
    public void enviarMensajePrivado(Mensaje mensaje, int idReceptor) {
        ManejadorCliente manejador = clientesConectados.get(idReceptor);
        if (manejador != null) {
            manejador.enviarNotificacion(mensaje);
        }
    }
    
    /**
     * Método principal para iniciar el servidor
     */
    public static void main(String[] args) {
        ServidorChat servidor = new ServidorChat();
        servidor.iniciar();
    }
}