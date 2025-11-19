package ej9Barberia;

import java.util.LinkedList;
import java.util.Queue;

class GestorBarberia {
    private Silla[] sillas;
    private Queue<Silla> colaEspera;
    private boolean abierto;
	
    public GestorBarberia(int numSillas) {
        sillas = new Silla[numSillas];
        colaEspera = new LinkedList<>();
        abierto = true;
        for (int i = 0; i < numSillas; i++) {
            sillas[i] = new Silla(i);
        }
    }

    public synchronized Silla solicitarSilla(String cliente) {
        if (!abierto) return null;

        for (Silla s : sillas) {
            if (!colaEspera.contains(s) && !s.isAtendido()) {
                System.out.println("El cliente " + cliente + " se ha sentado en la silla:" + s.getId());
                System.out.println(cliente + " estoy sentado en la silla:" + s.getId());
                colaEspera.add(s);
                notify(); 
                return s;
            }
        }
        System.out.println(cliente + " no había sillas libres, me marcho");
        return null;
    }

    public void esperarCorte(Silla silla) {
        synchronized (silla) {
            try {
                while (!silla.isAtendido()) {
                    silla.wait();
                }
                silla.setAtendido(false); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized Silla esperarCliente() throws InterruptedException {
        while (colaEspera.isEmpty() && abierto) {
            wait();
        }
        
        if (!abierto && colaEspera.isEmpty()) return null;
        
        return colaEspera.poll();
    }

    public void finalizarCorte(Silla silla, String barbero) {
        System.out.println(barbero + " atendiendo silla: " + silla.getId());
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        synchronized (silla) {
            System.out.println("Silla " + silla.getId() + " liberada por " + barbero);
            silla.setAtendido(true);
            silla.notify();
        }
    }

    public synchronized void cerrar() {
        abierto = false;
        notifyAll();
    }
}