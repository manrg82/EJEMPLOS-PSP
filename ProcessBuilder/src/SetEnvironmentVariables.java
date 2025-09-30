import java.io.*;

/**
 * Configurar variables de entorno antes de ejecutar el proceso
 */
public class SetEnvironmentVariables {
    public static void main(String[] args) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "echo %MY_VAR%");
        processBuilder.environment().put("MY_VAR", "Hola desde ProcessBuilder");
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
