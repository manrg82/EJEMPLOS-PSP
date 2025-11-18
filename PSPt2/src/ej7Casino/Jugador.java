package ej7Casino;

class Jugador extends Thread {
	private static final int NUMERO_MAX = 10;
	public static final int APUESTA = 10;
	private static final int SALDO_INICIAL = 100;
    private int saldo;
    private int numeroApostado;
    private Banca banca;
    

    public Jugador(int numJugador, Banca banca) {
    	this.setName("Jugador"+numJugador);
        this.saldo = SALDO_INICIAL;
        this.banca = banca;
    }

    public void run() {
    	boolean booTieneSaldo = (saldo > 0) ? true : false;
        while (booTieneSaldo) {
            //apuesta si tiene dinero suficiente 
            if ((saldo - APUESTA) >= 0) {
            	// El jugador hace una apuesta
                numeroApostado = 1 + (int)(Math.random() * (NUMERO_MAX-1)); // Números del 1 al 10
                System.out.println(Thread.currentThread().getName() + " apuesta al número " + numeroApostado);

                // Se resta la apuesta del saldo y se incrementa la banca
                saldo -= APUESTA;
                banca.recibirApuesta(APUESTA);
                
                try {
					banca.comprobarResultado(this);
				} catch (InterruptedException e) {
					e.printStackTrace();
					this.interrupt();
				}
            } else {
            	booTieneSaldo = false;
                System.out.println(Thread.currentThread().getName() + " ya no tiene saldo para seguir apostando.");
            }
        }
    }
    
    public void incrementarSaldo(int ganancia) {
    	saldo = saldo + ganancia;
    }

    public int getSaldo() {
        return saldo;
    }
    
    public int getNumeroApostado() {
    	return numeroApostado;
    }
    
	
}
