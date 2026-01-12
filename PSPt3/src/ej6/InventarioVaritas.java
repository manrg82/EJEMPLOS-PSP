package ej6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventarioVaritas {
    private List<String> varitas = new ArrayList<>(Arrays.asList(
        "Varita de Saúco", "Varita de Acebo y Pluma de Fénix", 
        "Varita de Vid y Pelo de Unicornio", "Varita de Fresno y Nervio de Dragón",
        "Varita de Cerezo y Pluma de Fénix", "Varita de Nogal y Pelo de Thestral",
        "Varita de Abeto y Pelo de Unicornio", "Varita de Sauce y Nervio de Dragón",
        "Varita de Espino y Pluma de Fénix", "Varita de Tejo y Pluma de Fénix"
    ));

    public synchronized String entregarVarita() {
        if (varitas.isEmpty()) return null;
        return varitas.remove(0);
    }
}