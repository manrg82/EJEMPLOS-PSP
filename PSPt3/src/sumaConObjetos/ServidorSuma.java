package sumaConObjetos;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSuma {

	private int validarNumero(int numero) {
		if (numero >= 100000000) {
			return 0;
		}
		return numero;
	}

	private int calcular(CalculoRequest request) {
		int num1 = validarNumero(request.getNum1());
		int num2 = validarNumero(request.getNum2());
		int resultado = 0;

		if (request.getOperacion() == EnumOperacion.SUMA) {
			resultado = num1 + num2;
		} else if (request.getOperacion() == EnumOperacion.RESTA) {
			resultado = num1 - num2;
		} else if (request.getOperacion() == EnumOperacion.MULTIPLICACION) {
			resultado = num1 * num2;
		} else if (request.getOperacion() == EnumOperacion.DIVISION) {
			if (num2 != 0) {
				resultado = num1 / num2;
			}
		}
		System.out.println("Operacion recibida: " + num1 + " " + request.getOperacion() + " " + num2 + " = " + resultado);
		return resultado;
	}

	public void escuchar() throws IOException {
		System.out.println("Arrancado el servidor de objetos");
		ServerSocket socketEscucha = null;
		Socket conexion = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		try {
			socketEscucha = new ServerSocket(9876);
			while (true) {
				try {
					conexion = socketEscucha.accept();
					System.out.println("Conexion recibida!");
					
					ois = new ObjectInputStream(conexion.getInputStream());
					CalculoRequest request = (CalculoRequest) ois.readObject();
					
					int resultadoNumerico = this.calcular(request);
					CalculoResponse response = new CalculoResponse(resultadoNumerico);
					
					oos = new ObjectOutputStream(conexion.getOutputStream());
					oos.writeObject(response);
					oos.flush();
					
				} catch (IOException | ClassNotFoundException e) {
					System.out.println("Error procesando peticion: " + e.getMessage());
					e.printStackTrace();
				} finally {
					close(oos);
					close(ois);
					close(conexion);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(socketEscucha);
		}
	}

	private void close(Closeable socket) {
		try {
			if (null != socket) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		ServidorSuma servidor = new ServidorSuma();
		servidor.escuchar();
	}
}