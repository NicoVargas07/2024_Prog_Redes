package Readers2;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class Strings {
	
}
public void repaso() {
	
	Lectura leer = new Lectura();
	
	//String  = leer.entradaDeDatos();
	PrintStream ps = new PrintStream( System.out );
	String texto = "    Este va a ser el, texto <> que yo quiero , manipular.";
	
	ps.println("ASCII El char (3):" +  texto.charAt(3) );
	ps.println("sin espacio:" + texto.trim()  );
	ps.println("'el' esta en la posicion':" + texto.indexOf("el"));
	ps.println("el '>' este ne la pocicion:" + texto.indexOf( '>' ) );
	ps.println("ultima aparicion de la letra E" + texto.lastIndexOf( 'e' ) );
	ps.println("largo del texto" +  texto.length());
	
	ps.println("" + texto.equals( "hola" )   );
	ps.println("" + texto.compareTo( "hola" )  );
	ps.println("" + texto.compareToIgnoreCase( "HoLa" ) );
	
	ps.println("" + texto.contains( "que" )  );
	ps.println("" + texto.replace("a", "4") );
	ps.println("" + texto.replaceAll( " ", "_" ) );
	
	ps.println("" + texto.concat(" otro texto" ).concat("otro mas")  );
	ps.println("" + texto.toLowerCase() );
	ps.println("" + texto.toUpperCase() );
	
	ps.println("" + texto.startsWith("Este") );
	ps.println("" + texto.endsWith(".") );
	
	ps.println("" + texto.substring( 10 ) );
	ps.println("" + texto.substring( 10 , 20 ) );
	
	ps.println("" + texto.substring(  0  ,  texto.indexOf("<>")  ) );
	ps.println("" + texto );
	ps.println(" UNICODE (E):" + texto.codePointAt(4) );
	
	char[] letraAletra = texto.toCharArray();
	
	String separar = "esto, es un texto, que vamos, a separar.";
	String[] vec = separar.split(",");  //[ "esto" , " es un texto" , " que vamos" , " a separar."]
	separar.split("\\+");  //   \n   \+ 
	texto.split(" <> ");

	StringTokenizer st = new StringTokenizer(separar, ",");
	
	while(st.hasMoreTokens()) 
		
		ps.println(st.countTokens());
		ps.println(st.nextElement());
		ps.println(st.nextToken());
	    
	
}
}