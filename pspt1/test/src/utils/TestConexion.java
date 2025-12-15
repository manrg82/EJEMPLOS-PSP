package utils;

import java.sql.Connection;
import java.sql. DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase de prueba para verificar la conexión a Oracle Docker
 */
public class TestConexion {
    
    public static void main(String[] args) {
        System.out.println("=== Test de Conexión a Oracle Docker ===\n");
        
        try {
            // Cargar driver
            System. out.println("1. Cargando driver...");
            Class.forName(ConfigDB. DRIVER);
            System.out. println("   OK - Driver cargado\n");
            
            // Conectar
            System.out. println("2. Conectando a: " + ConfigDB.URL);
            System.out.println("   Usuario: " + ConfigDB.USUARIO);
            Connection conn = DriverManager.getConnection(
                ConfigDB.URL, 
                ConfigDB.USUARIO, 
                ConfigDB.PASSWORD
            );
            System.out.println("   OK - Conexión establecida\n");
            
            // Probar consulta
            System.out.println("3. Consultando usuarios...");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIOS");
            
            System.out.println("\n   USUARIOS:");
            System.out.println("   ----------");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("   " + count + ". " + rs.getString("nombre_usuario") + 
                                 " - " + rs.getString("email"));
            }
            
            if (count == 0) {
                System.out.println("   (No hay usuarios - ejecuta los INSERT)");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("\n=== CONEXIÓN EXITOSA ===");
            
        } catch (Exception e) {
            System.err.println("\n   ERROR:  " + e.getMessage());
            e.printStackTrace();
        }
    }
}