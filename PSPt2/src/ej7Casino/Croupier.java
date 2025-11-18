package ej7Casino;

class Croupier extends Thread {
	private static final int NUMERO_MAX = 10;
	private Banca banca;
	
	public Croupier(Banca banca) {
		this.banca = banca;
	}
	
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(3000); // El croupier espera 3000ms
            
	            int numeroGanador = (int)(Math.random() * NUMERO_MAX); // Número aleatorio entre 0 y 10
	            System.out.println("----");
	            System.out.println("--El croupier ha sacado el número: " + numeroGanador);            
	            
	            banca.indicarNumeroGanador(numeroGanador);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
    
    
}