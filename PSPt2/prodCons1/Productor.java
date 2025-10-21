package prodCons1;

public class Productor extends Thread {
	
	private static final long MAX_TIME = 2 * 60 * 1000;
	
	private long initTime;
	
	private Contador contador=null;
	public Productor() {
		this.contador = Contador.getInstance();
	}

	@Override
	public void run() {
		//se tiene que ejecutar durante 2 minutos
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
