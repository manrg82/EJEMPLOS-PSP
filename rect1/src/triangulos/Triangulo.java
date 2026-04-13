package triangulos;


public class Triangulo {
    private int id;
    private int base;
    private int altura;

    public Triangulo(int id, int base, int altura) {
        this.id = id;
        this.base = base;
        this.altura = altura;
    }

    public int getId() { return id; }
    public int getBase() { return base; }
    public int getAltura() { return altura; }
}