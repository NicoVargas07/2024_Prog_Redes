package Tp_5_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class main {
    private static Logger logger = Logger.getLogger("main");

    public static void main(String[] args) throws InterruptedException {
        
    	//crea una lista de equipos con array list
        List<Equipo> equipos = new ArrayList<>();
        //for que añade a la lista de equipos un equipo y le asigna el numero de equipo que es
        for (int i = 1; i <= 4; i++) {
            equipos.add(new Equipo("Equipo " + i));
        }

        // agarrar tiempo
        long tiempoInicio = System.currentTimeMillis();

        // comienza la carrera lestgo
        logger.info("comienza la carrera");
        //for each para iniciar todos los hilos
        for (Equipo equipo : equipos) {
            new Thread(equipo).start();
        }
        // fore each para esperar a que todos terminen
        for (Equipo equipo : equipos) {
            equipo.esperarFin();
        }
        // ni bien termina el ultimo hilo guardo en una variable el tiempo final
        long tiempoFin = System.currentTimeMillis();
        //y con una resta saco el tiempo total
        long tiempoTotal = tiempoFin - tiempoInicio;

        // le paso la lista al metodo mostrar ganador para que chequee quien gana
        mostrarGanador(equipos, tiempoTotal);
    }
    private static void mostrarGanador(List<Equipo> equipos, long tiempoTotal) {

        equipos.sort(Comparator.comparing(Equipo::getTiempoTotal));
        System.out.println("\033[33m resultado carrera \033[0m");
        for (int i = 0; i < equipos.size(); i++) {
            Equipo equipo = equipos.get(i);
            System.out.printf("\033[32m%d. %s - Tiempo: %d ms\033[0m\n", 
                    i + 1, equipo.getNombre(), equipo.getTiempoTotal());
        }
        System.out.printf("el equipo ganador es %s con %d ms", 
                equipos.get(0).getNombre(), equipos.get(0).getTiempoTotal());
    }

}
