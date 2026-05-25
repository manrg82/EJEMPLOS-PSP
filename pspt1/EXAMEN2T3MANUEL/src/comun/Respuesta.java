package comun;

import java.io.Serializable;
import java.util.List;

public class Respuesta implements Serializable{
	private static final long serialVersionUID = 2L;
	private String tipo;
	private List<String> eventosDisponibles;
	private String eventoConfirmado;
	private int entradasConfirmadas;
	private String mensajeError;
	//respuesta para eventos
	public void setEventos(List<String> eventosDisponibles) {
		this.tipo="EVENTOS";
		this.eventosDisponibles=eventosDisponibles;
	}
	//respuesta para entradas
	public void setConfirmacion(String evento, int entradas) {
		this.tipo="ENTRADAS";
		this.eventoConfirmado=evento;
		this.entradasConfirmadas=entradas;
	}
	
	public void setError(String msg) {
		this.mensajeError=msg;
	}
	public List<String> getEventosDisponibles(){
		return eventosDisponibles;
	}
	@Override
	public String toString() {
        if (mensajeError != null) return mensajeError;
        if (tipo.equals("EVENTOS")) return "Eventos recibidos: " + eventosDisponibles;
        return "Entradas recibidas: Entrada [evento=" + eventoConfirmado + ", numEntradas=" + entradasConfirmadas + "]";
    }

}
