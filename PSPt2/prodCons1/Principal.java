package prodCons1;

public class Principal {

	public static void main(String[] args) {
		Productor productor = new Productor();
		productor.start();
		
		Consumidor consumidor = new Consumidor();
		consumidor.start();

	}

}
