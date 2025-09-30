import java.io.IOException;
import java.io.InputStream;

public class Ejemplo2 {

	public static void main(String[] args) throws Exception {

		ProcessBuilder pb;
		InputStream is = null;
		try {
			// Ejecutamos el proceso dir
			pb = new ProcessBuilder("CMD", "/C", "DIR");
			Process p = pb.start();

			// Mostramos caracter a caracter la salida generada por DIR
			is = p.getInputStream();
			int c;
			while( (c = is.read()) != -1) {
				System.out.print((char) c);
			}
			
			//recoger la salida de System.exit();
			int exitVal = p.waitFor();
			System.out.println("");
			System.out.println("Valor de salida: "+ exitVal);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != is) {
				is.close();
			}
		}
	}

}
