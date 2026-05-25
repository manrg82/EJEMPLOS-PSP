package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServidorReservas {
	private static ConcurrentHashMap<String,Integer> inventario= new ConcurrentHashMap<>();
	public static void main(String[] args) {
		inventario.put("Concierto", 15);
		inventario.put("Teatro", 25);
		inventario.put("Cine", 20);
		inventario.put("Deportes", 40);
		
		try(ServerSocket sv=new ServerSocket(5555)){
			System.out.println("arranca sv en el puerto 5555");
			while(true) {
				Socket socketCliente=sv.accept();//se queda esperando la peticion de algun cliente
				System.out.println("cliente conectado");
				HiloReserva hilo=new HiloReserva(socketCliente);//delega la conexion con el cliente al hiloreserva
				new Thread(hilo).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static synchronized List<String> obtenerEventosDisponibles() {//devuelve la lista de forma sincronizada
        List<String> disponibles = new ArrayList<>();
        for (Map.Entry<String, Integer> entrada : inventario.entrySet()) {
            if (entrada.getValue() > 0) {
                disponibles.add(entrada.getKey());
            }
        }
        return disponibles;
    }

    public static synchronized int procesarReserva(String evento, int cantidad) {//intenta procesar una reserva de forma sincronizada
        Integer entradasActuales = inventario.get(evento);

        if (entradasActuales != null && entradasActuales >= cantidad) {
            inventario.put(evento, entradasActuales - cantidad);
            return -1; 
        }

        return (entradasActuales != null) ? entradasActuales : 0; 
    }
	
}
