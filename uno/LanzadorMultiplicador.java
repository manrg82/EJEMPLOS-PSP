package com.psp.uno;

import java.io.File;
import java.io.IOException;


public class LanzadorMultiplicador {
	
	public void lanzarMultiplicador(int num1, int num2, String nomFicheroSalida) throws IOException, InterruptedException {
		String clase = "com.psp.uno.Multiplicador";
		ProcessBuilder pb;
        Process process = null;
        int exitValue;
        try {
        	   String classPath = ".;./bin";
			
        	   pb = new ProcessBuilder("java", "-cp", classPath, clase, String.valueOf(num1), String.valueOf(num2));
   			   
               pb.redirectError(new File("files" + File.separator + "error_"+System.currentTimeMillis()+".log"));
               
               pb.redirectOutput(new File("files" + File.separator + nomFicheroSalida));

               process = pb.start();
               
               
               exitValue = process.waitFor();
               System.out.println("Exit Value: "+exitValue);
               
                
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                throw e;
        } 
	}
	
	
	public static void main (String [] args) throws IOException, InterruptedException {
		
		LanzadorMultiplicador l1 = new LanzadorMultiplicador();
		
		l1.lanzarMultiplicador(8, 5, "resultado.log");
	}
	
	
	
	
	
}
