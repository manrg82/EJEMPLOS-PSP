package ej8;

import java.io.*;
import java.net.*;

public class ServidorFicheros {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1500)) {
            while (true) {
                Socket cliente = server.accept();
                new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                         PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {
                        File f = new File(in.readLine());
                        if (f.exists() && f.isFile()) {
                            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                                String linea;
                                while ((linea = reader.readLine()) != null) out.println(linea);
                            }
                        } else {
                            out.println("Error: No existe el fichero");
                        }
                    } catch (IOException e) { e.printStackTrace(); }
                }).start();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}