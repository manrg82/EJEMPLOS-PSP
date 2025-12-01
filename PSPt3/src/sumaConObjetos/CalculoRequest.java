package sumaConObjetos;

import java.io.Serializable;

public class CalculoRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private int num1;
	private int num2;
	private EnumOperacion operacion;

	public CalculoRequest(int num1, int num2, EnumOperacion operacion) {
		this.num1 = num1;
		this.num2 = num2;
		this.operacion = operacion;
	}

	public int getNum1() {
		return num1;
	}

	public int getNum2() {
		return num2;
	}

	public EnumOperacion getOperacion() {
		return operacion;
	}
}