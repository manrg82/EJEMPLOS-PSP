package ej7;

import java.util.HashMap;
import java.util.Map;

public class InventarioLibros {
    private Map<String, Integer> stock = new HashMap<>();

    public InventarioLibros() {
        stock.put("Harry Potter y la piedra filosofal", 7);
        stock.put("El Hombre Ilumninado", 15);
        stock.put("El Quijote", 20);
        stock.put("Cien años de soledad", 58);
    }

    public synchronized String realizarPedido(String titulo, int cantidad) {
        if (!stock.containsKey(titulo)) return "No disponible";
        int disponibles = stock.get(titulo);
        if (disponibles == 0) return "Agotado";
        int entrega = Math.min(disponibles, cantidad);
        stock.put(titulo, disponibles - entrega);
        return "Entregadas " + entrega + " unidades de " + titulo;
    }
}