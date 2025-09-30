import java.io.*;

/**
 * Redirigir la salida a un archivo
 */
public class RedirectOutputToFile {
    public static void main(String[] args) throws IOException {
        File outputFile = new File("files/output.txt");
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "echo Esto es un ejemplo");
        processBuilder.redirectOutput(outputFile);

        Process process = processBuilder.start();
        System.out.println("La salida se ha guardado en output.txt");
    }
}
