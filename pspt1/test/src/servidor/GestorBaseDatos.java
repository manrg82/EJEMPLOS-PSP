package servidor;

import modelo. Mensaje;
import modelo.Usuario;
import utils.ConfigDB;

import java. sql. Connection;
import java.sql. DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona todas las operaciones con la base de datos.
 * Implementa el patrón DAO (Data Access Object) para separar
 * la lógica de acceso a datos. 
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class GestorBaseDatos {
    
    // Instancia única (Singleton)
    private static GestorBaseDatos instancia;
    
    /**
     * Constructor privado (Singleton)
     */
    private GestorBaseDatos() {
        try {
            // Cargar el driver de Oracle
            Class.forName(ConfigDB.DRIVER);
            System.out.println("[BD] Driver Oracle cargado correctamente");
        } catch (ClassNotFoundException e) {
            System. err.println("[BD] Error al cargar driver: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la instancia única del gestor
     * @return Instancia del GestorBaseDatos
     */
    public static synchronized GestorBaseDatos getInstancia() {
        if (instancia == null) {
            instancia = new GestorBaseDatos();
        }
        return instancia;
    }
    
    /**
     * Establece conexión con la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error de conexión
     */
    private Connection getConexion() throws SQLException {
        return DriverManager.getConnection(
            ConfigDB.URL, 
            ConfigDB. USUARIO, 
            ConfigDB. PASSWORD
        );
    }
    
    // ==================== OPERACIONES DE USUARIO ====================
    
    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario Usuario a registrar
     * @return true si el registro fue exitoso
     */
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO USUARIOS (nombre_usuario, contrasena, email) VALUES (?, ?, ?)";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getContrasena());
            pstmt.setString(3, usuario. getEmail());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System. err.println("[BD] Error al registrar usuario: " + e. getMessage());
            return false;
        }
    }
    
    /**
     * Valida las credenciales de un usuario para login
     * @param nombreUsuario Nombre del usuario
     * @param contrasena Contraseña
     * @return Usuario si las credenciales son válidas, null en caso contrario
     */
    public Usuario validarLogin(String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM USUARIOS WHERE nombre_usuario = ? AND contrasena = ?  AND activo = 1";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, contrasena);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearUsuario(rs);
            }
            
        } catch (SQLException e) {
            System. err.println("[BD] Error en login: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Da de baja un usuario (lo marca como inactivo)
     * @param idUsuario ID del usuario a dar de baja
     * @return true si la baja fue exitosa
     */
    public boolean bajaUsuario(int idUsuario) {
        String sql = "UPDATE USUARIOS SET activo = 0 WHERE id_usuario = ?";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt. setInt(1, idUsuario);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System. err.println("[BD] Error en baja usuario: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene la lista de todos los usuarios activos
     * @return Lista de usuarios
     */
    public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIOS WHERE activo = 1 ORDER BY nombre_usuario";
        
        try (Connection conn = getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
            
        } catch (SQLException e) {
            System.err. println("[BD] Error al obtener usuarios: " + e. getMessage());
        }
        return usuarios;
    }
    
    /**
     * Obtiene un usuario por su nombre
     * @param nombreUsuario Nombre del usuario
     * @return Usuario encontrado o null
     */
    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        String sql = "SELECT * FROM USUARIOS WHERE nombre_usuario = ?  AND activo = 1";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn. prepareStatement(sql)) {
            
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearUsuario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("[BD] Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }
    
    // ==================== OPERACIONES DE AMIGOS ====================
    
    /**
     * Añade un amigo a la lista del usuario
     * @param idUsuario ID del usuario
     * @param idAmigo ID del amigo a añadir
     * @return true si se añadió correctamente
     */
    public boolean anadirAmigo(int idUsuario, int idAmigo) {
        // Insertamos la relación en ambas direcciones para que sea bidireccional
        String sql = "INSERT INTO AMIGOS (id_usuario, id_amigo) VALUES (?, ?)";
        
        try (Connection conn = getConexion()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Relación usuario -> amigo
                pstmt. setInt(1, idUsuario);
                pstmt.setInt(2, idAmigo);
                pstmt.executeUpdate();
                
                // Relación amigo -> usuario (bidireccional)
                pstmt.setInt(1, idAmigo);
                pstmt.setInt(2, idUsuario);
                pstmt. executeUpdate();
                
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            
        } catch (SQLException e) {
            System.err.println("[BD] Error al añadir amigo: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un amigo de la lista del usuario
     * @param idUsuario ID del usuario
     * @param idAmigo ID del amigo a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarAmigo(int idUsuario, int idAmigo) {
        String sql = "DELETE FROM AMIGOS WHERE (id_usuario = ? AND id_amigo = ?) OR (id_usuario = ? AND id_amigo = ?)";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idAmigo);
            pstmt. setInt(3, idAmigo);
            pstmt.setInt(4, idUsuario);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("[BD] Error al eliminar amigo: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene la lista de amigos de un usuario
     * @param idUsuario ID del usuario
     * @return Lista de amigos
     */
    public List<Usuario> obtenerAmigos(int idUsuario) {
        List<Usuario> amigos = new ArrayList<>();
        String sql = "SELECT u.* FROM USUARIOS u " +
                     "INNER JOIN AMIGOS a ON u.id_usuario = a.id_amigo " +
                     "WHERE a.id_usuario = ? AND u.activo = 1 " +
                     "ORDER BY u.nombre_usuario";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt. executeQuery();
            
            while (rs.next()) {
                amigos.add(mapearUsuario(rs));
            }
            
        } catch (SQLException e) {
            System.err. println("[BD] Error al obtener amigos: " + e.getMessage());
        }
        return amigos;
    }
    
    /**
     * Verifica si dos usuarios son amigos
     * @param idUsuario ID del usuario
     * @param idAmigo ID del posible amigo
     * @return true si son amigos
     */
    public boolean sonAmigos(int idUsuario, int idAmigo) {
        String sql = "SELECT COUNT(*) FROM AMIGOS WHERE id_usuario = ? AND id_amigo = ?";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idAmigo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("[BD] Error al verificar amistad: " + e.getMessage());
        }
        return false;
    }
    
    // ==================== OPERACIONES DE MENSAJES ====================
    
    /**
     * Guarda un mensaje en la base de datos
     * @param mensaje Mensaje a guardar
     * @return true si se guardó correctamente
     */
    public boolean guardarMensaje(Mensaje mensaje) {
        String sql = "INSERT INTO MENSAJES (id_emisor, id_receptor, tipo, contenido) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, mensaje.getIdEmisor());
            
            if (mensaje.getIdReceptor() != null) {
                pstmt. setInt(2, mensaje.getIdReceptor());
            } else {
                pstmt. setNull(2, Types.INTEGER);
            }
            
            pstmt.setString(3, mensaje.getTipo());
            pstmt.setString(4, mensaje.getContenido());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("[BD] Error al guardar mensaje: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene el historial de conversación entre dos usuarios
     * @param idUsuario1 ID del primer usuario
     * @param idUsuario2 ID del segundo usuario
     * @return Lista de mensajes ordenados por fecha
     */
    public List<Mensaje> obtenerHistorialConversacion(int idUsuario1, int idUsuario2) {
        List<Mensaje> mensajes = new ArrayList<>();
        String sql = "SELECT m.*, " +
                     "e.nombre_usuario as nombre_emisor, " +
                     "r.nombre_usuario as nombre_receptor " +
                     "FROM MENSAJES m " +
                     "INNER JOIN USUARIOS e ON m.id_emisor = e.id_usuario " +
                     "LEFT JOIN USUARIOS r ON m.id_receptor = r.id_usuario " +
                     "WHERE m.tipo = 'PRIVADO' AND " +
                     "((m.id_emisor = ?  AND m.id_receptor = ?) OR " +
                     "(m.id_emisor = ? AND m.id_receptor = ?)) " +
                     "ORDER BY m.fecha_envio ASC";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario1);
            pstmt.setInt(2, idUsuario2);
            pstmt.setInt(3, idUsuario2);
            pstmt.setInt(4, idUsuario1);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs. next()) {
                mensajes.add(mapearMensaje(rs));
            }
            
        } catch (SQLException e) {
            System.err. println("[BD] Error al obtener historial: " + e.getMessage());
        }
        return mensajes;
    }
    
    /**
     * Obtiene los IDs de los amigos de un usuario
     * @param idUsuario ID del usuario
     * @return Lista de IDs de amigos
     */
    public List<Integer> obtenerIdsAmigos(int idUsuario) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_amigo FROM AMIGOS WHERE id_usuario = ?";
        
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs. next()) {
                ids.add(rs.getInt("id_amigo"));
            }
            
        } catch (SQLException e) {
            System.err.println("[BD] Error al obtener IDs amigos: " + e. getMessage());
        }
        return ids;
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Mapea un ResultSet a un objeto Usuario
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombreUsuario(rs.getString("nombre_usuario"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setEmail(rs.getString("email"));
        usuario.setActivo(rs.getInt("activo") == 1);
        usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return usuario;
    }
    
    /**
     * Mapea un ResultSet a un objeto Mensaje
     */
    private Mensaje mapearMensaje(ResultSet rs) throws SQLException {
        Mensaje mensaje = new Mensaje();
        mensaje.setIdMensaje(rs.getInt("id_mensaje"));
        mensaje.setIdEmisor(rs.getInt("id_emisor"));
        
        int idReceptor = rs.getInt("id_receptor");
        if (! rs.wasNull()) {
            mensaje.setIdReceptor(idReceptor);
        }
        
        mensaje.setTipo(rs.getString("tipo"));
        mensaje.setContenido(rs.getString("contenido"));
        mensaje.setFechaEnvio(rs.getTimestamp("fecha_envio"));
        mensaje.setNombreEmisor(rs. getString("nombre_emisor"));
        mensaje.setNombreReceptor(rs.getString("nombre_receptor"));
        return mensaje;
    }
}