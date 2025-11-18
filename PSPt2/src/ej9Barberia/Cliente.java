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
        try {
            int idSilla = gestor.entrarEnBarberia(nombre);
            
            if (idSilla != -1) {
                gestor.esperarCortePelo(idSilla);
                System.out.println(nombre + " ya he sido atendido, me marcho");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}