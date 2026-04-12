package recu2;

import java.io.InputStream;

public class CmdDir {
	public static void main(String[] args) {
		ProcessBuilder pb=new ProcessBuilder("cmd","/C","dir");
		try {
			Process p=pb.start();
			p.waitFor();
			try (InputStream is = p.getInputStream()) {
                int c;
                while ((c = is.read()) != -1) {
                    System.out.print((char) c);
                }
            }
		}catch(Exception E) {
			E.printStackTrace();
		}
	}
}
