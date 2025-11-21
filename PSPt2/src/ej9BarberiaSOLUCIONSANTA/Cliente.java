public class Cliente extends Thread {
	private GestorSillas gestorSillas;
	
	public Cliente(GestorSillas gestorSillas, int numCliente) {
		this.gestorSillas = gestorSillas;
		this.setName("Cliente"+numCliente);
	}
		

	@Override
	public void run() {
		try {
			Silla silla = this.gestorSillas.getSillaLibre(this.getName());
			if (null == silla) {
				System.out.println(this.getName()+" no habia sillas libres, me marcho");
			}
			else {
				//el cliente se sienta y espera a que el barbero le corte el pelo
				while(silla.isBooOcupada()) {
					if (silla.hayBarbero()) {
						System.out.println(this.getName()+ " está sentado en la silla: "
								+silla.getNumSilla()
								+" y le está atendiendo el barbero: "+silla.getBarbero());
					} else {
						System.out.println(this.getName()+ " está sentado en la silla: "
								+silla.getNumSilla()
								+" esperando que le atienda un barbero");
					}
					this.sleep(100);
					//System.out.println("-- "+this.getName()+ " estoy sentado en la silla:" + silla.getNumSilla());
				}
				
				System.out.println(this.getName()+" ya he sido atendido, me marcho");
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}