package ej4;

public class Maquina {
    private static Maquina instancia;
    private int stock;

    private Maquina() {
        this.stock = 100;
    }

    public static synchronized Maquina getInstancia() {
        if (instancia == null) {
            instancia = createInstancia();
        }
        return instancia;
    }
    private static synchronized Maquina createInstancia() {
    	if (instancia == null) {
            return new Maquina();
        }
		return instancia;
    }

    public synchronized int adquirirRefrescos(int cantidad) {
        if (stock == 0) {
            return 0;
        } else if (stock >= cantidad) {
            stock -= cantidad;
            System.out.println("Quedan "+stock+" refrescos");
            return cantidad;
        } else {
            System.out.println("No hay suficientes Refrescos");
            int restantes = stock;
            stock = 0;
            return restantes;
        }
    }

    public synchronized int getStock() {
        return stock;
    }
}