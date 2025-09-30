
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lanzar proceso indicado por parámetro
 */
public class RunPBWithArguments {

	public static void main(String[] args) {

	    if (args.length <= 0) {
	      System.out.println("Debe indicarse comando a ejecutar.");
	      System.exit(1);
	    }

	    
	    InputStreamReader isr = null;
	    BufferedReader br = null;
	    
	    try 
	      {
	    	ProcessBuilder pb = new ProcessBuilder(args);   
	    	Process p=pb.start();   //Iniciamos el proceso
	             
	             isr  =new InputStreamReader (p.getInputStream());    //Cogemos la salida y la leemos
	             br = new BufferedReader(isr) ;
	             String linea;
	             
	             while (  (linea=br.readLine())!=null)
	             {
	               System.out.println(linea);   //Mostramos las lineas de la salida
	             }
	 
	    } catch (IOException e) 
	      {
	      System.err.println("Error durante ejecución del proceso");
	      System.err.println("Información detallada");
	      System.err.println("---------------------");
	      e.printStackTrace();
	      System.err.println("---------------------");
	      System.exit(2);
	     } 
	    finally {
	    	if (br != null) {
	    		try {
					br.close();
				} catch (IOException e) {
				}
	    		
	    	}
	    	
	    	if (isr != null) {
	    		try {
					isr.close();
				} catch (IOException e) {
				}
	    		
	    	}
	    }
	}

}
