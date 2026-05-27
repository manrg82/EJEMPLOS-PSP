package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import comun.Peticion;
import comun.Respuesta;

public class ClienteComida {

	public static void main(String[] args) {
		try (
			Socket soc=new Socket("localhost",5555);
			ObjectOutputStream out=new ObjectOutputStream(soc.getOutputStream());
			ObjectInputStream inp=new ObjectInputStream(soc.getInputStream());
		){
			ArrayList<String>listaPlatosDisp=null;
			out.writeObject(new Peticion("NOMBRES_PLATOS_DISPONIBLES",0,""));
			Respuesta resp=(Respuesta) inp.readObject();
			if(resp.getExito()) {
				listaPlatosDisp=resp.getListaPlatos();
				System.out.println("LISTA PLATOS RECIBIDOS: ");
				for(int i=0;i<listaPlatosDisp.size();i++) {
					System.out.println(listaPlatosDisp.get(i));
				}
			}else {
				System.out.println(resp.getError());
			}
			Random rand=new Random();
			out.writeObject(new Peticion("UNIDADES_PLATO",rand.nextInt(10)+1,listaPlatosDisp.get(rand.nextInt(4)+1)));
			resp=(Respuesta) inp.readObject();
			if(resp.getExito()) {
				System.out.println("Plato Recibido= "+ resp.getPlato() +", numUnidades= "+resp.getCantidad());
			}else {
				System.out.println(resp.getError());
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
