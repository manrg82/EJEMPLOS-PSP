
import java.io.*;

public class RunJavaClass {
    
	public static void main(String[] args) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "HelloWorld");
        processBuilder.directory(new File("bin")); // Ruta a la clase
        
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        		process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}

