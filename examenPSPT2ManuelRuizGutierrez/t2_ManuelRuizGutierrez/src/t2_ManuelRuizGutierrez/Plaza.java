package t2_ManuelRuizGutierrez;

public class Plaza {
	private boolean isOcupada;
	public Plaza() {
		this.isOcupada=false;
	}
	public void setOcupada() {
		if(!isOcupada) {
			this.isOcupada=true;
		}
	}
	public void setLibre() {
		if(isOcupada) {
			this.isOcupada=false;
		}
	}
	public boolean isOcupada() {
		return isOcupada;
	}
}
