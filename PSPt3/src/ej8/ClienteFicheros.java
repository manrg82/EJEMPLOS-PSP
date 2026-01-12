package ej8;

import java.io.*;
import java.net.*;

public class ClienteFicheros {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1500);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            String nombreFichero = "ejemplo.txt";
            out.println(nombreFichero);
            
            String linea;
            while ((linea = in.readLine()) != null) {
                System.out.println(linea);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}