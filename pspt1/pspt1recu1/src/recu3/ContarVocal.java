package recu3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ContarVocal {

	public static void main(String[] args) {
		if(args.length!=2) {
			throw new IllegalArgumentException("se necesitas 2 param");
		}
		String nombreFichero =args[0];
		char charBuscar = args[1].toLowerCase().charAt(0); 
        int contador = 0;
		File archivo=new File(nombreFichero);
		try(BufferedReader br=new BufferedReader(new FileReader(archivo))){
			int carac;
			while((carac=br.read())!=-1) {
				char c=(char)carac;
				//comprobar si es igual al char que se busca
				if(Character.toLowerCase(c)==charBuscar) {
					contador++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(contador);//mostrar nº instancias del char buscado
		
	}

}
