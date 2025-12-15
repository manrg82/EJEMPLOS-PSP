package cliente;

import modelo.Mensaje;
import modelo.Peticion;
import modelo.Peticion.Operacion;
import modelo.Usuario;
import utils.ConfigDB;

import java.io. IOException;
import java.io. ObjectInputStream;
import java.io.ObjectOutputStream;
import java. net.Socket;

/**
 * Cliente del chat que se conecta al servidor. 
 * Gestiona la comunicación con el servidor y la interfaz de usuario.
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class ClienteChat {
    
    // Socket de conexión con el servidor
    private Socket socket;
    
    // Streams de entrada/salida de objetos
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    
    // Interfaz de usuario
    private InterfazCliente interfaz;
    
    // Usuario actualmente logueado
    private Usuario usuarioActual;
    
    // Estado de la conexión
    private boolean conectado;
    
    /**
     * Constructor del cliente
     */
    public ClienteChat() {
        this.interfaz = new InterfazCliente();
        this.conectado = false;
        this.usuarioActual = null;
    }
    
    /**
     * Conecta con el servidor
     * @return true si la conexión fue exitosa
     */
    public boolean conectar() {
        try {
            socket = new Socket(ConfigDB.HOST_SERVIDOR, ConfigDB. PUERTO_SERVIDOR);
            
            // Crear streams (salida primero para evitar deadlock)
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
            
            conectado = true;
            System.out.println("[Cliente] Conectado al servidor en " + 
                             ConfigDB.HOST_SERVIDOR + ":" + ConfigDB.PUERTO_SERVIDOR);
            return true;
            
        } catch (IOException e) {
            interfaz.mostrarError("No se pudo conectar al servidor:  " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Desconecta del servidor
     */
    public void desconectar() {
        try {
            conectado = false;
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null) socket.close();
            
        } catch (IOException e) {
            System.err.println("[Cliente] Error al desconectar: " + e.getMessage());
        }
    }
    
    /**
     * Envía una petición al servidor y espera respuesta
     * @param peticion Petición a enviar
     * @return Respuesta del servidor
     */
    private synchronized Peticion enviarPeticion(Peticion peticion) {
        try {
            // Enviar petición
            salida.writeObject(peticion);
            salida.flush();
            salida.reset(); // Evitar caché de objetos
            
            // Esperar respuesta
            Peticion respuesta = (Peticion) entrada.readObject();
            return respuesta;
            
        } catch (IOException | ClassNotFoundException e) {
            interfaz.mostrarError("Error de comunicación: " + e.getMessage());
            conectado = false;
            return null;
        }
    }
    
    /**
     * Ejecuta el bucle principal del cliente
     */
    public void ejecutar() {
        // Intentar conectar
        if (!conectar()) {
            return;
        }
        
        boolean salir = false;
        
        while (! salir && conectado) {
            try {
                if (usuarioActual == null) {
                    // Menú inicial (sin login)
                    interfaz.mostrarMenuInicial();
                    int opcion = interfaz.leerOpcion();
                    
                    switch (opcion) {
                        case 1:
                            registrarUsuario();
                            break;
                        case 2:
                            iniciarSesion();
                            break;
                        case 0:
                            salir = true;
                            break;
                        default:
                            interfaz.mostrarError("Opción no válida");
                    }
                } else {
                    // Menú principal (con login)
                    interfaz. mostrarMenuPrincipal(usuarioActual.getNombreUsuario());
                    int opcion = interfaz.leerOpcion();
                    
                    switch (opcion) {
                        case 3:
                            cerrarSesion();
                            break;
                        case 4:
                            if (darDeBaja()) {
                                salir = true;
                            }
                            break;
                        case 5:
                            verListaUsuarios();
                            break;
                        case 6:
                            anadirAmigo();
                            break;
                        case 7:
                            eliminarAmigo();
                            break;
                        case 8:
                            verListaAmigos();
                            break;
                        case 9:
                            enviarMensajeGlobal();
                            break;
                        case 10:
                            enviarMensajeAmigos();
                            break;
                        case 11:
                            enviarMensajePrivado();
                            break;
                        case 12:
                            verHistorial();
                            break;
                        default:
                            interfaz. mostrarError("Opción no válida");
                    }
                }
            } catch (Exception e) {
                interfaz. mostrarError("Error inesperado: " + e.getMessage());
            }
        }
        
        // Cerrar conexiones
        desconectar();
        interfaz.mostrarDespedida();
        interfaz.cerrar();
    }
    
    // ==================== OPERACIONES DEL MENÚ ====================
    
    /**
     * Operación 1: Registrar nuevo usuario
     */
    private void registrarUsuario() {
        Usuario nuevoUsuario = interfaz.solicitarDatosRegistro();
        
        // Validar datos
        if (nuevoUsuario.getNombreUsuario().isEmpty() || 
            nuevoUsuario.getContrasena().isEmpty() ||
            nuevoUsuario.getEmail().isEmpty()) {
            interfaz.mostrarError("Todos los campos son obligatorios");
            return;
        }
        
        // Crear petición
        Peticion peticion = new Peticion(Operacion. USUARIO_NUEVO, nuevoUsuario);
        
        // Enviar y procesar respuesta
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta.isExitosa()) {
                interfaz.mostrarExito(respuesta.getMensajeRespuesta());
            } else {
                interfaz. mostrarError(respuesta.getMensajeRespuesta());
            }
        }
    }
    
    /**
     * Operación 2: Iniciar sesión
     */
    private void iniciarSesion() {
        Usuario credenciales = interfaz.solicitarDatosLogin();
        
        // Validar datos
        if (credenciales. getNombreUsuario().isEmpty() || 
            credenciales. getContrasena().isEmpty()) {
            interfaz.mostrarError("Usuario y contraseña son obligatorios");
            return;
        }
        
        // Crear petición
        Peticion peticion = new Peticion(Operacion.LOGIN, credenciales);
        
        // Enviar y procesar respuesta
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta.isExitosa()) {
                usuarioActual = respuesta.getUsuario();
                interfaz.mostrarExito(respuesta.getMensajeRespuesta());
            } else {
                interfaz. mostrarError(respuesta.getMensajeRespuesta());
            }
        }
    }
    
    /**
     * Operación 3: Cerrar sesión
     */
    private void cerrarSesion() {
        Peticion peticion = new Peticion(Operacion.LOGOUT);
        peticion.setUsuario(usuarioActual);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null && respuesta.isExitosa()) {
            interfaz.mostrarExito(respuesta.getMensajeRespuesta());
            usuarioActual = null;
            conectado = false; // Terminar conexión tras logout
        }
    }
    
    /**
     * Operación 4: Dar de baja usuario
     * @return true si el usuario fue dado de baja
     */
    private boolean darDeBaja() {
        if (! interfaz.solicitarConfirmacion("¿Está seguro de eliminar su cuenta?")) {
            return false;
        }
        
        Peticion peticion = new Peticion(Operacion.BAJA_USUARIO);
        peticion.setUsuario(usuarioActual);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta. isExitosa()) {
                interfaz.mostrarExito(respuesta.getMensajeRespuesta());
                usuarioActual = null;
                return true;
            } else {
                interfaz.mostrarError(respuesta.getMensajeRespuesta());
            }
        }
        return false;
    }
    
    /**
     * Operación 5: Ver lista de usuarios
     */
    private void verListaUsuarios() {
        Peticion peticion = new Peticion(Operacion.LISTA_USUARIOS);
        peticion.setUsuario(usuarioActual);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null && respuesta.isExitosa()) {
            interfaz.mostrarListaUsuarios(respuesta.getListaUsuarios());
        } else if (respuesta != null) {
            interfaz.mostrarError(respuesta.getMensajeRespuesta());
        }
    }
    
    /**
     * Operación 6: Añadir amigo
     */
    private void anadirAmigo() {
        String nombreAmigo = interfaz.solicitarNombreAmigo("añadir");
        
        if (nombreAmigo. isEmpty()) {
            interfaz.mostrarError("Debe introducir un nombre");
            return;
        }
        
        Peticion peticion = new Peticion(Operacion.ANADIR_AMIGO);
        peticion.setUsuario(usuarioActual);
        peticion.setParametro(nombreAmigo);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta.isExitosa()) {
                interfaz.mostrarExito(respuesta.getMensajeRespuesta());
            } else {
                interfaz. mostrarError(respuesta.getMensajeRespuesta());
            }
        }
    }
    
    /**
     * Operación 7: Eliminar amigo
     */
    private void eliminarAmigo() {
        String nombreAmigo = interfaz.solicitarNombreAmigo("eliminar");
        
        if (nombreAmigo. isEmpty()) {
            interfaz. mostrarError("Debe introducir un nombre");
            return;
        }
        
        if (!interfaz.solicitarConfirmacion("¿Eliminar a " + nombreAmigo + " de amigos?")) {
            return;
        }
        
        Peticion peticion = new Peticion(Operacion.BAJA_AMIGO);
        peticion.setUsuario(usuarioActual);
        peticion.setParametro(nombreAmigo);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta.isExitosa()) {
                interfaz.mostrarExito(respuesta.getMensajeRespuesta());
            } else {
                interfaz.mostrarError(respuesta.getMensajeRespuesta());
            }
        }
    }
    
    /**
     * Operación 8: Ver lista de amigos
     */
    private void verListaAmigos() {
        Peticion peticion = new Peticion(Operacion.LISTA_AMIGOS);
        peticion.setUsuario(usuarioActual);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null && respuesta.isExitosa()) {
            interfaz.mostrarListaAmigos(respuesta.getListaUsuarios());
        } else if (respuesta != null) {
            interfaz.mostrarError(respuesta.getMensajeRespuesta());
        }
    }
    
    /**
     * Operación 9: Enviar mensaje global
     */
    private void enviarMensajeGlobal() {
        String contenido = interfaz.solicitarMensaje();
        
        if (contenido.isEmpty()) {
            interfaz.mostrarError("El mensaje no puede estar vacío");
            return;
        }
        
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(contenido);
        mensaje.setTipo(Mensaje.TIPO_GLOBAL);
        
        Peticion peticion = new Peticion(Operacion.MENSAJE_GLOBAL);
        peticion.setUsuario(usuarioActual);
        peticion.setMensaje(mensaje);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta.isExitosa()) {
                interfaz.mostrarExito(respuesta.getMensajeRespuesta());
            } else {
                interfaz. mostrarError(respuesta.getMensajeRespuesta());
            }
        }
    }
    
    /**
     * Operación 10: Enviar mensaje a todos los amigos
     */
    private void enviarMensajeAmigos() {
        String contenido = interfaz.solicitarMensaje();
        
        if (contenido.isEmpty()) {
            interfaz.mostrarError("El mensaje no puede estar vacío");
            return;
        }
        
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(contenido);
        mensaje.setTipo(Mensaje.TIPO_AMIGOS);
        
        Peticion peticion = new Peticion(Operacion.MENSAJE_AMIGOS);
        peticion.setUsuario(usuarioActual);
        peticion.setMensaje(mensaje);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta.isExitosa()) {
                interfaz.mostrarExito(respuesta.getMensajeRespuesta());
            } else {
                interfaz.mostrarError(respuesta. getMensajeRespuesta());
            }
        }
    }
    
    /**
     * Operación 11: Enviar mensaje privado a un amigo
     */
    private void enviarMensajePrivado() {
        String nombreAmigo = interfaz.solicitarNombreAmigo("enviar mensaje");
        
        if (nombreAmigo.isEmpty()) {
            interfaz.mostrarError("Debe introducir un nombre");
            return;
        }
        
        String contenido = interfaz.solicitarMensaje();
        
        if (contenido.isEmpty()) {
            interfaz.mostrarError("El mensaje no puede estar vacío");
            return;
        }
        
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(contenido);
        mensaje.setTipo(Mensaje.TIPO_PRIVADO);
        
        Peticion peticion = new Peticion(Operacion. MENSAJE_PRIVADO);
        peticion.setUsuario(usuarioActual);
        peticion.setMensaje(mensaje);
        peticion.setParametro(nombreAmigo);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta. isExitosa()) {
                interfaz.mostrarExito(respuesta.getMensajeRespuesta());
            } else {
                interfaz.mostrarError(respuesta.getMensajeRespuesta());
            }
        }
    }
    
    /**
     * Operación 12: Ver historial de conversación con un amigo
     */
    private void verHistorial() {
        String nombreAmigo = interfaz.solicitarNombreAmigo("ver historial");
        
        if (nombreAmigo. isEmpty()) {
            interfaz. mostrarError("Debe introducir un nombre");
            return;
        }
        
        Peticion peticion = new Peticion(Operacion.HISTORIAL_CONVERSACION);
        peticion.setUsuario(usuarioActual);
        peticion.setParametro(nombreAmigo);
        
        Peticion respuesta = enviarPeticion(peticion);
        
        if (respuesta != null) {
            if (respuesta. isExitosa()) {
                interfaz.mostrarHistorial(respuesta.getListaMensajes(), nombreAmigo);
            } else {
                interfaz.mostrarError(respuesta.getMensajeRespuesta());
            }
        }
    }
    
    /**
     * Método principal para iniciar el cliente
     */
    public static void main(String[] args) {
        ClienteChat cliente = new ClienteChat();
        cliente.ejecutar();
    }
}