package ej1;

public class CalculadoraGPU {

    public static void main(String[] args) {
        // 1. Comprobamos que recibimos los 3 parámetros necesarios (ID, núcleos, frecuencia)
        if (args.length < 3) {
            throw new IllegalArgumentException("Error: Se requieren 3 parámetros (ID, núcleos, frecuencia).");
        }

        try {
            // 2. Parseamos los argumentos recibidos como texto a enteros
            int id = Integer.parseInt(args[0]);
            int nucleos = Integer.parseInt(args[1]);
            int frecuencia = Integer.parseInt(args[2]);

            // 3. Aplicamos la fórmula de rendimiento
            int rendimiento = (nucleos * frecuencia) / 1000;

            // 4. Imprimimos el resultado. 
            // OJO: Esto no saldrá por consola, el ProcessBuilder del padre lo redirigirá al .txt
            System.out.println("Núcleos: " + nucleos + ", Frecuencia: " + frecuencia + ", Rendimiento: " + rendimiento);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Los parámetros de núcleos y frecuencia deben ser números enteros.");
        }
    }
}