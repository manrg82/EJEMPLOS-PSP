package centro;

import java.util.Random;

public class MozoAlmacen extends Thread{
	private String nombre;
	private Random random;
	public MozoAlmacen(String nm) {
		this.nombre=nm;
		this.random=new Random();
	}
	@Override
	public void run() {
		AlmacenStock almacen=AlmacenStock.getInstance();
		for(int i=0;i<5;i++){
			int nmRand=random.nextInt(10,101);
			almacen.retirarProductor(nmRand);
			System.out.println("Hola soy "+nombre+" y he intentado retirar "+nmRand+" productos");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
