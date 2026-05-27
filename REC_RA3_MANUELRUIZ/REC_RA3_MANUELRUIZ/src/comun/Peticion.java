package comun;

import java.io.Serializable;

public class Peticion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4282541776271262834L;
	private String tipo;
	private int cantidad;
	private String comida;
	public Peticion(String t) {
		this.tipo=t;
	}
	public Peticion(String t,int cant, String com) {
		this.tipo=t;
		this.cantidad=cant;
		this.comida=com;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipoNombres() {
		this.tipo = "NOMBRES_PLATOS_DISPONIBLES";
	}
	public void setTipoUnidades() {
		this.tipo = "UNIDADES_PLATO";
	}
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getComida() {
		return comida;
	}
	public void setComida(String comida) {
		this.comida = comida;
	}
	
}
