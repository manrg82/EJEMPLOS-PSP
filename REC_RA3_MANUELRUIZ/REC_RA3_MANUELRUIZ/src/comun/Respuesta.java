package comun;

import java.io.Serializable;
import java.util.ArrayList;

public class Respuesta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String plato;
	private int cantidad;
	private String tipo;
	private boolean exito;
	private ArrayList<String> listaPlatos;
	private String error;
	public Respuesta(String tipo,int cant, boolean ex, ArrayList<String> lista, String plat) {
		this.tipo=tipo;
		if(tipo=="NOMBRES_PLATOS_DISPONIBLES") {
			this.exito=ex;
			if(ex) {
				this.listaPlatos=lista;
			}else {
				this.error="No hay ningun plato disponible";
			}
		}else {
			this.exito=ex;
			if(ex) {
				this.plato=plat;
				this.cantidad=cant;
			}
			this.error="No hay platos de "+ plat +" suficientes";
		}
	}
	public boolean getExito() {
		return this.exito;
	}
	public String getPlato() {
		return plato;
	}
	public void setPlato(String plato) {
		this.plato = plato;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isExito() {
		return exito;
	}
	public void setExito(boolean exito) {
		this.exito = exito;
	}
	public ArrayList<String> getListaPlatos() {
		return listaPlatos;
	}
	public void setListaPlatos(ArrayList<String> listaPlatos) {
		this.listaPlatos = listaPlatos;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
