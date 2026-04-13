package triangulos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Lanzador {

    public static void main(String[] args) {
        // 1. Crear el directorio "files" si no existe
        File directorio = new File("files");
        if (!directorio.exists()) {
            directorio.mkdir();
        }

        // 2. Usar ArrayList y crear los triángulos en el main (Corrección del profesor)
        ArrayList<Triangulo> listaTriangulos = new ArrayList<>();
        listaTriangulos.add(new Triangulo(1, 4, 5));
        listaTriangulos.add(new Triangulo(2, 7, 7));
        listaTriangulos.add(new Triangulo(3, 8, 4));
        listaTriangulos.add(new Triangulo(4, 10, 5));
        listaTriangulos.add(new Triangulo(5, 2, 10));
        listaTriangulos.add(new Triangulo(6, 6, 8));

        ArrayList<Process> procesos = new ArrayList<>();

        // 3. Lanzar los procesos usando la longitud del ArrayList (listaTriangulos.size())
        for (int i = 0; i < listaTriangulos.size(); i++) {
            Triangulo t = listaTriangulos.get(i);

            // IMPORTANTE: Pasamos la base y altura como Strings al hijo
            ProcessBuilder pb = new ProcessBuilder(
                    "java", 
                    "-cp", "bin", 
                    "ejercicio1.ImprimirTriangulo", 
                    String.valueOf(t.getId()), 
                    String.valueOf(t.getBase()), 
                    String.valueOf(t.getAltura())
            );

            // Usamos redirectOutput como indicó el profesor
            File archivoSalida = new File("files/area_triangulo_" + t.getId() + ".txt");
            pb.redirectOutput(archivoSalida);

            try {
                Process proceso = pb.start();
                procesos.add(proceso);
            } catch (IOException e) {
                System.err.println("Error al iniciar el proceso del triángulo " + t.getId());
                e.printStackTrace();
            }
        }

        // 4. Asegurarse de que cada proceso termina y comprobar errores
        for (int i = 0; i < procesos.size(); i++) {
            try {
                Process p = procesos.get(i);
                int exitCode = p.waitFor(); // Esperamos a que termine
                
                // Si el código de salida no es 0, hubo un error
                if (exitCode != 0) {
                    System.err.println("Se produjo un error en el proceso del triángulo " + (i + 1));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Todos los cálculos han finalizado.");
    }
}
