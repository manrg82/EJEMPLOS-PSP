
public class CrearHilos extends Thread {
	public CrearHilos(String nom) {
		this.setName(nom);
	}
	
	@Override
	public void run() {
		
		
		
		//Acciones que lleva a cabo el hilo
		System.out.println("Hola soy "+Thread.currentThread().getName()
				+"\n Prioridad: "+Thread.currentThread().getPriority()
				+"\n ID: "+Thread.currentThread().getId()
				+"\n Hilos activos: "+Thread.currentThread().activeCount());
				
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
