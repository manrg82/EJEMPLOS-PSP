
public class PruebaStatic {
	
	private static int dato = 0;
	
	public static void setDato(int value) {
		dato = value;
	}
	
	public static int getDato() {
		return dato;
	}
	
	public static void main(String[] args) {
		PruebaStatic prueba1 = new PruebaStatic();
		PruebaStatic prueba2 = new PruebaStatic();
		PruebaStatic prueba3 = new PruebaStatic();
		
		prueba1.setDato(1);
		System.out.println("prueba1: "+prueba1.getDato());
		System.out.println("prueba2: "+prueba2.getDato());
		System.out.println("prueba3: "+prueba3.getDato());
		
		prueba2.setDato(2);
		System.out.println("prueba1: "+prueba1.getDato());
		System.out.println("prueba2: "+prueba2.getDato());
		System.out.println("prueba3: "+prueba3.getDato());

	}

}
