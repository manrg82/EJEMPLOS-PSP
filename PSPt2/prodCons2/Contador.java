package prodCons2;

public class Contador {
	private int cont = 100;
	
	public Contador() {
		
	}
	
	public synchronized void incrementar() {
		if (cont < 150) {
			cont = cont + 100;
			System.out.println("Contador incrementado: "+this.cont);
		}
	}
	
	public synchronized void decrementar() {
		if ( cont >= 50) {
			cont = cont - 50;
			System.out.println("Contador decrementado: "+this.cont);
		}
	}
}
