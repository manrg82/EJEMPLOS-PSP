package ej7;

import java.io.*;
import java.net.*;

public class ClienteLibreria {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 7000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            String tituloLibro = "Harry Potter y la piedra filosofal";
            int cantidad = 5;
            
            out.println(tituloLibro);
            out.println(cantidad);
            
            String respuesta = in.readLine();
            System.out.println("Respuesta de la libreria: " + respuesta);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}