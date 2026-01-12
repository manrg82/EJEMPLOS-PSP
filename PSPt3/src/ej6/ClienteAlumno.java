package ej6;

import java.io.*;
import java.net.*;

public class ClienteAlumno {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            String respuesta = in.readLine();
            System.out.println(respuesta);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}