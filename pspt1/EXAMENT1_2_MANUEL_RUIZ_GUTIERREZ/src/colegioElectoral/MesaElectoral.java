package colegioElectoral;

import java.util.Random;

public class MesaElectoral extends Thread{
	private Random random;
	private String nombre;
	public MesaElectoral(String nm) {
		this.nombre=nm;
		this.random=new Random();
	}
	@Override
	public void run() {
		for(int i=0;i<4;i++) {
			UrnaElectronica urna=UrnaElectronica.getInstance();
			int amt=random.nextInt(50,251);
			urna.sumarVotos(amt);
			System.out.println("Se han contado "+amt+" votos en la urna "+nombre);
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
