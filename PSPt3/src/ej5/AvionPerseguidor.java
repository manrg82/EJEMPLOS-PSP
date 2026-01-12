package ej5;

import java.util.Observer;
import java.util.Observable;

public class AvionPerseguidor implements Observer {
    private int altura;
    private int velocidad;
    private String direccion;

    public AvionPerseguidor(int altura, int velocidad, String direccion) {
        this.altura = altura;
        this.velocidad = velocidad;
        this.direccion = direccion;
    }

    @Override
    public void update(Observable o, Object arg) {
        AvionNormal perseguido = (AvionNormal) o;
        this.altura = perseguido.getAltura();
        this.velocidad = perseguido.getVelocidad();
        this.direccion = perseguido.getDireccion();
        System.out.println("Avión perseguidor cambia a: Altura=" + altura + "m, Velocidad=" + velocidad + "km, Dirección=" + direccion);
    }

    public void mostrarEstadoFinal() {
        System.out.println("Datos finales perseguidor: " + altura + "m, " + velocidad + "km, " + direccion);
    }
}