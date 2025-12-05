package sumaConObjetos;

import java.io.Serializable;

public class CalculoResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private int resultado;
//falta una variable calculorequest .0
	public CalculoResponse(int resultado) {
		this.resultado = resultado;
	}

	public int getResultado() {
		return resultado;
	}
}