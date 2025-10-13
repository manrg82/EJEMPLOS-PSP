package com.psp.seis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LanzadorProcesos {

	
	 public static void lanzarProcesos() {
		 
		 int [] numeros = {20, 25, 30, 2, 7, 13};
		 
		 List<Process> procesos = new ArrayList<>();

	     String clase = "com.psp.seis.CalcularFactorial";
	     String classPath = ".;./bin";
	     
	     
	     try {
	            
	            for (int n : numeros) {
	                ProcessBuilder pb = new ProcessBuilder("java", "-cp", classPath, clase, String.valueOf(n));
	                Process p = pb.start();
	                
	                procesos.add(p);
	            }
	            
	            
	            
	            
	            for (int i = 0; i < procesos.size(); i++) {
	                Process p = procesos.get(i);
	                int exitValue = p.waitFor();
	                
	                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	                
	                
	                String linea;
	                while ((linea = br.readLine()) != null) {
	                    System.out.println(linea);
	                }
	                
	                
	                
	                
	                
	                // Mostrar errores
	                BufferedReader brErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	                
	                while ((linea = brErr.readLine()) != null) {
	                    System.out.println("Error: " + linea);
	                }
	                
	                if (exitValue != 0) {
	                    System.out.println("Proceso " + (i + 1) + " terminó con error.");
	                }
	            }
	            
	            
	                
	     } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }	
	   }
	 
	 
	 public static void main(String[]args) {
		 
		 lanzarProcesos();
	 }
}
