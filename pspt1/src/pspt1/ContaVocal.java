package pspt1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ContarVocal {

    public static void contarVocales(String fichero, String vocal) {

        try (BufferedReader br = new BufferedReader(new FileReader (fichero))){

            int c;
            int contador = 0;

            char caracter;

            while((c = br.read()) != -1) {

                caracter = Character.toLowerCase((char) c);


                if(caracter == vocal.charAt(0)) {
                    contador ++;
                }
            }


            try(BufferedWriter bw = new BufferedWriter(new FileWriter("files/resultado_"+vocal+".txt"))){
                bw.write(String.valueOf(contador));
            }


        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        contarVocales(args[0], args[1]);

    }
}