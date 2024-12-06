package TP;

public class Producto {

	private String nombre;
	private double precioCompra;
	private double precioVenta;
	private int stock;
	Producto(String nombre, double precioCompra, double precioVenta, int stock) {
		this.nombre = nombre;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.stock = stock;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecioCompra() {
		return precioCompra;
	}
	public void setPrecioCompra(double precioCompra) {
		this.precioCompra = precioCompra;
	}
	public double getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String mostrarDatos() {
		String aux = this.nombre.concat(";").concat(String.valueOf(this.precioCompra)).concat(";").concat(String.valueOf(this.precioVenta).concat(";").concat(String.valueOf(this.stock)));
		return aux;
	}
	
	
}

