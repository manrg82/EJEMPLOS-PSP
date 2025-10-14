package seis;

public class CalcularFactorial {
	
	
	public static long factorial(int num) {
	    if (num < 0) {
	        throw new IllegalArgumentException("No se puede calcular factorial de un número negativo");
	    }
	    
	    long resultado = 1;
	    for (int i = 2; i <= num; i++) {
	        resultado *= i;
	    }
	    
	    
	    return resultado;
	}


    public static void main(String[] args) {
    	
        int num = Integer.parseInt(args[0]);
        long factorial = factorial(num);
        
        
        System.out.println("El factorial de " + num + " es: " + factorial);
    }
	
	
}
