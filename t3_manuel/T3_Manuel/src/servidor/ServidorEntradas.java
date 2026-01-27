package servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comun.Hilo;

public class ServidorEntradas {

	public static void main(String[] args) {
		Inventario inv=Inventario.getInstance();
		try {
			ServerSocket svSK=new ServerSocket(5555);
			System.out.println("Servidor en marcha");
			Socket cliente=svSK.accept();
			Hilo h=new Hilo();
			h.run();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
