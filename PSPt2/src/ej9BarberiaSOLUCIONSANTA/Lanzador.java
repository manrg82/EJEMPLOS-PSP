public class Lanzador {

	private static final int NUM_BARBEROS = 5;
	private static final int NUM_CLIENTES = 50;
	
	public static void main(String[] args) throws InterruptedException {
		
		GestorSillas gestorSillas = new GestorSillas();
		
		//Crear barberos
		Barbero[] barberos= new Barbero[NUM_BARBEROS];
		for (int i = 0; i < NUM_BARBEROS; i++) {
			barberos[i] = new Barbero(gestorSillas, (i+1));
			barberos[i].start();
		} 

		//Crear clientes
		Cliente[] clientes= new Cliente[NUM_CLIENTES];
		for (int i = 0; i < NUM_CLIENTES; i++) {
			clientes[i] = new Cliente(gestorSillas, i+1);
			clientes[i].start();
			//esperar para crear cliente
			Thread.currentThread().sleep(10);
		}

		//Esperar que se vayan todos los clientes
		for (int i = 0; i < NUM_CLIENTES; i++) {
			clientes[i].join();
		}
		System.out.println("Se han ido todos los clientes.");
				
		/* La jornada ha terminado, "cerramos" los barberos */
		for (int i = 0; i < NUM_BARBEROS; i++) {
			barberos[i].interrupt();
		}
		Thread.currentThread().sleep(2000);
		System.out.println("Barberia cerrada.");
	} 
} 