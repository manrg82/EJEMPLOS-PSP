package ej9Barberia;
class Cliente extends Thread {
    private GestorBarberia gestor;
    private String nombre;

    public Cliente(GestorBarberia gestor, int id) {
        this.gestor = gestor;
        this.nombre = "Cliente" + id;
    }

    @Override
    public void run() {
        Silla silla = gestor.solicitarSilla(nombre);
        if (silla != null) {
            gestor.esperarCorte(silla);
            System.out.println(nombre + " ya he sido atendido, me marcho");
        }
    }
}