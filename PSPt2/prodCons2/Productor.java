package prodCons2;

public class Productor extends Thread {
	
	private static final long MAX_TIME = 1 * 60 * 1000;
	
	private long initTime;
	
	private Contador contador=null;
	public Productor(Contador contador) {
		this.contador = contador;
	}

	@Override
	public void run() {
		//se tiene que ejecutar durante 1 minuto
		initTime = System.currentTimeMillis();
		
		while ((System.currentTimeMillis() - initTime) <= MAX_TIME) {
			
			try {
				contador.incrementar();
				this.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	
}
