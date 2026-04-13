package triangulos;

public class ImprimirTriangulo {

    public static void main(String[] args) {
        // Control de excepciones solicitado en el enunciado
        if (args.length != 3) {
            throw new IllegalArgumentException("Se necesitan 3 parámetros: id, base y altura.");
        }

        try {
            int id = Integer.parseInt(args[0]);
            double base = Double.parseDouble(args[1]);
            double altura = Double.parseDouble(args[2]);

            // Calcular el área: (base * altura) / 2
            double area = (base * altura) / 2;

            // Imprimir el formato exacto que pide el ejemplo
            // Como el padre redirige la salida, este println escribirá dentro del .txt
            System.out.println("Base: " + args[1] + ", Altura: " + args[2] + ", Area: " + area);

        } catch (NumberFormatException e) {
            System.err.println("Error: Los parámetros deben ser números.");
            System.exit(1); // Salimos con código de error
        }
    }
}
