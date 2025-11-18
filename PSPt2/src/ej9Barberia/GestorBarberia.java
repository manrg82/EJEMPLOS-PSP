package ej9Barberia;
import java.util.LinkedList;
import java.util.Queue;

class GestorBarberia {
    private int numSillas;
    private int[] sillas; 
    private boolean[] clienteAtendido;
    
    private Queue<Integer> colaSillasOcupadas = new LinkedList<>();

    public GestorBarberia(int numSillas) {
        this.numSillas = numSillas;
        this.sillas = new int[numSillas];
        this.clienteAtendido = new boolean[numSillas];
        for (int i = 0; i < numSillas; i++) {
            sillas[i] = -1; 
            clienteAtendido[i] = false;
        }
    }
    public synchronized int entrarEnBarberia(String nombreCliente) {
        int sillaLibre = -1;
        for (int i = 0; i < numSillas; i++) {
            if (sillas[i] == -1) {
                sillaLibre = i;
                break;
            }
        }
        if (sillaLibre == -1) {
            System.out.println(nombreCliente + " no había sillas libres, me marcho");
            return -1;
        }
        sillas[sillaLibre] = 1;
        clienteAtendido[sillaLibre] = false;
        colaSillasOcupadas.add(sillaLibre);
        System.out.println("El cliente " + nombreCliente + " se ha sentado en la silla:" + sillaLibre);
        System.out.println(nombreCliente + " estoy sentado en la silla:" + sillaLibre);
        notifyAll(); 
        return sillaLibre;
    }
    public synchronized void esperarCortePelo(int idSilla) throws InterruptedException {
        while (!clienteAtendido[idSilla]) {
            wait(); 
        }
        sillas[idSilla] = -1; 
    }
    public synchronized int siguienteCliente(String idBarbero) throws InterruptedException {
        while (colaSillasOcupadas.isEmpty()) {
            wait(); 
        }
        
        return colaSillasOcupadas.poll();
    }
    public synchronized void finCorte(int idSilla, String idBarbero) {
        System.out.println("Silla " + idSilla + " liberada por " + idBarbero);
        clienteAtendido[idSilla] = true;
        notifyAll();
    }
}