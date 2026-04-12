package colegioElectoral;

public class UrnaElectronica {
	private static UrnaElectronica instance=null;
	private int votos=0;
	private UrnaElectronica() {
		
	}
	private synchronized static void createInstance() {
		if (null == instance) {
			instance = new UrnaElectronica();
		}
	}
	
	public static UrnaElectronica getInstance() {
		if(null == instance) {
			createInstance();
		}
		return instance;
	}
	public synchronized void sumarVotos(int amt) {
			votos+=amt;
	}
	public int getVotos() {
		return votos;
	}
}
