package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import comun.Peticion;
import comun.Respuesta;

public class HiloReserva implements Runnable{
	
	private Socket sc;
	
	public HiloReserva(Socket s) {
		this.sc=s;
	}
	
	@Override
	public void run() {
		try(
			ObjectOutputStream out =new ObjectOutputStream(sc.getOutputStream());//flujos/canales de lectura
			ObjectInputStream in =new ObjectInputStream(sc.getInputStream());
		){
			Peticion pet=(Peticion)in.readObject();
			Respuesta res=new Respuesta();
			if (pet.getTipo().equals("EVENTOS")) {
                // El hilo pide la lista a través del método seguro del servidor
                List<String> disponibles = ServidorReservas.obtenerEventosDisponibles();
                res.setEventos(disponibles);
            }else if(pet.getTipo().equals("ENTRADAS")) {
            	int result=ServidorReservas.procesarReserva(pet.getEvento(), pet.getNumEntradas());
            	if(result==-1) {//exito
            		res.setConfirmacion(pet.getEvento(), pet.getNumEntradas());
            	}else {//fallo
            		String msg = "Error: No hay suficientes entradas (Quedan " + res + ")";
                    res.setError(msg);
                    System.out.println("ATENCIÓN: " + msg + " para el evento " + pet.getEvento());
            	}
            }
			out.writeObject(res);//escribe la respuesta al canal de lectura
            out.flush();//limpia el canal
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try { 
				sc.close(); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			}
		}
		
	}
	
}
