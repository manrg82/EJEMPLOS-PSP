package centro;


public class AlmacenStock {
	private static AlmacenStock instance=null;
	private int stock=5000;
	private AlmacenStock() {
		
	}
	private synchronized static void createInstance() {
		if (null == instance) {
			instance = new AlmacenStock();
		}
	}
	
	public static AlmacenStock getInstance() {
		if(null == instance) {
			createInstance();
		}
		return instance;
	}
	public synchronized void retirarProductor(int amt) {
		if(amt<stock) {
			stock-=amt;
		}
		else {
			System.out.println("NO HAY PRODUCTOS SUFICIENTES EN ESTE ALMACEN");
			stock=0;
		}
	}
	public int getStock() {
		return stock;
	}
}
