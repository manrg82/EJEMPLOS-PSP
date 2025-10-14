package cinco;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class LanzadorProcesos {

	public void lanzarProcesos() {
		
		Scanner sc = new Scanner(System.in);
        System.out.print("Introduce la palabra a buscar: ");
        String palabra = sc.nextLine().trim();
        sc.close();

		String[] ficheros = { "about_BeforeEach_AfterEach.help.txt", "about_Mocking.help.txt", "about_Pester.help.txt",
				"about_should.help.txt", "about_TestDrive.help.txt", "default.help.txt", "gmreadme.txt",
				"ThirdPartyNotices.txt" };

		ProcessBuilder pb;
		List<Process> procesos = new ArrayList();

		for (String fichero : ficheros) {
			try {

				String classPath = ".;./bin";

				pb = new ProcessBuilder("java", "-cp", classPath, "com.psp.cinco.BuscarPalabra",
						"filesejer5/" + fichero, palabra);

				pb.redirectError(new File("filesejer5/" + fichero + ".err"));
				pb.redirectOutput(new File("filesejer5/" + fichero + ".res"));

				Process p = pb.start();

				procesos.add(p);

			} catch (IOException ioe) {
				ioe.printStackTrace();

			}
		}

		for (Process p : procesos) {

			try {
				p.waitFor();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {

		LanzadorProcesos lp = new LanzadorProcesos();

		lp.lanzarProcesos();
	}
}
