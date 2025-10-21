package Ej1Coches;

import java.util.Random;

public class Coche {
    private String nombre;
    private int distanciaRecorrida;
    private int distanciaPorIteracion;

    public Coche(String nombre) {
        this.nombre = nombre;
        Random rand = new Random();
        this.distanciaPorIteracion = rand.nextInt(50) + 1;
        this.distanciaRecorrida = 0;
    }

    public void avanzar() {
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            System.err.println("Error al dormir el hilo: " + e.getMessage());
        }

        distanciaRecorrida += distanciaPorIteracion;
        if (distanciaRecorrida > 1000) {
            distanciaRecorrida = 1000;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public int getDistanciaPorIteracion() {
        return distanciaPorIteracion;
    }
}