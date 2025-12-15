package modelo;

import java.io. Serializable;
import java.sql.Timestamp;

/**
 * Clase que representa un mensaje en el sistema de chat.
 * Soporta tres tipos:  GLOBAL, AMIGOS y PRIVADO.
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class Mensaje implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Constantes para los tipos de mensaje
    public static final String TIPO_GLOBAL = "GLOBAL";
    public static final String TIPO_AMIGOS = "AMIGOS";
    public static final String TIPO_PRIVADO = "PRIVADO";
    
    // Atributos que mapean la tabla MENSAJES
    private int idMensaje;
    private int idEmisor;
    private Integer idReceptor; // Puede ser null para GLOBAL y AMIGOS
    private String tipo;
    private String contenido;
    private Timestamp fechaEnvio;
    
    // Atributos auxiliares para mostrar información
    private String nombreEmisor;
    private String nombreReceptor;
    
    /**
     * Constructor vacío necesario para serialización
     */
    public Mensaje() {
    }
    
    /**
     * Constructor para crear un nuevo mensaje
     * @param idEmisor ID del usuario que envía
     * @param idReceptor ID del receptor (null si es GLOBAL o AMIGOS)
     * @param tipo Tipo de mensaje (GLOBAL, AMIGOS, PRIVADO)
     * @param contenido Contenido del mensaje
     */
    public Mensaje(int idEmisor, Integer idReceptor, String tipo, String contenido) {
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.tipo = tipo;
        this.contenido = contenido;
    }
    
    // ==================== GETTERS Y SETTERS ====================
    
    public int getIdMensaje() {
        return idMensaje;
    }
    
    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }
    
    public int getIdEmisor() {
        return idEmisor;
    }
    
    public void setIdEmisor(int idEmisor) {
        this.idEmisor = idEmisor;
    }
    
    public Integer getIdReceptor() {
        return idReceptor;
    }
    
    public void setIdReceptor(Integer idReceptor) {
        this.idReceptor = idReceptor;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }
    
    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    
    public String getNombreEmisor() {
        return nombreEmisor;
    }
    
    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }
    
    public String getNombreReceptor() {
        return nombreReceptor;
    }
    
    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }
    
    @Override
    public String toString() {
        String fecha = (fechaEnvio != null) ? fechaEnvio.toString() : "Sin fecha";
        return "[" + fecha + "] " + nombreEmisor + ": " + contenido;
    }
}