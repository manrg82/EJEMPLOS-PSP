package pspt1recu1;

import java.io.File;
import java.io.IOException;

public class LanzadorMultiplicador {
	public static void main(String[] args) {
	   LanzadorMultiplicador lanzador=new LanzadorMultiplicador();
	   try {
		lanzador.lanzarMultiplicador(5, 8, "5x8.txt");
		lanzador.lanzarMultiplicador(12,10, "12x10.txt");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	public void lanzarMultiplicador(int n1,int n2,String ficheroSalida) throws IOException {
		File directorio = new File("files");
        if (!directorio.exists()) {
            directorio.mkdir(); 
        }
        ProcessBuilder pb = new ProcessBuilder(
                "java", 
                "-cp", "bin", 
                "pspt1recu1.Multiplicador", 
                String.valueOf(n1),          
                String.valueOf(n2)         
        );
        String nombreFicheroError = System.currentTimeMillis() + "error.log";
        pb.redirectOutput(new File(directorio,ficheroSalida));
        pb.redirectError(new File(directorio, "error.log"));
        //lanzar procesos
        try {
        	Process p=pb.start();
        	int codSalida=p.waitFor();
        	if(codSalida!=0) {
        		System.out.println("error");
        	}else {
        		System.out.println("no error :)");
        	}
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
	
}
