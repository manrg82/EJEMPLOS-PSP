package com.psp.cuatro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProcesarFichero {
	
	public void leerFichero (String nombreArchivo) {
		
		int contador = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
			
		    String linea;
		    
		    

		    while ((linea = br.readLine()) != null) {
		    	
		        int numero = Integer.parseInt(linea.trim());
		        contador += numero;
		        
		    }
		    
		    

		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo + ".res"))) {
		        bw.write(String.valueOf(contador));
		    }
		    
		    
		    
		    

		} catch (IOException ioEx) {
		        ioEx.printStackTrace();
		    }
		}
		
	
	
	
	public static void main (String []args) throws Exception {
		
		ProcesarFichero pf = new ProcesarFichero();
		
		if(args.length != 1) {
			throw new Exception ("Error en los argumentos");
		}
		
		
		pf.leerFichero(args[0]);
		
	}
	
}
