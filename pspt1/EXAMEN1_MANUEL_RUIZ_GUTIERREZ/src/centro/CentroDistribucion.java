package centro;

public class CentroDistribucion {
	public static void main(String[] args) {
		if(args.length<1) {
			System.out.println("faltan args");
			return;
		}
		String ciudad=args[0];
		MozoAlmacen m1=new MozoAlmacen("m1");
		MozoAlmacen m2=new MozoAlmacen("m2");
		MozoAlmacen m3=new MozoAlmacen("m3");
		m1.start();
		m2.start();
		m3.start();
		try {
			m1.join();
			m2.join();
			m3.join();
		}catch(InterruptedException e) {
            System.err.println("El turno fue interrumpido inesperadamente.");
		}
		int stockSobrante=AlmacenStock.getInstance().getStock();
		System.out.println("Sobraron "+stockSobrante+" de stock");
	}
}
