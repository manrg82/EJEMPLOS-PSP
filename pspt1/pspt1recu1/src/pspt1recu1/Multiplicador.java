package pspt1recu1;

public class Multiplicador {
	public static void main(String[] args) {
		int a=Integer.parseInt(args[0]);
		int b=Integer.parseInt(args[1]);
		System.out.println(multiplicar(a,b));
	}
	public static int multiplicar(int a, int b) {
		return a*b;
	}
}
