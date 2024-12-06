package TP1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class main {

	public static void main(String[] args) {
		Archivo a = new Archivo("C:\\archJava\\","datos", ".txt");
		Archivo resultados = new Archivo("C:\\archJava\\","resultados", ".txt");
		Archivo error = new Archivo("C:\\archJava\\","error", ".txt");
		Ficheros f = new Ficheros();
		ArrayList<Integer> array = new ArrayList<Integer>(almacenarNumeros(f, error));
		almacenarNumerosFile(f, a ,array);
		cuenta(array, resultados, error, f);
	}

	
	public static ArrayList<Integer> almacenarNumeros(Ficheros f, Archivo error) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		int aux;
		for(int i=0; i<=4;i++) {
			try{aux = Integer.parseInt(f.entradaDeDatos());
				array.add(aux);
			}
			catch(NumberFormatException e) {
				array.add(null);
			}
			}
		//for(int i=0; i<=4;i++){
		//f.ps.println(array.get(i));}
		return array;
	}
	public static void almacenarNumerosFile(Ficheros f, Archivo a, ArrayList<Integer> lista) {
		for( double i = 0; i>=lista.size();i++){
			int c = (int) i;
			a.guardar(a.getArchivo(), String.valueOf(lista.get(c)));
		}
	}
	public static void cuenta(ArrayList<Integer> lista, Archivo resultados, Archivo error, Ficheros f) {
			double aux = 0;
			for (int i = 0; i < lista.size()- 1; i++) {
	            try{
	            	int num1 = lista.get(i);
	            	int num2 = (lista.get(i+1)) - 3;
	            	if(num2 == 0) {
	            		
	            		throw new ArithmeticException("no se puede dividir por 0");
	            		
	            	}
	            	aux = (double) num1 / num2;
	            	String str = String.valueOf(num1).concat(" / ").concat(String.valueOf(num2)).concat("=").concat(String.valueOf(aux));
	            	resultados.guardar(resultados.getArchivo(), str);
	            }
	            catch(ArithmeticException e){
				
	            	error.guardar(error.getArchivo(), String.valueOf(e));
	            }
	            catch(NullPointerException e) {
					error.guardar(error.getArchivo(), String.valueOf(e));
	            }
	            catch(NumberFormatException e) {
					error.guardar(error.getArchivo(), String.valueOf(e));
	            }
		}		
	}


}
