package utils;

/**
 * Clase de configuración para la conexión a Oracle en Docker. 
 */
public class ConfigDB {
    
    // Driver JDBC de Oracle
    public static final String DRIVER = "oracle.jdbc.OracleDriver";
    
    // URL de conexión - SIN ESPACIOS
    public static final String URL = "jdbc:oracle:thin:@//localhost:1521/free";
    
    // Credenciales
    public static final String USUARIO = "CHAT";
    public static final String PASSWORD = "CHAT";
    
    // Configuración del servidor de chat
    public static final int PUERTO_SERVIDOR = 5000;
    public static final String HOST_SERVIDOR = "localhost";
    
    private ConfigDB() {
    }
}