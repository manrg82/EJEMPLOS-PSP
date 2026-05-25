package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ServidorComponentes {
	public static ConcurrentHashMap<String,Integer> inventario = new ConcurrentHashMap<>();
	public static void main(String[] Args) {
		inventario.put("AMD Ryzen 7 5700X",15);
		inventario.put("Radeon RX 9070 XT",5);
		inventario.put("WD Black SN850X 1TB",20);
		inventario.put("Thermalright HR10 2280 PRO",30);
		try(ServerSocket sc=new ServerSocket(8888)){
			System.out.println("Inicia Sv");
			while (true){//bucle de conexion
				Socket conexion = sc.accept();
				HandlerCliente h = new HandlerCliente(conexion);//delega el trabajo al hilo del handler
                Thread hilo = new Thread(h);
                hilo.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
