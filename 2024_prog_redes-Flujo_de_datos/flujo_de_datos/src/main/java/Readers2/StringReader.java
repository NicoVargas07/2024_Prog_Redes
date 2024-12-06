package Readers2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StringReader {

	PrintStream ps;
	InputStreamReader isr;
	BufferedReader br;
	public StringReader() {
		isr = new InputStreamReader(System.in);
		ps = new PrintStream(System.out);
		br = new BufferedReader(isr);		
	}
	
	public String leer() {
		
		try {
			br.readLine();

		} catch (Exception e) {
			Logger.getLogger( StringReader.class.getName())
			.log( Level.WARNING, null , e );		}
		return null;
		
	}
}
