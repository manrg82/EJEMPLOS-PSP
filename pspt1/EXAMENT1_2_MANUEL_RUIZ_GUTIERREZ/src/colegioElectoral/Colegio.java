package colegioElectoral;

import java.util.ArrayList;
import java.util.Random;

public class Colegio {
	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("faltan args");
			return;
		}
		
		String ciudad = args[0];
		System.out.println("--- Iniciando recuento en Colegio Distrito: " + ciudad + " ---");
		
		Random rand = new Random();
		int numeroDeMesas = rand.nextInt(3, 6);
		ArrayList<MesaElectoral> listaMesas = new ArrayList<>();
		for(int i = 0; i < numeroDeMesas; i++) {
			MesaElectoral mesa = new MesaElectoral("ur" + i);
			mesa.start(); 
			listaMesas.add(mesa); 
		}
		for(MesaElectoral mesa : listaMesas) {
			try {
				mesa.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("--- Fin de recuento local en"+ciudad+" ---");
		int totVotos = UrnaElectronica.getInstance().getVotos();
		System.out.println("Total de votos contabilizados en Colegio " + ciudad + ": " + totVotos + " votos.");
	}
	
}
