package ej7;

import java.io.*;
import java.net.*;

public class ServidorLibreria {
    public static void main(String[] args) {
        InventarioLibros inv = new InventarioLibros();
        try (ServerSocket server = new ServerSocket(7000)) {
            while (true) {
                Socket cliente = server.accept();
                new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                         PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {
                        String titulo = in.readLine();
                        int cant = Integer.parseInt(in.readLine());
                        out.println(inv.realizarPedido(titulo, cant));
                    } catch (IOException | NumberFormatException e) { e.printStackTrace(); }
                }).start();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}