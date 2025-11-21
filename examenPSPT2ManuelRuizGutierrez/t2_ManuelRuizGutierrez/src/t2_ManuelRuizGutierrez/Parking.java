package t2_ManuelRuizGutierrez;

import java.util.Vector;

public class Parking {
	private Vector<Plaza> plazas;
	private static Parking instance;
	private static int numPlazas;
	private static int numLibres;
	private Parking(){
		this.numPlazas=5;
		this.numLibres=numPlazas;
		this.plazas=new Vector<Plaza>();
		for(int i=0;i<numPlazas;i++) {
			plazas.add(new Plaza());
		}
	}
	private static synchronized void createInstance() {
		if(instance==null) {
			instance=new Parking();
		}
	}
	public static Parking getInstance() {
		if(instance==null) {
			createInstance();
		}
		return instance;
	}
	public synchronized Object[] ocuparPlaza(String nm) {
		boolean plazaEncontrada=false;
		int i=0;
		try {
			for(i=0;!plazaEncontrada&&i<numPlazas;i++) {
				if(plazas.get(i)==null) {
					plazas.get(i).setOcupada();
					plazaEncontrada=true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		numLibres--;
		StringBuffer str=null;
		str=new StringBuffer("Parking: [");
		for(int j=1;j<numPlazas-1+1;j++) {
			if(plazas.get(j).isOcupada()) {
				str.append(j+": ocupada, ");
			}else {
				str.append(j+": libre, ");
			}
		}
		str.append("[");
		if(numLibres>numPlazas) {
			numLibres=5;
		}else if(numLibres<0) {
			numLibres=0;
		}
		System.out.println(nm+" ha entrado a la plaza numero "+i);
		System.out.println("Plazas libres: "+numLibres);
		System.out.println("Parking: "+str.toString());
		return new Object[]{plazas.get(i-1),nm,i};
	}
	public void liberarPlaza(Plaza p,String nm,int num) {
		p.setLibre();
		numLibres++;
		if(numLibres>numPlazas) {
			numLibres=5;
		}else if(numLibres<0) {
			numLibres=0;
		}
		StringBuffer str=null;
		str=new StringBuffer("Parking: [");
		for(int j=1;j<numPlazas-1;j++) {
			if(plazas.get(j).isOcupada()) {
				str.append(j+": ocupada, ");
			}else {
				str.append(j+": libre, ");
			}
		}
		str.append("[");
		System.out.println(nm+" ha salido de la plaza numero "+num);
		System.out.println("Plazas libres: "+numLibres);
		System.out.println("Parking: "+str.toString());
	}
}
