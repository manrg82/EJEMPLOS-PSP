package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ManejadorCliente implements Runnable {
    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String peticion;
            // Leemos las peticiones del cliente hasta que se desconecte
            while ((peticion = in.readLine()) != null) {
                
                if (peticion.equals("JUEGOS")) {
                    ArrayList<String> disponibles = new ArrayList<>();
                    // Filtramos los que tienen plazas
                    ServidorJuegos.inventario.forEach((juego, plazas) -> {
                        if (plazas > 0) disponibles.add(juego);
                    });
                    out.println("Juegos recibidos: " + disponibles.toString());
                } 
                else if (peticion.startsWith("RESERVAR")) {
                    // Formato esperado: RESERVAR;Nombre del Juego;Cantidad
                    String[] partes = peticion.split(";");
                    if (partes.length == 3) {
                        String juego = partes[1];
                        int plazasSolicitadas = Integer.parseInt(partes[2]);
                        
                        // Bloque sincronizado para evitar condiciones de carrera (Race Conditions)
                        synchronized (ServidorJuegos.inventario) {
                            Integer plazasActuales = ServidorJuegos.inventario.get(juego);
                            
                            if (plazasActuales != null && plazasActuales >= plazasSolicitadas) {
                                // Descontar plazas
                                ServidorJuegos.inventario.put(juego, plazasActuales - plazasSolicitadas);
                                out.println("RESERVA CONFIRMADA. [Juego=" + juego + ", Plazas asignadas=" + plazasSolicitadas + "]");
                            } else {
                                out.println("ERROR: No hay suficientes plazas o el juego no existe.");
                                System.out.println("Intento de reserva fallido para: " + juego);
                            }
                        }
                    }
                } else if (peticion.equals("SALIR")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Conexión finalizada con el cliente.");
        } finally {
            try {
                socket.close(); // Cerramos siempre el socket al terminar
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}