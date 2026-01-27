package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import comun.Respuesta;
import comun.Solicitud;

public class ClienteEntradas {
	public static void main(String[] args) {
		
		try {
			Socket sk=new Socket("localhost",5555);
			ObjectInputStream in =new ObjectInputStream(sk.getInputStream());
			ObjectOutputStream out=new ObjectOutputStream(sk.getOutputStream());
			out.writeObject(new Solicitud(true,"Teatro",8));
			try {
				Respuesta r1=(Respuesta)in.readObject();
				System.out.println("Lista de Eventos con mas de 1 entrada disponible");
				for(int i=0;i<r1.lista.size();i++) {
					System.out.println(r1.lista.get(i));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			out.writeObject(new Solicitud(false,"Teatro",8));
			try {
				Respuesta r2=(Respuesta)in.readObject();
				System.out.println("Recibidas "+ r2.getNmEntradas()+" del evento: "+r2.getNmEntradas());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			out.writeObject(new Solicitud(false,"Teatro",20));
			try {
				Respuesta r3=(Respuesta)in.readObject();
				System.out.println("Recibidas "+ r3.getNmEntradas()+" del evento: "+r3.getNmEntradas());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			out.writeObject(new Solicitud(true,"Teatro",8));
			try {
				Respuesta r4=(Respuesta)in.readObject();
				System.out.println("Lista de Eventos con mas de 1 entrada disponible");
				for(int i=0;i<r4.lista.size();i++) {
					System.out.println(r4.lista.get(i));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
