package examen;

import java.io.*;
import java.util.*;

public class GestorArchivo {
    private String archivo;

    public GestorArchivo(String archivo) {
        this.archivo = archivo;
    }

    public boolean crearArchivo() {
        File file = new File(archivo);
        try {
            return file.createNewFile() || file.exists();
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el archivo.", e);
        }
    }

    public void agregarProducto(Producto producto) {
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write(producto.toCSV() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el archivo.", e);
        }
    }

    public List<String> leerProductos() {
        List<String> productos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                productos.add(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo.", e);
        }
        return productos;
    }
}
