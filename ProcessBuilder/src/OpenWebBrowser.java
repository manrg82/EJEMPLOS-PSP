import java.io.*;

/**
 * Usamos ProcessBuilder para abrir el navegador predeterminado en una URL específica
 */
public class OpenWebBrowser {
    public static void main(String[] args) throws IOException {
        ProcessBuilder processBuilder = 
        		new ProcessBuilder("cmd.exe", "/c", "start https://www.google.com");
        processBuilder.start();
        System.out.println("El navegador se ha abierto.");
    }
}
