import java.util.Observable;

public class AvionNormal extends Observable {
    private int altura;
    private int velocidad;
    private String direccion;

    public AvionNormal(int altura, int velocidad, String direccion) {
        this.altura = altura;
        this.velocidad = velocidad;
        this.direccion = direccion;
    }

    public void subir() {
        this.altura += 100;
        setChanged();
        notifyObservers();
    }

    public void bajar() {
        this.altura -= 100;
        setChanged();
        notifyObservers();
    }

    public void acelerar() {
        this.velocidad += 200;
        setChanged();
        notifyObservers();
    }

    public void frenar() {
        this.velocidad -= 200;
        setChanged();
        notifyObservers();
    }

    public void girar(String nuevaDireccion) {
        this.direccion = nuevaDireccion;
        setChanged();
        notifyObservers();
    }

    public int getAltura() {
        return altura;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public String getDireccion() {
        return direccion;
    }
}