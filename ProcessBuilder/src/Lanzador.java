import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Creador de procesos que ejecuta la clase java com.psp.Multiplicador
 * 
 * @author Santa Arévalo Arévalo
 *
 */
public class Lanzador {

	public void lanzarSumador(int n1, int n2, String fNombreSalida) throws IOException, InterruptedException{
		String clase = "com.psp.Sumador";
		ProcessBuilder pb;
        Process process = null;
        int exitValue;
        try {
        	   String classPath = ".;./bin";
			
        	   pb = new ProcessBuilder("java", "-cp", classPath, clase, String.valueOf(n1),           
                                String.valueOf(n2));
   			   //
               pb.redirectError(new File("files" + File.separator + "error_"+System.currentTimeMillis()+".log"));
               //Escribir datos generados por sumador en un fichero
               pb.redirectOutput(new File("files" + File.separator + fNombreSalida));
               
               // Cambia el directorio de trabajo, al directorio donde se encuentran los .class
               //pb.directory(new File("bin"));
               
               process = pb.start();
               
               //el proceso padre, espera a que el proceso hijo termine
               //devuelve el valor de salida, del proceso hijo (de process)
               exitValue = process.waitFor();
               System.out.println("Exit Value: "+exitValue);
               
                
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                throw e;
        } 
        
        //Si todo fue ok leer datos de salida del proceso
        //O se redireciona la salida o se lee aquí
//        if (exitValue == 0) {        
//        	// Mostramos caracter a caracter la salida generada por Sumador
//     		try (InputStream is = process.getInputStream()) {
//     			int c;
//     			while ((c = is.read()) != -1) {
//     				System.out.print((char) c);
//     			}
//     		} catch (IOException e) {
//     			e.printStackTrace();
//     			throw e;
//     		}
//        }
	}


	public void lanzarSumador2(int n1, int n2) throws IOException{
		String clase = "com.psp.Sumador";
		ProcessBuilder pb;
        Process process = null;
        try {
               pb = new ProcessBuilder("java", clase, String.valueOf(n1),           
                                String.valueOf(n2));
   			   //
               pb.redirectError(new File("files" + File.separator + "error.log"));
               // Cambia el directorio de trabajo, al directorio donde se encuentran los .class
               pb.directory(new File("bin"));
               pb.redirectOutput(new File("files" + File.separator + "salida.log"));
               
               process = pb.start();
                
        } catch (IOException e) {
                e.printStackTrace();
                throw e;
        }
	}

	/**
	 * Se ejecutan dos procesos, que realizan la multiplicación de dos números y
	 * devuelven el resultado cada uno por salida estándar
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		Lanzador l = new Lanzador();
		l.lanzarSumador(23, 7, "salida.log");
	}
}