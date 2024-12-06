package TP1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Archivo {
	PrintStream ps = new PrintStream(System.out);
	File archivo;
	public Archivo (String ruta, String nombre, String extencion) {
		String datosArch = ruta.concat(nombre.concat(extencion));
		archivo = new File(datosArch);
		}

public File getArchivo() {
	return archivo;
}


public void guardar(File a, String dato) {
	try {
		if(!a.exists()) {
			try {
				a.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fosArch = new FileOutputStream(a,true);
		ps = new PrintStream(fosArch);
		ps.println(dato);
		ps.flush();
	} 
	catch (FileNotFoundException e) {
		e.printStackTrace();
	}finally {
		ps.close();
	}
}
}