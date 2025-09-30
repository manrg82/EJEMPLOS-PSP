import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Ejemplo4 {

	public static void main(String[] args) throws Exception {

		Process p;
		InputStream in = null;
		BufferedReader bReader=null;
		try {
			// Ejecutamos el proceso ping 
			p = new ProcessBuilder("ping", "www.google.es").start();

			// Mostramos linea a linea la salida generada por ping
			in = p.getInputStream();
			bReader = new BufferedReader(new InputStreamReader(in));
			String linea=null;
			while( (linea = bReader.readLine()) != null) {
				System.out.println(" >"+linea);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != bReader) {
				bReader.close();
			}
			if (null != in) {
				in.close();
			}
		}
	}

}
