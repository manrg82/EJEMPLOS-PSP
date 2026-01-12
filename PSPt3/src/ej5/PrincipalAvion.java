package ej5;

public class PrincipalAvion {
    public static void main(String[] args) {
        AvionNormal perseguido = new AvionNormal(1000, 100, "derecha");
        AvionPerseguidor perseguidor = new AvionPerseguidor(2000, 200, "izquierda");

        perseguido.addObserver(perseguidor);

        perseguido.subir();
        perseguido.bajar();
        perseguido.bajar();
        perseguido.girar("izquierda");
        perseguido.girar("derecha");
        perseguido.acelerar();

        System.out.println("Datos finales perseguido: " + perseguido.getAltura() + "m, " + perseguido.getVelocidad() + "km, " + perseguido.getDireccion());
        perseguidor.mostrarEstadoFinal();
    }
}
