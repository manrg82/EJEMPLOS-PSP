package servidor;

import modelo.Mensaje;
import modelo. Peticion;
import modelo.Peticion. Operacion;
import modelo.Usuario;

import java.io. IOException;
import java.io. ObjectInputStream;
import java.io.ObjectOutputStream;
import java. net.Socket;
import java. util.List;

/**
 * Hilo que maneja la comunicación con un cliente específico. 
 * Cada cliente conectado tiene su propio ManejadorCliente.
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class ManejadorCliente extends Thread {
    
    // Socket de comunicación con el cliente
    private Socket socket;
    
    // Streams de entrada/salida de objetos
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    
    // Referencia al servidor principal
    private ServidorChat servidor;
    
    // Gestor de base de datos
    private GestorBaseDatos gestorBD;
    
    // Usuario actualmente conectado en esta sesión
    private Usuario usuarioConectado;
    
    // Estado de la conexión
    private boolean conectado;
    
    /**
     * Constructor del manejador de cliente
     * @param socket Socket de conexión con el cliente
     * @param servidor Referencia al servidor principal
     */
    public ManejadorCliente(Socket socket, ServidorChat servidor) {
        this.socket = socket;
        this.servidor = servidor;
        this.gestorBD = GestorBaseDatos.getInstancia();
        this.conectado = true;
        
        try {
            // Crear streams de objetos (salida primero para evitar deadlock)
            this.salida = new ObjectOutputStream(socket. getOutputStream());
            this.entrada = new ObjectInputStream(socket.getInputStream());
            System.out.println("[Servidor] Cliente conectado desde:  " + socket.getInetAddress());
        } catch (IOException e) {
            System.err. println("[Servidor] Error al crear streams: " + e.getMessage());
            conectado = false;
        }
    }
    
    /**
     * Método principal del hilo.  Escucha peticiones del cliente.
     */
    @Override
    public void run() {
        try {
            while (conectado) {
                // Leer petición del cliente
                Peticion peticion = (Peticion) entrada.readObject();
                
                // Procesar la petición y obtener respuesta
                Peticion respuesta = procesarPeticion(peticion);
                
                // Enviar respuesta al cliente
                enviarRespuesta(respuesta);
            }
        } catch (IOException e) {
            System. out.println("[Servidor] Cliente desconectado:  " + 
                (usuarioConectado != null ? usuarioConectado.getNombreUsuario() : "desconocido"));
        } catch (ClassNotFoundException e) {
            System.err.println("[Servidor] Error de clase: " + e.getMessage());
        } finally {
            desconectar();
        }
    }
    
    /**
     * Procesa una petición del cliente y genera la respuesta
     * @param peticion Petición recibida
     * @return Respuesta a enviar
     */
    private Peticion procesarPeticion(Peticion peticion) {
        Peticion respuesta = new Peticion(peticion. getOperacion());
        
        switch (peticion.getOperacion()) {
            case USUARIO_NUEVO:
                procesarRegistro(peticion, respuesta);
                break;
                
            case LOGIN:
                procesarLogin(peticion, respuesta);
                break;
                
            case LOGOUT:
                procesarLogout(respuesta);
                break;
                
            case BAJA_USUARIO:
                procesarBajaUsuario(respuesta);
                break;
                
            case LISTA_USUARIOS:
                procesarListaUsuarios(respuesta);
                break;
                
            case ANADIR_AMIGO:
                procesarAnadirAmigo(peticion, respuesta);
                break;
                
            case BAJA_AMIGO:
                procesarBajaAmigo(peticion, respuesta);
                break;
                
            case LISTA_AMIGOS:
                procesarListaAmigos(respuesta);
                break;
                
            case MENSAJE_GLOBAL:
                procesarMensajeGlobal(peticion, respuesta);
                break;
                
            case MENSAJE_AMIGOS:
                procesarMensajeAmigos(peticion, respuesta);
                break;
                
            case MENSAJE_PRIVADO:
                procesarMensajePrivado(peticion, respuesta);
                break;
                
            case HISTORIAL_CONVERSACION:
                procesarHistorial(peticion, respuesta);
                break;
                
            default:
                respuesta.setExitosa(false);
                respuesta. setMensajeRespuesta("Operación no reconocida");
        }
        
        return respuesta;
    }
    
    // ==================== PROCESAMIENTO DE OPERACIONES ====================
    
    /**
     * Procesa el registro de un nuevo usuario
     */
    private void procesarRegistro(Peticion peticion, Peticion respuesta) {
        Usuario nuevoUsuario = peticion.getUsuario();
        
        if (gestorBD.registrarUsuario(nuevoUsuario)) {
            respuesta.setExitosa(true);
            respuesta.setMensajeRespuesta("Usuario registrado correctamente");
            System.out.println("[Servidor] Nuevo usuario registrado: " + nuevoUsuario.getNombreUsuario());
        } else {
            respuesta.setExitosa(false);
            respuesta. setMensajeRespuesta("Error al registrar usuario.  El nombre o email ya existe.");
        }
    }
    
    /**
     * Procesa el login de un usuario
     */
    private void procesarLogin(Peticion peticion, Peticion respuesta) {
        Usuario usuario = peticion.getUsuario();
        Usuario usuarioValidado = gestorBD.validarLogin(
            usuario.getNombreUsuario(), 
            usuario.getContrasena()
        );
        
        if (usuarioValidado != null) {
            this.usuarioConectado = usuarioValidado;
            servidor.registrarClienteConectado(usuarioValidado. getIdUsuario(), this);
            
            respuesta.setExitosa(true);
            respuesta. setUsuario(usuarioValidado);
            respuesta.setMensajeRespuesta("Login exitoso.  Bienvenido " + usuarioValidado.getNombreUsuario());
            System.out.println("[Servidor] Login:  " + usuarioValidado.getNombreUsuario());
        } else {
            respuesta.setExitosa(false);
            respuesta.setMensajeRespuesta("Credenciales inválidas o usuario inactivo");
        }
    }
    
    /**
     * Procesa el logout de un usuario
     */
    private void procesarLogout(Peticion respuesta) {
        if (usuarioConectado != null) {
            servidor.eliminarClienteConectado(usuarioConectado.getIdUsuario());
            System.out.println("[Servidor] Logout: " + usuarioConectado.getNombreUsuario());
            usuarioConectado = null;
        }
        
        respuesta. setExitosa(true);
        respuesta.setMensajeRespuesta("Sesión cerrada correctamente");
        conectado = false;
    }
    
    /**
     * Procesa la baja de un usuario
     */
    private void procesarBajaUsuario(Peticion respuesta) {
        if (usuarioConectado == null) {
            respuesta. setExitosa(false);
            respuesta.setMensajeRespuesta("Debe iniciar sesión primero");
            return;
        }
        
        if (gestorBD.bajaUsuario(usuarioConectado.getIdUsuario())) {
            servidor.eliminarClienteConectado(usuarioConectado.getIdUsuario());
            System. out.println("[Servidor] Baja usuario: " + usuarioConectado.getNombreUsuario());
            
            respuesta.setExitosa(true);
            respuesta. setMensajeRespuesta("Usuario dado de baja correctamente");
            usuarioConectado = null;
            conectado = false;
        } else {
            respuesta.setExitosa(false);
            respuesta.setMensajeRespuesta("Error al dar de baja el usuario");
        }
    }
    
    /**
     * Procesa la solicitud de lista de usuarios
     */
    private void procesarListaUsuarios(Peticion respuesta) {
        if (! verificarSesion(respuesta)) return;
        
        List<Usuario> usuarios = gestorBD.obtenerTodosUsuarios();
        respuesta.setExitosa(true);
        respuesta.setListaUsuarios(usuarios);
        respuesta.setMensajeRespuesta("Se encontraron " + usuarios.size() + " usuarios");
    }
    
    /**
     * Procesa añadir un amigo
     */
    private void procesarAnadirAmigo(Peticion peticion, Peticion respuesta) {
        if (!verificarSesion(respuesta)) return;
        
        String nombreAmigo = peticion.getParametro();
        Usuario amigo = gestorBD. obtenerUsuarioPorNombre(nombreAmigo);
        
        if (amigo == null) {
            respuesta. setExitosa(false);
            respuesta.setMensajeRespuesta("Usuario '" + nombreAmigo + "' no encontrado");
            return;
        }
        
        if (amigo.getIdUsuario() == usuarioConectado.getIdUsuario()) {
            respuesta.setExitosa(false);
            respuesta.setMensajeRespuesta("No puedes añadirte a ti mismo como amigo");
            return;
        }
        
        if (gestorBD.sonAmigos(usuarioConectado.getIdUsuario(), amigo.getIdUsuario())) {
            respuesta.setExitosa(false);
            respuesta.setMensajeRespuesta("Ya sois amigos");
            return;
        }
        
        if (gestorBD.anadirAmigo(usuarioConectado.getIdUsuario(), amigo.getIdUsuario())) {
            respuesta.setExitosa(true);
            respuesta.setMensajeRespuesta("Amigo añadido:  " + nombreAmigo);
            System.out.println("[Servidor] " + usuarioConectado.getNombreUsuario() + 
                             " añadió a " + nombreAmigo + " como amigo");
        } else {
            respuesta.setExitosa(false);
            respuesta.setMensajeRespuesta("Error al añadir amigo");
        }
    }
    
    /**
     * Procesa eliminar un amigo
     */
    private void procesarBajaAmigo(Peticion peticion, Peticion respuesta) {
        if (!verificarSesion(respuesta)) return;
        
        String nombreAmigo = peticion.getParametro();
        Usuario amigo = gestorBD.obtenerUsuarioPorNombre(nombreAmigo);
        
        if (amigo == null) {
            respuesta. setExitosa(false);
            respuesta.setMensajeRespuesta("Usuario no encontrado");
            return;
        }
        
        if (gestorBD.eliminarAmigo(usuarioConectado.getIdUsuario(), amigo.getIdUsuario())) {
            respuesta.setExitosa(true);
            respuesta.setMensajeRespuesta("Amigo eliminado: " + nombreAmigo);
            System.out.println("[Servidor] " + usuarioConectado.getNombreUsuario() + 
                             " eliminó a " + nombreAmigo + " de amigos");
        } else {
            respuesta.setExitosa(false);
            respuesta. setMensajeRespuesta("Error al eliminar amigo o no era tu amigo");
        }
    }
    
    /**
     * Procesa la solicitud de lista de amigos
     */
    private void procesarListaAmigos(Peticion respuesta) {
        if (!verificarSesion(respuesta)) return;
        
        List<Usuario> amigos = gestorBD.obtenerAmigos(usuarioConectado. getIdUsuario());
        respuesta.setExitosa(true);
        respuesta.setListaUsuarios(amigos);
        respuesta.setMensajeRespuesta("Tienes " + amigos.size() + " amigos");
    }
    
    /**
     * Procesa envío de mensaje global
     */
    private void procesarMensajeGlobal(Peticion peticion, Peticion respuesta) {
        if (!verificarSesion(respuesta)) return;
        
        Mensaje mensaje = new Mensaje(
            usuarioConectado.getIdUsuario(),
            null,
            Mensaje.TIPO_GLOBAL,
            peticion.getMensaje().getContenido()
        );
        mensaje.setNombreEmisor(usuarioConectado.getNombreUsuario());
        
        // Guardar en BD
        gestorBD. guardarMensaje(mensaje);
        
        // Enviar a todos los clientes conectados
        servidor.enviarMensajeGlobal(mensaje, usuarioConectado.getIdUsuario());
        
        respuesta.setExitosa(true);
        respuesta.setMensajeRespuesta("Mensaje global enviado");
        System.out.println("[Servidor] Mensaje global de " + usuarioConectado. getNombreUsuario());
    }
    
    /**
     * Procesa envío de mensaje a amigos
     */
    private void procesarMensajeAmigos(Peticion peticion, Peticion respuesta) {
        if (!verificarSesion(respuesta)) return;
        
        Mensaje mensaje = new Mensaje(
            usuarioConectado.getIdUsuario(),
            null,
            Mensaje.TIPO_AMIGOS,
            peticion.getMensaje().getContenido()
        );
        mensaje.setNombreEmisor(usuarioConectado.getNombreUsuario());
        
        // Guardar en BD
        gestorBD. guardarMensaje(mensaje);
        
        // Obtener IDs de amigos y enviar
        List<Integer> idsAmigos = gestorBD.obtenerIdsAmigos(usuarioConectado.getIdUsuario());
        servidor.enviarMensajeAAmigos(mensaje, idsAmigos);
        
        respuesta. setExitosa(true);
        respuesta.setMensajeRespuesta("Mensaje enviado a " + idsAmigos.size() + " amigos");
        System.out.println("[Servidor] Mensaje a amigos de " + usuarioConectado.getNombreUsuario());
    }
    
    /**
     * Procesa envío de mensaje privado
     */
    private void procesarMensajePrivado(Peticion peticion, Peticion respuesta) {
        if (!verificarSesion(respuesta)) return;
        
        String nombreReceptor = peticion.getParametro();
        Usuario receptor = gestorBD.obtenerUsuarioPorNombre(nombreReceptor);
        
        if (receptor == null) {
            respuesta.setExitosa(false);
            respuesta.setMensajeRespuesta("Usuario destinatario no encontrado");
            return;
        }
        
        // Verificar que son amigos
        if (!gestorBD.sonAmigos(usuarioConectado.getIdUsuario(), receptor.getIdUsuario())) {
            respuesta. setExitosa(false);
            respuesta.setMensajeRespuesta("Solo puedes enviar mensajes privados a tus amigos");
            return;
        }
        
        Mensaje mensaje = new Mensaje(
            usuarioConectado.getIdUsuario(),
            receptor.getIdUsuario(),
            Mensaje.TIPO_PRIVADO,
            peticion.getMensaje().getContenido()
        );
        mensaje.setNombreEmisor(usuarioConectado.getNombreUsuario());
        mensaje.setNombreReceptor(receptor.getNombreUsuario());
        
        // Guardar en BD
        gestorBD.guardarMensaje(mensaje);
        
        // Enviar al receptor si está conectado
        servidor.enviarMensajePrivado(mensaje, receptor.getIdUsuario());
        
        respuesta.setExitosa(true);
        respuesta.setMensajeRespuesta("Mensaje privado enviado a " + nombreReceptor);
        System.out.println("[Servidor] Mensaje privado de " + usuarioConectado.getNombreUsuario() + 
                         " a " + nombreReceptor);
    }
    
    /**
     * Procesa solicitud de historial de conversación
     */
    private void procesarHistorial(Peticion peticion, Peticion respuesta) {
        if (!verificarSesion(respuesta)) return;
        
        String nombreAmigo = peticion.getParametro();
        Usuario amigo = gestorBD. obtenerUsuarioPorNombre(nombreAmigo);
        
        if (amigo == null) {
            respuesta.setExitosa(false);
            respuesta.setMensajeRespuesta("Usuario no encontrado");
            return;
        }
        
        List<Mensaje> historial = gestorBD. obtenerHistorialConversacion(
            usuarioConectado.getIdUsuario(),
            amigo.getIdUsuario()
        );
        
        respuesta. setExitosa(true);
        respuesta.setListaMensajes(historial);
        respuesta.setMensajeRespuesta("Historial con " + nombreAmigo + ": " + historial.size() + " mensajes");
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Verifica que el usuario ha iniciado sesión
     */
    private boolean verificarSesion(Peticion respuesta) {
        if (usuarioConectado == null) {
            respuesta.setExitosa(false);
            respuesta.setMensajeRespuesta("Debe iniciar sesión primero");
            return false;
        }
        return true;
    }
    
    /**
     * Envía una respuesta al cliente
     */
    public synchronized void enviarRespuesta(Peticion respuesta) {
        try {
            salida.writeObject(respuesta);
            salida.flush();
            salida.reset(); // Importante para evitar caché de objetos
        } catch (IOException e) {
            System.err.println("[Servidor] Error al enviar respuesta: " + e.getMessage());
        }
    }
    
    /**
     * Envía una notificación de mensaje al cliente
     */
    public void enviarNotificacion(Mensaje mensaje) {
        Peticion notificacion = new Peticion(Operacion.NOTIFICACION_MENSAJE);
        notificacion.setMensaje(mensaje);
        notificacion.setExitosa(true);
        enviarRespuesta(notificacion);
    }
    
    /**
     * Desconecta al cliente y libera recursos
     */
    private void desconectar() {
        try {
            if (usuarioConectado != null) {
                servidor.eliminarClienteConectado(usuarioConectado.getIdUsuario());
            }
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("[Servidor] Error al cerrar conexión: " + e. getMessage());
        }
    }
    
    /**
     * Obtiene el usuario conectado en esta sesión
     */
    public Usuario getUsuarioConectado() {
        return usuarioConectado;
    }
}