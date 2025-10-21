package Ej1Coches;

public class Principal {
    public static void main(String[] args) {
        try {
            // Crear la carrera
            Carrera carrera = Carrera.getInstancia();
            System.out.println("Distancia carrera: " + carrera.getDistanciaTotal());

            // Crear los coches
            Coche opel = new Coche("Opel");
            Coche ford = new Coche("Ford");
            Coche seat = new Coche("Seat");

            // Mostrar información sobre los coches
            System.out.println("El coche " + opel.getNombre() + " avanza " + opel.getDistanciaPorIteracion() + " metros cada vez.");
            System.out.println("El coche " + ford.getNombre() + " avanza " + ford.getDistanciaPorIteracion() + " metros cada vez.");
            System.out.println("El coche " + seat.getNombre() + " avanza " + seat.getDistanciaPorIteracion() + " metros cada vez.");

            // Agregar los coches a la carrera
            carrera.agregarCoche(opel);
            carrera.agregarCoche(ford);
            carrera.agregarCoche(seat);

            // Iniciar la carrera
            carrera.iniciarCarrera();
        } catch (Exception e) {
            System.err.println("Error en el programa: " + e.getMessage());
        }
    }
}

