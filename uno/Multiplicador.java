package com.psp.uno;

public class Multiplicador {

	
	public static int multiplicar(int num1, int num2) {
		
		return num1 * num2;
	}
	
	
	public static void main (String [] args) {
		
		
		
		int n1=Integer.parseInt(args[0]);
        int n2=Integer.parseInt(args[1]);
        
        int resultado = Multiplicador.multiplicar(n1,n2);
		
		
		System.out.println(resultado);
	}
	
}
