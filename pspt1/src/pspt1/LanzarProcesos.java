package pspt1;
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

        String fichero = "files/texto.txt";
        String [] vocales = {"a", "e", "i", "o", "u"};
        List <Process> procesos = new ArrayList();

        ProcessBuilder pb;

        for(String v : vocales) {

            try {
                String classPath = ".;./bin";

                pb = new ProcessBuilder("java", "-cp", classPath, "com.psp.tres.ContarVocal", fichero, v);

                procesos.add(pb.start());

            } catch (IOException e) {

                e.printStackTrace();
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
        for(String v : vocales) {
            File resultado = new File("files/resultado_"+v+".txt");

            try(BufferedReader br = new BufferedReader(new FileReader(resultado))){

                total += Integer.parseInt(br.readLine());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        System.out.println("Número total de vocales: " + total);




    }


    public static void main(String[] args) {
        lanzarProcesos();
    }
}
