package ej6;

import java.io.*;
import java.net.*;

public class ServidorHogwarts {
    public static void main(String[] args) {
        InventarioVaritas inventario = new InventarioVaritas();
        try (ServerSocket server = new ServerSocket(6000)) {
            while (true) {
                Socket cliente = server.accept();
                new Thread(() -> {
                    try (PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {
                        String v = inventario.entregarVarita();
                        out.println(v != null ? "Has recibido: " + v : "No hay varitas disponibles");
                    } catch (IOException e) { e.printStackTrace(); }
                }).start();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}