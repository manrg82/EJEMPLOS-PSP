package com.psp.cinco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BuscarPalabra {

	public void buscarPalabra(String nombreArchivo, String palabra) {
		
		
		try(BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))){
			
			String linea;
			
			int contador = 0;
			while((linea = br.readLine()) != null) {
				
				linea.toLowerCase();
				palabra.toLowerCase();
				
				if(linea.contains(palabra)) {
					contador++;
				}
				
			}
			
			System.out.println("En el fichero "+nombreArchivo+" se ha encontrado la palabra "+palabra+" "+contador+" veces.");
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void main (String[]args) {
		
		BuscarPalabra bp = new BuscarPalabra();
		
		bp.buscarPalabra(args[0], args[1]);
		
	}
}
