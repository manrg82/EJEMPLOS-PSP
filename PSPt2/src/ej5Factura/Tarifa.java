package ej5Factura;
public class Tarifa {

    private int proximoNumeroFactura = 1;

    public synchronized Factura generarFactura(double consumo) {
        double importe;
        if (consumo < 15) {
            importe = consumo * 1.5;
        } else if (consumo < 30) {
            importe = consumo * 2.1;
        } else {
            importe = consumo * 3.0;
        }

        String numeroFactura = "FAC" + proximoNumeroFactura;
        proximoNumeroFactura++;

        return new Factura(numeroFactura, importe);
    }
}