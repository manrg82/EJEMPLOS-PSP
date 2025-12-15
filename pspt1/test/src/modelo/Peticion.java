package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula el protocolo de comunicación cliente-servidor.
 * Contiene la operación a realizar y todos los datos necesarios. 
 * 
 * Protocolo de comunicación: 
 * - Cliente envía Peticion con operación y datos necesarios
 * - Servidor procesa y devuelve Peticion con resultado
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class Peticion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Enumeración con todas las operaciones disponibles
     */
    public enum Operacion {
        USUARIO_NUEVO,          // 1. Registrar nuevo usuario
        LOGIN,                  // 2. Iniciar sesión
        LOGOUT,                 // 3. Cerrar sesión
        BAJA_USUARIO,           // 4. Dar de baja usuario
        LISTA_USUARIOS,         // 5. Listar todos los usuarios
        ANADIR_AMIGO,           // 6. Añadir amigo
        BAJA_AMIGO,             // 7. Eliminar amigo
        LISTA_AMIGOS,           // 8. Listar amigos
        MENSAJE_GLOBAL,         // 9. Enviar mensaje global
        MENSAJE_AMIGOS,         // 10. Enviar mensaje a amigos
        MENSAJE_PRIVADO,        // 11. Enviar mensaje privado
        HISTORIAL_CONVERSACION, // 12. Ver historial con amigo
        NOTIFICACION_MENSAJE    // Notificación de mensaje entrante
    }
    
    // Operación a realizar
    private Operacion operacion;
    
    // Datos de la petición
    private Usuario usuario;            // Usuario que hace la petición
    private Mensaje mensaje;            // Mensaje a enviar (si aplica)
    private String parametro;           // Parámetro adicional (nombre amigo, etc.)
    
    // Datos de la respuesta
    private boolean exitosa;            // Si la operación fue exitosa
    private String mensajeRespuesta;    // Mensaje descriptivo del resultado
    private List<Usuario> listaUsuarios; // Lista de usuarios (para listados)
    private List<Mensaje> listaMensajes; // Lista de mensajes (para historial)
    
    /**
     * Constructor vacío
     */
    public Peticion() {
        this.listaUsuarios = new ArrayList<>();
        this.listaMensajes = new ArrayList<>();
    }
    
    /**
     * Constructor con operación
     * @param operacion Tipo de operación a realizar
     */
    public Peticion(Operacion operacion) {
        this();
        this.operacion = operacion;
    }
    
    /**
     * Constructor con operación y usuario
     * @param operacion Tipo de operación
     * @param usuario Usuario que realiza la petición
     */
    public Peticion(Operacion operacion, Usuario usuario) {
        this(operacion);
        this.usuario = usuario;
    }
    
    // ==================== GETTERS Y SETTERS ====================
    
    public Operacion getOperacion() {
        return operacion;
    }
    
    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Mensaje getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getParametro() {
        return parametro;
    }
    
    public void setParametro(String parametro) {
        this.parametro = parametro;
    }
    
    public boolean isExitosa() {
        return exitosa;
    }
    
    public void setExitosa(boolean exitosa) {
        this.exitosa = exitosa;
    }
    
    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }
    
    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }
    
    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    
    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    public List<Mensaje> getListaMensajes() {
        return listaMensajes;
    }
    
    public void setListaMensajes(List<Mensaje> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }
}