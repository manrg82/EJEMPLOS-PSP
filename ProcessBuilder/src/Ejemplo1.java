
public class Ejemplo1 {
    public void ejecutar(String ruta){ 
            ProcessBuilder pb;
            try {
                    pb = new ProcessBuilder(ruta);
                    pb.start();
            } catch (Exception e) {
                    e.printStackTrace();
            }
    } 
    
 	public static void main(String[] args) {
            //String ruta= "C:\\Program Files\\Notepad++\\notepad++.exe";
 		String ruta = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
            Ejemplo1 lp=new Ejemplo1();
            lp.ejecutar(ruta);
            System.out.println("Finalizado");
    }
}

