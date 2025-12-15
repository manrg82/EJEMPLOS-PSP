package cliente;

import modelo. Mensaje;
import modelo.Usuario;

import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona la interfaz de usuario por consola. 
 * Muestra menús y recoge la entrada del usuario. 
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class InterfazCliente {
    
    private Scanner scanner;
    
    /**
     * Constructor de la interfaz
     */
    public InterfazCliente() {
        this.scanner = new Scanner(System. in);
    }
    
    /**
     * Muestra el menú inicial (antes de login)
     */
    public void mostrarMenuInicial() {
        System.out.println("\n========================================");
        System.out.println("       CHAT MULTIHILO - MENÚ");
        System.out.println("========================================");
        System.out. println("1. Registrar nuevo usuario");
        System.out. println("2. Iniciar sesión");
        System.out. println("0. Salir");
        System.out.println("========================================");
        System.out.print("Seleccione opción: ");
    }
    
    /**
     * Muestra el menú principal (después de login)
     * @param nombreUsuario Nombre del usuario conectado
     */
    public void mostrarMenuPrincipal(String nombreUsuario) {
        System.out.println("\n========================================");
        System.out. println("   Bienvenido, " + nombreUsuario);
        System.out.println("========================================");
        System.out. println("--- USUARIOS ---");
        System.out.println("3. Cerrar sesión (Logout)");
        System.out. println("4. Dar de baja mi cuenta");
        System.out.println("5. Ver lista de usuarios");
        System.out.println("--- AMIGOS ---");
        System.out.println("6. Añadir amigo");
        System.out. println("7. Eliminar amigo");
        System.out. println("8. Ver mis amigos");
        System.out.println("--- MENSAJES ---");
        System.out.println("9. Enviar mensaje global");
        System.out.println("10. Enviar mensaje a amigos");
        System.out.println("11. Enviar mensaje privado");
        System.out. println("12. Ver historial con amigo");
        System.out.println("========================================");
        System.out.print("Seleccione opción: ");
    }
    
    /**
     * Lee una opción numérica del usuario
     * @return Opción seleccionada
     */
    public int leerOpcion() {
        try {
            String linea = scanner.nextLine().trim();
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Lee una línea de texto
     * @param mensaje Mensaje a mostrar
     * @return Texto introducido
     */
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    
    /**
     * Solicita datos para registro de usuario
     * @return Usuario con los datos introducidos
     */
    public Usuario solicitarDatosRegistro() {
        System.out.println("\n--- REGISTRO DE NUEVO USUARIO ---");
        String nombre = leerTexto("Nombre de usuario: ");
        String password = leerTexto("Contraseña: ");
        String email = leerTexto("Email:  ");
        return new Usuario(nombre, password, email);
    }
    
    /**
     * Solicita datos para login
     * @return Usuario con credenciales
     */
    public Usuario solicitarDatosLogin() {
        System.out.println("\n--- INICIAR SESIÓN ---");
        String nombre = leerTexto("Nombre de usuario: ");
        String password = leerTexto("Contraseña: ");
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombre);
        usuario.setContrasena(password);
        return usuario;
    }
    
    /**
     * Solicita el nombre de un amigo
     * @param accion Acción a realizar (añadir/eliminar/etc.)
     * @return Nombre del amigo
     */
    public String solicitarNombreAmigo(String accion) {
        return leerTexto("Nombre del usuario a " + accion + ": ");
    }
    
    /**
     * Solicita el contenido de un mensaje
     * @return Contenido del mensaje
     */
    public String solicitarMensaje() {
        return leerTexto("Escribe tu mensaje:  ");
    }
    
    /**
     * Solicita confirmación para una acción
     * @param mensaje Mensaje de confirmación
     * @return true si confirma
     */
    public boolean solicitarConfirmacion(String mensaje) {
        String respuesta = leerTexto(mensaje + " (s/n): ");
        return respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si");
    }
    
    /**
     * Muestra un mensaje de información
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println("\n>> " + mensaje);
    }
    
    /**
     * Muestra un mensaje de error
     * @param mensaje Mensaje de error
     */
    public void mostrarError(String mensaje) {
        System.out.println("\n[ERROR] " + mensaje);
    }
    
    /**
     * Muestra un mensaje de éxito
     * @param mensaje Mensaje de éxito
     */
    public void mostrarExito(String mensaje) {
        System.out.println("\n[OK] " + mensaje);
    }
    
    /**
     * Muestra la lista de usuarios
     * @param usuarios Lista de usuarios
     */
    public void mostrarListaUsuarios(List<Usuario> usuarios) {
        System.out.println("\n--- LISTA DE USUARIOS ---");
        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        
        System.out.println("Total:  " + usuarios.size() + " usuarios\n");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            System.out.println((i + 1) + ". " + u.getNombreUsuario() + 
                             " (" + u.getEmail() + ")");
        }
        System.out.println("-------------------------");
    }
    
    /**
     * Muestra la lista de amigos
     * @param amigos Lista de amigos
     */
    public void mostrarListaAmigos(List<Usuario> amigos) {
        System.out.println("\n--- MIS AMIGOS ---");
        if (amigos == null || amigos.isEmpty()) {
            System.out.println("No tienes amigos añadidos.");
            return;
        }
        
        System. out.println("Total: " + amigos.size() + " amigos\n");
        for (int i = 0; i < amigos.size(); i++) {
            Usuario a = amigos.get(i);
            System.out.println((i + 1) + ". " + a.getNombreUsuario());
        }
        System.out.println("------------------");
    }
    
    /**
     * Muestra el historial de mensajes con un amigo
     * @param mensajes Lista de mensajes
     * @param nombreAmigo Nombre del amigo
     */
    public void mostrarHistorial(List<Mensaje> mensajes, String nombreAmigo) {
        System.out.println("\n--- HISTORIAL CON " + nombreAmigo. toUpperCase() + " ---");
        if (mensajes == null || mensajes.isEmpty()) {
            System.out.println("No hay mensajes en el historial.");
            return;
        }
        
        System.out.println("Total: " + mensajes.size() + " mensajes\n");
        for (Mensaje m : mensajes) {
            String fecha = (m.getFechaEnvio() != null) ? 
                          m.getFechaEnvio().toString().substring(0, 19) : "Sin fecha";
            System.out. println("[" + fecha + "] " + m.getNombreEmisor() + ": " + m.getContenido());
        }
        System.out.println("--------------------------------");
    }
    
    /**
     * Muestra una notificación de mensaje entrante
     * @param mensaje Mensaje recibido
     */
    public void mostrarNotificacionMensaje(Mensaje mensaje) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out. println("║  NUEVO MENSAJE [" + mensaje.getTipo() + "]");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  De: " + mensaje.getNombreEmisor());
        System.out.println("║  " + mensaje.getContenido());
        System.out.println("╚════════════════════════════════════╝");
    }
    
    /**
     * Limpia la pantalla (simulado)
     */
    public void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    /**
     * Muestra mensaje de despedida
     */
    public void mostrarDespedida() {
        System.out.println("\n========================================");
        System.out. println("   ¡Hasta pronto!  Gracias por usar");
        System.out.println("        el Chat Multihilo");
        System.out.println("========================================\n");
    }
    
    /**
     * Pausa la ejecución esperando que el usuario pulse Enter
     */
    public void pausar() {
        leerTexto("\nPulse Enter para continuar...");
    }
    
    /**
     * Cierra el scanner
     */
    public void cerrar() {
        if (scanner != null) {
            scanner.close();
        }
    }
}