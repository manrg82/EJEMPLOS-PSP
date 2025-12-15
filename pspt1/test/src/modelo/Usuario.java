package modelo;

import java.io. Serializable;
import java.sql.Timestamp;

/**
 * Clase que representa un usuario del sistema de chat. 
 * Implementa Serializable para transmisión cliente-servidor mediante ObjectStream.
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class Usuario implements Serializable {
    
    // Identificador de versión para serialización
    private static final long serialVersionUID = 1L;
    
    // Atributos que mapean la tabla USUARIOS
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String email;
    private boolean activo;
    private Timestamp fechaRegistro;
    
    /**
     * Constructor vacío necesario para serialización
     */
    public Usuario() {
    }
    
    /**
     * Constructor para registro de nuevo usuario
     * @param nombreUsuario Nombre único del usuario
     * @param contrasena Contraseña del usuario
     * @param email Email único del usuario
     */
    public Usuario(String nombreUsuario, String contrasena, String email) {
        this.nombreUsuario = nombreUsuario;
        this. contrasena = contrasena;
        this.email = email;
        this.activo = true;
    }
    
    /**
     * Constructor completo con todos los campos
     */
    public Usuario(int idUsuario, String nombreUsuario, String contrasena,
                   String email, boolean activo, Timestamp fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }
    
    // ==================== GETTERS Y SETTERS ====================
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    @Override
    public String toString() {
        return nombreUsuario + " (" + email + ") - " + (activo ? "Activo" : "Inactivo");
    }
}