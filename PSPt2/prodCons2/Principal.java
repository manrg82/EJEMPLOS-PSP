package prodCons2;

public class Principal {

	public static void main(String[] args) {
		
		Contador contador = new Contador();
		
		Productor productor = new Productor(contador);
		productor.start();
		
		Consumidor consumidor = new Consumidor(contador);
		consumidor.start();

	}

}
