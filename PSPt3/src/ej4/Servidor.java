package ej4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 5000;
        
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            while (Maquina.getInstancia().getStock() > 0) {
                Socket socketCliente = servidor.accept();
                Thread hilo = new Thread(new ManejadorCliente(socketCliente));
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}