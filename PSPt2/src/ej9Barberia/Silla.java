package ej9Barberia;

class Silla {
    private int id;
    private boolean atendido;

    public Silla(int id) {
        this.id = id;
        this.atendido = false;
    }

    public int getId() {
        return id;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }
}
