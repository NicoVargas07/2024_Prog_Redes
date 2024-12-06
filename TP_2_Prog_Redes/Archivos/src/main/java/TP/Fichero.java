package TP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Fichero {

	private File archivo;
	private PrintStream ps;
	public Fichero (String ruta, String nombre, String extencion) {
		String datosArch = ruta.concat(nombre.concat(extencion));
		archivo = new File(datosArch);
		
	}

	public File getArchivo() {
		return archivo;
	}

	public void guardar(File a, Producto p) {
		try {
			if(!a.exists()) {
				try {
					a.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileOutputStream fosA = new FileOutputStream(a,true);
			ps = new PrintStream(fosA);
			ps.println(p.mostrarDatos());
			ps.flush();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			ps.close();
		}
	}
	public String leerArchivo(File a) {
		
		try {
			
			FileReader frA=new FileReader(a);
			BufferedReader br = new BufferedReader(frA);
			String linea= "";
			String texto= "";
			try {
				while((linea = br.readLine()) != null) {
				 texto+=linea.concat("\n");
				}
				br.close();
				return texto;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return "No hay datos para leer";
		
	}
	
	
}
