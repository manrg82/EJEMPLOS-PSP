package ej8Filosofos;

public class MainFilosofos {
    public static void main(String[] args) {
        int numFilosofos = 5;
        Mesa mesa = new Mesa(numFilosofos);
        for (int i = 0; i < numFilosofos; i++) {
            new Filosofo(i, mesa).start();
        }
    }
}