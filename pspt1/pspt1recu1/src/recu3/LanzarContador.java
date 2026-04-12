package recu3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LanzarContador {

	public static void main(String[] args) {
		String[] vocales = {"a", "e", "i", "o", "u"};
		String ficheroLectura = "files" + File.separator + "texto.txt";
		ArrayList<Process> listaProcesos = new ArrayList<>();
		
		System.out.println("Lanzando procesos...");
		for (String vocal : vocales) {
			ProcessBuilder pb = new ProcessBuilder(
					"java",
					"-cp", "bin",
					"recu3.ContarVocal",
					ficheroLectura,
					vocal
			);
			String nombreFicheroSalida = "files" + File.separator + "resultado_" + vocal + ".txt";
			pb.redirectOutput(new File(nombreFicheroSalida));
			pb.redirectError(new File("files" + File.separator + "error_" + vocal + ".log"));
			
			try {
				Process p = pb.start(); // lanzar procesos y añadirlos a la lista
				listaProcesos.add(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Procesos lanzados. Esperando a que terminen...");
		for (Process p : listaProcesos) {
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 

		System.out.println("Recopilando resultados...");
		int totalVocales = 0;
		for (String vocal : vocales) { 
			File resultadoHijo = new File("files" + File.separator + "resultado_" + vocal + ".txt");
			
			try (BufferedReader br = new BufferedReader(new FileReader(resultadoHijo))) {
				String linea = br.readLine();
				if (linea != null && !linea.isEmpty()) {
					int subtotal = Integer.parseInt(linea.trim());
					System.out.println("Total de " + vocal + ": " + subtotal);
					totalVocales += subtotal;
				}
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero de la vocal: " + vocal);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("TOTAL DE VOCALES: " + totalVocales);
	}
	
}
