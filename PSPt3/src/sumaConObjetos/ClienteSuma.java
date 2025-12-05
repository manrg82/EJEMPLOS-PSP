package sumaConObjetos;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSuma {

	public static void main(String[] args) throws Exception {
		Socket socket = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		try {
			InetSocketAddress direccion = new InetSocketAddress("localhost", 9876);
			socket = new Socket();
			socket.connect(direccion);

			oos = new ObjectOutputStream(socket.getOutputStream());
			Scanner sc=new Scanner(System.in);	
			int nm1=sc.nextInt();
			int nm2=sc.nextInt();
			char opt=(char)sc.nextByte();
			CalculoRequest request=null;
			switch(opt) {

			case '+':
				request = new CalculoRequest(nm1,nm2, EnumOperacion.SUMA);
				break;
			case '-':
				request = new CalculoRequest(nm1,nm2, EnumOperacion.RESTA);
				break;
			case '*':
				request = new CalculoRequest(nm1,nm2, EnumOperacion.MULTIPLICACION);
				break;
			case '/':
				request = new CalculoRequest(nm1,nm2, EnumOperacion.DIVISION);
				break;
			default:
					
				break;
			}
			oos.writeObject(request);
			oos.flush();

			ois = new ObjectInputStream(socket.getInputStream());
			CalculoResponse response = (CalculoResponse) ois.readObject();

			System.out.println("El resultado fue: " + response.getResultado());

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(ois);
			close(oos);
			close(socket);
		}
	}

	private static void close(Closeable socket) {
		try {
			if (null != socket) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}