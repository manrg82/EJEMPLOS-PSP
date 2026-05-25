package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

import comun.Peticion;
import comun.Respuesta;

public class ClienteReservas {
	public static void main(String[] args) {
		List<String> eventosRecibidos=null;
		try(
			Socket sc1=new Socket("localhost",5555);
			ObjectOutputStream out1 = new ObjectOutputStream(sc1.getOutputStream());
	        ObjectInputStream in1 = new ObjectInputStream(sc1.getInputStream())
		){
			out1.flush();
			System.out.println("conectado al sv");
			Peticion petEvento=new Peticion("EVENTOS");
			System.out.println("Enviada peticion: "+ petEvento.toString());
			out1.writeObject(petEvento);//manda la peticion al sv
			out1.flush();
			Respuesta resEvento=(Respuesta) in1.readObject();//lee la respuesta y la castea porque viene como Object
			System.out.println(resEvento.toString());
			eventosRecibidos=resEvento.getEventosDisponibles();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(eventosRecibidos!=null && !eventosRecibidos.isEmpty()) {
			Random rand=new Random();
			String eventoElegido=eventosRecibidos.get(rand.nextInt(eventosRecibidos.size()));//elige un evento random de la lista
			int cantidadElegida= rand.nextInt(10)+1;//elige una cantidad random de entradas
			try (
				Socket sc2 = new Socket("localhost", 5555);
				ObjectOutputStream out2 = new ObjectOutputStream(sc2.getOutputStream());
				ObjectInputStream in2 = new ObjectInputStream(sc2.getInputStream())
            ){
				out2.flush();
				Peticion petEntradas =new Peticion("ENTRADAS", eventoElegido, cantidadElegida);
				System.out.println("Enviada petición: " + petEntradas.toString());
				out2.writeObject(petEntradas);//manda peticion
				out2.flush();
				Respuesta respuestaEntradas = (Respuesta) in2.readObject();//lee respuesta
				System.out.println(respuestaEntradas.toString());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
            System.out.println("No hay eventos disponibles para reservar.");
        }
		}
	}

