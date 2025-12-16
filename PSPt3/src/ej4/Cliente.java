package ej4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Cliente implements Runnable {

    @Override
    public void run() {
        String host = "localhost";
        int puerto = 5000;

        try (Socket socket = new Socket(host, puerto);
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
             DataInputStream entrada = new DataInputStream(socket.getInputStream())) {

            Random random = new Random();
            int peticion = random.nextInt(10) + 1;

            salida.writeInt(peticion);

            int recibido = entrada.readInt();
            
            System.out.println("Cliente solicitó: " + peticion + " - Recibió: " + recibido);

        } catch (IOException e) {
            System.out.println("Cliente no pudo conectar (Posiblemente se agotó el stock).");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(new Cliente()).start();
        }
    }
}
