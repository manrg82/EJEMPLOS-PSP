package ej6Boxeo;

public class Ring {

    private int numCombates = 0;
    private final int MAX_COMBATES = 100;

    public synchronized boolean hayCombate() {
        return numCombates < MAX_COMBATES;
    }

    public synchronized boolean realizarCombate(String atacante, String rival) {
        if (numCombates < MAX_COMBATES) {
            numCombates++;
            System.out.println(atacante + " pega a " + rival + " [" + numCombates + "]");
            return true;
        }
        return false;
    }

    public int getNumCombates() {
        return numCombates;
    }
}