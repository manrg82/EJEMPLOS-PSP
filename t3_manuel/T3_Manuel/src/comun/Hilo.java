package comun;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import servidor.Inventario;


public class Hilo extends Thread{
	
	@Override
	public void run() {
		try {
			Socket sk=new Socket("localhost",5555);
			ObjectInputStream in =new ObjectInputStream(sk.getInputStream());
			ObjectOutputStream out=new ObjectOutputStream(sk.getOutputStream());
			Solicitud s=(Solicitud)in.readObject();
			Inventario inv=Inventario.getInstance();
			if(s.getIsLista()) {//en caso de que pida la lista de eventos con entradas !=0
				ArrayList<String>ls=new ArrayList<String>();
				if(inv.invEntradas.get("Concierto")!=0) {
					ls.add("Concierto");
				}
				if(inv.invEntradas.get("Teatro")!=0) {
					ls.add("Teatro");
				}
				if(inv.invEntradas.get("Cine")!=0) {
					ls.add("Cine");
				}
				if(inv.invEntradas.get("Deportes")!=0) {
					ls.add("Deportes");
				}
				Respuesta r=new Respuesta(true,ls);
				
				out.writeObject(r);
			}else {
				int nmEntr=0;
				if(inv.invEntradas.get(s.getNmEvento())==0) {//no hay entradas
					nmEntr=0;
				}else if(inv.invEntradas.get(s.getNmEvento())<s.getNmEntradas()) {//hay entradas pero no suficientes
					nmEntr=inv.invEntradas.get(s.getNmEvento());
					inv.invEntradas.put(s.getNmEvento(), 0);
				}else {//hay entradas de sobra
					nmEntr=s.getNmEntradas();
					inv.invEntradas.put(s.getNmEvento(),inv.invEntradas.get(s.getNmEvento())-s.getNmEntradas() );
				}
				Respuesta r=new Respuesta(false,s.getNmEvento(),nmEntr);
				out.writeObject(r);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
