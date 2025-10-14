package cuatro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LanzarProcesos {

	public static void lanzarProcesos() {
		
		String[] ficheros = {
	            "informatica.txt",
	            "comercio.txt",
	            "administracion.txt",
	            "eso.txt",
	            "bachillerato.txt"
	        };
		
		
		ProcessBuilder pb;
		List <Process> procesos = new ArrayList();
		

		
		for(String fichero : ficheros) {
			try {
				
				String classPath = ".;./bin";
				
				pb = new ProcessBuilder("java", "-cp", classPath, "com.psp.cuatro.ProcesarFichero",  "filesejer4/"+fichero);
				
				pb.redirectError(new File("filesejer4/"+fichero+".err"));
				
				// Process p = pb.start();
				
				procesos.add(pb.start());
				
			}catch(IOException ioe){
				ioe.printStackTrace();
			}finally {
				
			}
		}
		
		
		for (Process p : procesos) {
			
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
		
		
		 int total = 0;
		 
		 for(String fichero : ficheros) {
			 File resultado = new File("filesejer4/"+fichero+".res");

			 
			 if(resultado.exists()) {
				 try(BufferedReader br = new BufferedReader(new FileReader(resultado))){
					 
					 String linea;
					 
					 while((linea = br.readLine()) != null) {
						 total += Integer.parseInt(linea);
					 }
					 
				 } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 
			 
			 
			 
			 
		 }
		 
		 try (BufferedWriter bw = new BufferedWriter(new FileWriter("filesejer4/resultado_final.txt"))) {
             bw.write("Suma total: " + total);
             
         } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	
	
	
	public static void main (String[]args) {
		LanzarProcesos.lanzarProcesos();
	}
	
}
