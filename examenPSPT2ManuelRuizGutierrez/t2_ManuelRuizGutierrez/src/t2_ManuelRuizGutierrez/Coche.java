package t2_ManuelRuizGutierrez;

public class Coche extends Thread {
	private String nombre;
	private Parking pk;
	public Coche(String nm,Parking p) {
		this.nombre=nm;
		this.pk=p;
	}
	@Override
	public void run() {
		while(true) {
			Object[] param=pk.ocuparPlaza(this.nombre);
			try {
			this.sleep(3000);
			pk.liberarPlaza((Plaza)param[0],(String)param[1],(int)param[2]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
