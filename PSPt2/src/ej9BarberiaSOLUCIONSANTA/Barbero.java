public class Barbero extends Thread {
	GestorSillas gestorSillas;

	public Barbero(GestorSillas gestorSillas, int numBarbero) {
		this.gestorSillas = gestorSillas;
		this.setName("Barbero"+numBarbero);
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Silla silla = this.gestorSillas.getSillaOcupada(this.getName());
				if (null != silla) {
					System.out.println(this.getName()+" atendiendo silla: " + silla.getNumSilla());
					//atiende
					Thread.currentThread().sleep(250);
					
					//libera la silla
					silla.setLibre();
				}
			} catch (InterruptedException e) {
				System.out.println(this.getName()+" llegó interupción: "+this.isInterrupted());
				//this.interrupt();
			}
		}
		System.out.println(this.getName()+" se va.");
	}

}