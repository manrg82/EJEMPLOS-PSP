package ej1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class PrincipalGPU {

    public static void main(String[] args) {
        // 1. Creamos el directorio "files" si no existe
        File directorio = new File("files");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // 2. Usamos ArrayList en lugar de arrays primitivos (exigencia del profesor)
        // Guardamos los datos de las gráficas en el formato: "ID,Nucleos,Frecuencia"
        ArrayList<String> datosGraficas = new ArrayList<>();
        datosGraficas.add("1,2560,2424"); // RX 6700 XT
        datosGraficas.add("2,6144,2600"); // RX 9070 XT
        datosGraficas.add("3,5888,2475"); // RTX 4070
        datosGraficas.add("4,3840,2430"); // RX 7800 XT

        // Lista para guardar los procesos en ejecución y comprobarlos luego
        List<Process> procesosActivos = new ArrayList<>();

        // 3. Lanzamos un subproceso por cada gráfica
        for (String datos : datosGraficas) {
            String[] partes = datos.split(",");
            String id = partes[0];
            String nucleos = partes[1];
            String frecuencia = partes[2];

            try {
                // Pasamos los argumentos a la clase en el ProcessBuilder
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", "ej1.CalculadoraGPU", id, nucleos, frecuencia);
                
                // Redireccionamos la salida y los errores a ficheros (evitamos escribir el .txt manualmente)
                pb.redirectOutput(new File("files" + File.separator + "rendimiento_gpu_" + id + ".txt"));
                pb.redirectError(new File("files" + File.separator + "error_gpu_" + id + ".log"));

                // Arrancamos el proceso y lo guardamos en la lista
                Process proceso = pb.start();
                procesosActivos.add(proceso);
                System.out.println("Lanzado proceso para la gráfica ID: " + id);

            } catch (IOException e) {
                System.out.println("Error de E/S al lanzar el proceso " + id + ": " + e.getMessage());
            }
        }

        // 4. Esperamos a que todos terminen y comprobamos errores
        for (int i = 0; i < procesosActivos.size(); i++) {
            Process p = procesosActivos.get(i);
            try {
                p.waitFor(); // Bloquea hasta que el proceso hijo termine
                int estado = p.exitValue();
                
                if (estado == 0) {
                    System.out.println("El proceso " + (i + 1) + " ha terminado correctamente.");
                } else {
                    System.out.println("ATENCIÓN: El proceso " + (i + 1) + " terminó con un error (código " + estado + "). Revisa su archivo de log.");
                }
            } catch (InterruptedException e) {
                System.out.println("El proceso principal fue interrumpido.");
            }
        }
        System.out.println("Todos los cálculos han finalizado.");
    }
}