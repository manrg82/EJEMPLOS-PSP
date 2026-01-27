package servidor;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventario {
	private static Inventario instance;
	private ArrayList<String> listaEventos;
	public ArrayList<String> getListaEventos() {
		return listaEventos;
	}
	public HashMap<String, Integer> getInvEntradas() {
		return invEntradas;
	}
	public HashMap<String,Integer> invEntradas;
	public static Inventario getInstance() {
		if(instance==null) {
			return createInstance();
		}else {
			return instance;
		}
	}
	private static Inventario createInstance() {
		if(instance==null) {
			return new Inventario();
		}else {
			return instance;
		}
		
	}
	private Inventario() {
		listaEventos=new ArrayList<String>();
		invEntradas=new HashMap<>();
		listaEventos.add("Concierto");
		listaEventos.add("Teatro");
		listaEventos.add("Cine");
		listaEventos.add("Deportes");
		invEntradas.put(listaEventos.get(0), 15);
		invEntradas.put(listaEventos.get(1), 25);
		invEntradas.put(listaEventos.get(2), 20);
		invEntradas.put(listaEventos.get(3), 40);
		
	}

}
