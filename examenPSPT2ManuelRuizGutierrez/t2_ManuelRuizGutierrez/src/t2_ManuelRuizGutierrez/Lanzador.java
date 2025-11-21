package t2_ManuelRuizGutierrez;

import java.util.ArrayList;

public class Lanzador {
	public static void main(String[] Args) {
		Parking pk=Parking.getInstance();
		ArrayList<Coche> coches=new ArrayList<Coche>();
		for(int i=0;i<10;i++) {
			coches.add(new Coche("Coche "+i,pk));
			coches.get(i).start();
		}
	}
}
