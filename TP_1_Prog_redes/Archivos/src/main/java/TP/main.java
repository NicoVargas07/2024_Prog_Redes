package TP;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) {

		PrintStream ps = new PrintStream(System.out);	
		ArrayList<Producto> listaProductos = new ArrayList();
		Fichero a = new Fichero("C:\\archJava\\","datos", ".txt");
		
		
		
		boolean i=false;		
		while (i==false) {
			ps.println("\u001B[34m 1) \u001B[0m\u001B[36m Es texto \u001B[0m \n\u001B[34m 2) \u001B[0m\u001B[36m Añadir producto \u001B[0m \n\u001B[34m 3) \u001B[0m\u001B[36m Guardar productos \u001B[0m \n\u001B[34m 4) \u001B[0m\u001B[36m Mostrar productos \u001B[0m \n\u001B[34m 0) \u001B[0m\u001B[36m Salir \u001B[0m");
			int aux=Integer.parseInt(entradaDatos());
			if(aux==1) {ps.println(numSwitch(esNumero(entradaDatos())));}
			if(aux==2) {listaProductos.add(CrearProducto());}
			if(aux==3) {saveProductos(listaProductos, a);}
			if(aux==4) {MostrarProductos(a);}
			if(aux==0) {i=true;}					
		}
	}
		

	
	public static float numSwitch(String valor) {
			
			if(valor.contains(".")) {
			     Float aux= Float.parseFloat(valor);
			     System.out.println("float");
			     return aux;
			}/*
			if(valor.contains(",")) {
				 
			     Float aux= Float.parseFloat(valor);
			     return aux;
			}*/
			return 0;
		}/*
	public static int numSwitch(String valor, Fichero a) {
		
		     int aux= Integer.parseInt(valor);
		     System.out.println("int");
		     return aux;
		return 0;
	}*/


	public static void MostrarProductos(Fichero a) {
		PrintStream ps = new PrintStream(System.out);
		ps.println(a.leerArchivo(a.getArchivo()));
	}
	
	
	
	
	public static Producto CrearProducto() {
		PrintStream ps = new PrintStream(System.out);
		ps.println("\u001B[91mingrese el nombre: \u001B[0m");
		String nombre = entradaDatos();
		ps.println("\u001B[91mingrese el precio de compra: \u001B[0m");
		Double precio = Double.parseDouble(entradaDatos());
		ps.println("\u001B[91mingrese el precio de venta: \u001B[0m");
		double venta = Double.parseDouble(entradaDatos());
		ps.println("\u001B[91mingrese el stock: \u001B[0m");
		int stock = Integer.parseInt(entradaDatos());
		Producto p = new Producto(nombre, precio, venta, stock);
		return p;
	};
	
	public static void saveProductos(ArrayList<Producto> listaP, Fichero arch){
		
		for(Producto p:listaP) {
			arch.guardar(arch.getArchivo(), p);
		}		
	}
	
	private static String esNumero(String valor){
        if(valor != null && valor.matches("[0-9.]+")) {
        	return valor;}
        return "0";
    }

	
	public static String entradaDatos(){
		String cadena = "";
		try {
			int Byte = -1;
			while(    (Byte = System.in.read())  != '\n'    ) 
			{
				if( Byte != 13 )
					cadena += (char)Byte;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cadena;
	}
}

