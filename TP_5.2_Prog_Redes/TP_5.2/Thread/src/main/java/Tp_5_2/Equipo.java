	package Tp_5_2;

	import java.util.ArrayList;
	import java.util.List;
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;

	public class Equipo implements Runnable {
	    private String nombre;
	    private List<Atleta> atletas = new ArrayList();
	    private long tiempoTotal;
	    private Lock lock = new ReentrantLock();

	    public Equipo(String nombre) {
	        this.nombre = nombre;
	        // crea los 4 randoms
	        for (int i = 1; i <= 4; i++) {
	            atletas.add(new Atleta("Atleta " + i + " (" + nombre + ")"));
	        }
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public long getTiempoTotal() {
	        return tiempoTotal;
	    }

	    // hace 1 hilo x team y sincroniza corredores 
	    @Override
	    public void run() {
	        long tiempoInicio = System.currentTimeMillis();

	        for (int i = 0; i < atletas.size(); i++) {
	            Atleta atleta = atletas.get(i);
	            atleta.start();

	            try {
	                atleta.join();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        long tiempoFin = System.currentTimeMillis();
	        tiempoTotal = tiempoFin - tiempoInicio;
	    }

	    // ver si termino y esperar
	    public void esperarFin() throws InterruptedException {
	        lock.lock();
	        try {
	            for (Atleta atleta : atletas) {
	                atleta.join();
	            }
	        } finally {
	            lock.unlock();
	        }
	    }
	}
