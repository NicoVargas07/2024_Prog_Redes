package examen;

public class Producto {
    private String nombre;
    private String marca;
    private int cantidad;
    private double precio;

    public Producto(String nombre, String marca, int cantidad, double precio) {
        this.nombre = nombre;
        this.marca = marca;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public String toCSV() {
        return nombre + ";" + marca + ";" + cantidad + ";" + precio;
    }
    
}
