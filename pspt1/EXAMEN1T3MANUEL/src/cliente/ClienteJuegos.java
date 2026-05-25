package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClienteJuegos {
    public static void main(String[] args) {
        System.out.println("Iniciando cliente... conectado al gestor de servidores.");

        // Try-with-resources asegura que el socket y los streams se cierren automáticamente
        try (Socket socket = new Socket("localhost", 6666);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // --- 1. SOLICITAR JUEGOS ---
            System.out.println("Enviada petición: Peticion [tipo=JUEGOS]");
            out.println("JUEGOS");
            
            // Leer respuesta
            String respuestaJuegos = in.readLine();
            System.out.println(respuestaJuegos);
            System.out.println("---");

            // --- 2. PROCESAR RESPUESTA Y RESERVAR ---
            // Limpiamos el texto para quedarnos solo con los nombres separados por comas
            String listaPura = respuestaJuegos.replace("Juegos recibidos: [", "").replace("]", "");
            String[] juegos = listaPura.split(", ");

            if (juegos.length > 0 && !juegos[0].isEmpty()) {
                System.out.println("Generando reserva aleatoria...");
                
                Random rand = new Random();
                // Escoger un juego aleatorio del array
                String juegoElegido = juegos[rand.nextInt(juegos.length)];
                // Generar plazas aleatorias entre 1 y 5
                int plazasAleatorias = rand.nextInt(5) + 1; 

                System.out.println("Enviada petición: Peticion [tipo=RESERVAR, Juego=" + juegoElegido + ", Plazas=" + plazasAleatorias + "]");
                
                // Enviamos la petición uniendo los datos con ";"
                out.println("RESERVAR;" + juegoElegido + ";" + plazasAleatorias);

                // Leer la confirmación final
                String respuestaReserva = in.readLine();
                System.out.println("Respuesta recibida: " + respuestaReserva);
            } else {
                System.out.println("No hay juegos disponibles para reservar.");
            }

            // Desconexión limpia
            out.println("SALIR");

        } catch (IOException e) {
            System.out.println("No se pudo conectar al servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}