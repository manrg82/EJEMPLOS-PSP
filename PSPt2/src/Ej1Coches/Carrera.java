package Ej1Coches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carrera {
    private static Carrera instancia;
    
    private int distanciaTotal;
    private List<Coche> coches;

    private Carrera() {
        Random rand = new Random();
        this.distanciaTotal = rand.nextInt(1000) + 1;
        this.coches = new ArrayList<>();
    }

    public int getDistanciaTotal() {
		return distanciaTotal;
	}
    public static Carrera getInstancia() {
        if (instancia == null) {
            instancia = new Carrera();
        }
        return instancia;
    }

    public void agregarCoche(Coche coche) {
        coches.add(coche);
    }

    public void iniciarCarrera() {
        boolean carreraTerminada = false;

        while (!carreraTerminada) {
            for (Coche coche : coches) {
                coche.avanzar();
                if (coche.getDistanciaRecorrida() >= distanciaTotal) {
                    System.out.println("El coche " + coche.getNombre() + " ha ganado la carrera ¡¡¡¡");
                    carreraTerminada = true;
                    break;
                }

                System.out.println("El coche " + coche.getNombre() + " lleva recorrida el " +
                        (coche.getDistanciaRecorrida() * 100 / distanciaTotal) + " % de la distancia");
            }
        }
        mostrarPodium();
    }
    public void mostrarPodium() {
        coches.sort((c1, c2) -> Integer.compare(c2.getDistanciaRecorrida(), c1.getDistanciaRecorrida()));

        System.out.println("--- PODIUM ----");
        System.out.println("ORO: " + coches.get(0).getNombre() + " con " + coches.get(0).getDistanciaRecorrida() + " m.");
        System.out.println("PLATA: " + coches.get(1).getNombre() + " con " + coches.get(1).getDistanciaRecorrida() + " m.");
        System.out.println("BRONCE: " + coches.get(2).getNombre() + " con " + coches.get(2).getDistanciaRecorrida() + " m.");
    }
}