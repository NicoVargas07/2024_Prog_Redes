package Tp_5_2;

import java.util.Random;

public class Atleta extends Thread {
    private static Random random = new Random();
    private long tiempoActividad; 

    public Atleta(String nombre) {
        super(nombre); 	
        this.tiempoActividad = 300 + random.nextInt(2701);  // tiempo random
    }

    @Override
    public void run() {
        try {
            // muestra q el atleta arranco su tarea
            System.out.printf("\033[34m%s está corriendo durante %d ms\033[0m\n", 
                    getName(), tiempoActividad);

            // tiempo de la actividad
            Thread.sleep(tiempoActividad);

            // Imprimir que el atleta ha terminado su tarea
            System.out.printf("\033[32m%s ha terminado su parte\033[0m\n", getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


