package sumaConObjetos;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

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
			CalculoRequest request = new CalculoRequest(15, 84, EnumOperacion.SUMA);
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