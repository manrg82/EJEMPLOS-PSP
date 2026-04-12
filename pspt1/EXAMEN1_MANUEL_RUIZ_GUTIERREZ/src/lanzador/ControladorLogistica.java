package lanzador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControladorLogistica {
	public static void main(String[] args) {
		ArrayList<Process> listaProcesos=new ArrayList<>();
		String[] ciudades= {"Madrid","Barcelona","Sevilla","Valencia"};
		File carpeta = new File("files");
		if (!carpeta.exists()) {
		    carpeta.mkdir(); 
		}
		for(String ciudad:ciudades) {
			ProcessBuilder pb=new ProcessBuilder(
					"java",
					"-cp",
					"bin",
					"centro.CentroDistribucion", 
				    ciudad
			);
			pb.redirectOutput(new File("files" + File.separator + "registro_" + ciudad + ".txt"));
            pb.redirectError(new File("files" + File.separator + "errores_logistica.log"));
			try {
				Process proc=pb.start();
				listaProcesos.add(proc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Process p:listaProcesos) {
			try {
				p.waitFor();
			}catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
		System.out.println("FIN");
	}
}
