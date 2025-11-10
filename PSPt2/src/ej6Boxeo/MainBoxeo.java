package ej6Boxeo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainBoxeo {

    public static void main(String[] args) {
        Ring ring = new Ring();
        Random rand = new Random();

        List<Boxeador> boxeadores = new ArrayList<>();
        boxeadores.add(new Boxeador("Boxeador 1", ring));
        boxeadores.add(new Boxeador("Boxeador 2", ring));
        boxeadores.add(new Boxeador("Boxeador 3", ring));
        boxeadores.add(new Boxeador("Boxeador 4", ring));

        for (Boxeador b : boxeadores) {
            Boxeador rival = boxeadores.get(rand.nextInt(boxeadores.size()));
            b.setRival(rival);
            System.out.println("El rival de " + b.getNombre() + " es " + rival.getNombre());
        }

        List<Thread> hilos = new ArrayList<>();
        for (Boxeador b : boxeadores) {
            Thread t = new Thread(b);
            hilos.add(t);
            t.start();
        }

        try {
            for (Thread t : hilos) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- FIN DEL COMBATE ---");
        for (Boxeador b : boxeadores) {
            b.mostrarResultados();
        }
    }
}