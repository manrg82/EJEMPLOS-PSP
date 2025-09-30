package pspt1;

import java.io.File;

public class LanzarProcesos {
	public void lanzarContador(String file, char voc) {
		ProcessBuilder pb;
        try {
        	String clase = "pspt1.ContaVocal";
        	String classPath = ".;./bin";
			
     	   pb = new ProcessBuilder("java", "-cp", classPath, clase,file,String.valueOf(voc));
                pb.redirectError(new File("files" + File.separator + "error_"+System.currentTimeMillis()+".log"));
                pb.redirectOutput(new File("files" + File.separator + "resultado "+voc+".txt"));
                Process pr=pb.start();
                int exitValue = pr.waitFor();
                System.out.println("Exit Value: "+exitValue);
        } catch (Exception e) {
                e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		LanzarProcesos lz=new LanzarProcesos();
		lz.lanzarContador("/files/texto.txt", 'a');
	}

}
