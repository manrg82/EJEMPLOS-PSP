package ej5Factura;
public class Factura {
    private String numero;
    private double importe;

    public Factura(String numero, double importe) {
        this.numero = numero;
        this.importe = importe;
    }

    public String getNumero() {
        return numero;
    }

    public double getImporte() {
        return importe;
    }
}