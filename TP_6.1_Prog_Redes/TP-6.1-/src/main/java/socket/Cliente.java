package socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    InetAddress IP;
    int puerto = 7777;
    boolean nickEnviado = true;
    String nick = "";

    Socket sock;
    DataInputStream dis;
    DataOutputStream dos;
    BufferedReader buff;
    PrintStream ps;

    public Cliente() {
        try {
            // Inicializar conexión
            IP = InetAddress.getByName("localhost");
            sock = new Socket(IP, puerto);

            dis = new DataInputStream(sock.getInputStream());
            dos = new DataOutputStream(sock.getOutputStream());
            buff = new BufferedReader(new InputStreamReader(System.in));
            ps = new PrintStream(System.out);

            // Enviar nickname
            if (sock.isConnected() && nickEnviado) {
                ps.println("Ingresa tu ID: ");
                nick = buff.readLine();
                dos.writeUTF(nick);
                nickEnviado = false;

                ps.println("Bienvenido " + nick);
            }

            // Iniciar hilos
            startEnviarMensajes();
            startRecibirMensajes();

        } catch (UnknownHostException e) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Host desconocido", e);
        } catch (IOException e) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error de I/O", e);
        }
    }

    private void startEnviarMensajes() {
        Thread enviarMensajes = new Thread(() -> {
            String msg = "";
            try {
                while (true) {
                    ps.print("\t-> ");
                    msg = buff.readLine();

                    if (msg.equalsIgnoreCase("/salir")) {
                        dos.writeUTF(msg);
                        cerrarConexion();
                        break;
                    }

                    //enviar mensaje
                    dos.writeUTF(msg);
                }
            } catch (IOException e) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error al enviar mensaje", e);
            }
        });
        enviarMensajes.setName("enviar");
        enviarMensajes.start();
    }
    private void startRecibirMensajes() {
        Thread recibirMensajes = new Thread(() -> {
            String msg = "";
            try {
                while (true) {
                    msg = dis.readUTF();

                    if (msg.equalsIgnoreCase("/salir")) {
                        ps.println("\nEl servidor te ha desconectado.");
                        cerrarConexion();
                        break;
                    }

                    //  detectar mensajes privados
                    if (msg.startsWith("[MP]")) {
                        ps.println("\n" + Servidor.ANSI_PURPLE + msg + Servidor.ANSI_RESET);
                    } else {
                        ps.println("\n" + Servidor.ANSI_YELLOW + msg + Servidor.ANSI_RESET);
                    }

                    ps.print("\t-> ");
                }
            } catch (IOException e) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error al recibir mensaje", e);
            }
        });
        recibirMensajes.setName("recibir");
        recibirMensajes.start();
    }


    private void cerrarConexion() {
        try {
            if (sock != null) sock.close();
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            ps.println("Conexión cerrada.");
            System.exit(0);
        } catch (IOException e) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", e);
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}
