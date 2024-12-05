package examen;

import java.io.*;
import java.util.*;

public class SistemaStock {
    public static void main(String[] args) throws IOException {
        GestorArchivo gestorArchivo = new GestorArchivo("datos.csv");
        if (!gestorArchivo.crearArchivo()) {
            throw new RuntimeException("No se pudo crear el archivo.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintStream writer = new PrintStream(System.out);

        boolean continuar = true;

        while (continuar) {
            writer.println("--- Menú ---");
            writer.println("1. Agregar un Producto");
            writer.println("2. Mostrar Todos los Productos");
            writer.println("3. Calcular Stock");
            writer.println("4. Salir");
            writer.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(reader.readLine());
            System.out.println(opcion);

            switch (opcion) {
                case 1:
                    agregarProducto(gestorArchivo, reader, writer);
                    break;
                case 2:
                    mostrarProductos(gestorArchivo, writer);
                    break;
                case 3:
                    calcularStock(gestorArchivo, writer);
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    writer.println("Opción inválida.");
            }
        }
    }

private static void agregarProducto(GestorArchivo gestorArchivo, BufferedReader reader, PrintStream writer)
        throws IOException {
    writer.print("Ingrese el nombre del producto: ");
    String nombre = validarEntradaTexto(reader);

    writer.print("Ingrese la marca del producto: ");
    String marca = validarEntradaTexto(reader);

    int cantidad = 0;
    boolean cantidadValida = false;
    while (!cantidadValida) {
        writer.print("Ingrese la cantidad (solo números enteros): ");
        String cantidadTexto = validarEntradaTexto(reader);
        try {
            cantidad = Integer.parseInt(cantidadTexto);
            if (cantidad < 0) {
                writer.println("La cantidad debe ser mayor o igual a 0.");
            } else {
                cantidadValida = true;
            }
        } catch (NumberFormatException e) {
            writer.println("Entrada inválida. Por favor, ingrese un número entero.");
        }
    }

    double precio = 0.0;
    boolean precioValido = false;
    while (!precioValido) {
        writer.print("Ingrese el precio (solo números decimales): ");
        String precioTexto = validarEntradaTexto(reader);
        try {
            precio = Double.parseDouble(precioTexto);
            if (precio < 0) {
                writer.println("El precio debe ser mayor o igual a 0.");
            } else {
                precioValido = true;
            }
        } catch (NumberFormatException e) {
            writer.println("Entrada inválida. Por favor, ingrese un número decimal.");
        }
    }

    Producto producto = new Producto(nombre, marca, cantidad, precio);
    gestorArchivo.agregarProducto(producto);
    writer.println("Producto agregado correctamente.");
}

    private static String validarEntradaTexto(BufferedReader reader, PrintWriter writer) throws IOException {
        String entrada;
        do {
            entrada = reader.readLine().trim();
            if (entrada.isEmpty()) {
                writer.println("La entrada no puede estar vacía. Intente nuevamente.");
            }
        } while (entrada.isEmpty());
        return entrada;
    }

    private static int validarEntradaIntPositivo(BufferedReader reader, PrintWriter writer) throws IOException {
        int cantidad = -1;
        while (cantidad <= 0) {
            try {
                String entrada = reader.readLine().trim();
                cantidad = Integer.parseInt(entrada);
                if (cantidad <= 0) {
                    writer.println("La cantidad debe ser un número entero positivo. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                writer.println("Por favor ingrese un número entero válido para la cantidad.");
            }
        }
        return cantidad;
    }

    
    private static double validarEntradaDoublePositivo(BufferedReader reader, PrintWriter writer) throws IOException {
        double precio = -1.0;
        while (precio <= 0) {
            try {
                String entrada = reader.readLine().trim();
                precio = Double.parseDouble(entrada);
                if (precio <= 0) {
                    writer.println("El precio debe ser un número positivo. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                writer.println("Por favor ingrese un número válido para el precio.");
            }
        }
        return precio;
    }


    private static void mostrarProductos(GestorArchivo gestorArchivo, PrintStream writer) {
        List<String> productos = gestorArchivo.leerProductos();
        writer.println("--- Lista de Productos ---");
        for (String producto : productos) {
            writer.println(producto);
        }
    }

    private static void calcularStock(GestorArchivo gestorArchivo, PrintStream writer) {
        Map<String, Integer> stockMap = new HashMap<>();
        Map<String, String> marcaMap = new HashMap<>();

        List<String> productos = gestorArchivo.leerProductos();
        for (String linea : productos) {
            String[] partes = linea.split(";");
            if (partes.length != 4) continue;

            String producto = partes[0];
            String marca = partes[1];
            int cantidad = Integer.parseInt(partes[2]);

            stockMap.put(producto, stockMap.getOrDefault(producto, 0) + cantidad);
            marcaMap.put(producto, marca);
        }

        writer.println("--- Stock de Productos ---");
        for (String producto : stockMap.keySet()) {
            writer.println(producto + " - " + marcaMap.get(producto) + " - " + stockMap.get(producto));
        }
    }

    private static String validarEntradaTexto(BufferedReader reader) throws IOException {
        String entrada;
        do {
            entrada = reader.readLine();
        } while (entrada.isEmpty());
        return entrada;
    }
}
