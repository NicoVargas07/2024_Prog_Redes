package socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_MAGENTA = "\u0033[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
	public static final String[] PALABRAS_PROHIBIDAS = { 
	        "puto", "autista", "retrasado", "marica", "gay" 
	    };

    PrintStream ps;
    static HashMap<String, ClienteCli> ClientesConectados;


    ServerSocket serverSock;
    Socket sockCli;
    DataInputStream dis;
    DataOutputStream dos;
    int puerto = 7777;

    public Servidor() {
        try {
            ps = new PrintStream(System.out);
            dis = null;
            dos = null;
            ClientesConectados = new HashMap<>();

            serverSock = new ServerSocket(puerto);

            // Verificar clientes desconectados periódicamente
            Thread verificarLista = new Thread(() -> {
                while (true) {
                    synchronized (ClientesConectados) {
                        ClientesConectados.entrySet().removeIf(entry -> {
                            ClienteCli cli = entry.getValue();
                            if (!cli.getSock().isConnected() || !cli.isConected()) {
                                cli.notificarClientes(false);
                                return true;
                            }
                            return false;
                        });
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            verificarLista.start();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        // Hilo para manejar la entrada de consola del servidor
        Thread entradaConsola = new Thread(() -> {
            try (BufferedReader entradaTexto = new BufferedReader(new InputStreamReader(System.in))) {
                while (true) {
                    ps.println(ANSI_YELLOW + "Escribe un mensaje (formato: 'global:mensaje' o 'nickname:mensaje'):" + ANSI_RESET);
                    String textoConsola = entradaTexto.readLine(); // Leer entrada de consola

                    if (textoConsola != null) {
                        if (textoConsola.startsWith("global:")) {
                            // mensaje global
                            String mensaje = textoConsola.substring(7).trim();
                            enviarMensajeGlobal(mensaje);
                        } else if (textoConsola.contains(":")) {
                            // mensaje privado
                            String[] partes = textoConsola.split(":", 2);
                            String nickname = partes[0].trim();
                            String mensaje = partes[1].trim();
                            enviarMensajePrivado(nickname, mensaje);
                        } else {
                            ps.println(ANSI_RED + "Formato no válido. Usa 'global:mensaje' o 'nickname:mensaje'" + ANSI_RESET);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        entradaConsola.start();
        // aceptar clientes
        while (true) {
            try {
                ps.println("Esperando conexión de un cliente...\n");
                sockCli = serverSock.accept();

                ps.println(Servidor.ANSI_CYAN + "Cliente conectado: " + sockCli.getInetAddress().getHostAddress() + ANSI_RESET);

                dis = new DataInputStream(sockCli.getInputStream());
                dos = new DataOutputStream(sockCli.getOutputStream());

                ps.println(ANSI_CYAN + "Creando un cliente... esperando identificación..." + ANSI_RESET);
                String nickName = dis.readUTF();

                // verificar si el nickname ya esta en uso
                synchronized (ClientesConectados) {
                    if (ClientesConectados.containsKey(nickName)) {
                        dos.writeUTF(ANSI_RED + "El nickname '" + nickName + "' ya está en uso. Desconectando..." + ANSI_RESET);
                        sockCli.close();
                        continue;
                    }
                }

                ClienteCli cli = new ClienteCli(sockCli, nickName, dis, dos);
                synchronized (ClientesConectados) {
                    ClientesConectados.put(nickName, cli);
                }

                ps.println(ANSI_RED + "El cliente " + cli.getNickName() + " accedió al servidor.\n" + ANSI_RESET);

                cli.getHilo().start();
                cli.notificarClientes(true);

            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Meodo para enviar un mensaje global
    private void enviarMensajeGlobal(String mensaje) {
        ps.println(ANSI_GREEN + "Enviando mensaje global: " + mensaje + ANSI_RESET);
        synchronized (ClientesConectados) {
            for (ClienteCli cli : ClientesConectados.values()) {
                try {
                    cli.getDos().writeUTF(ANSI_YELLOW + "[Servidor]: " + mensaje + ANSI_RESET);
                } catch (IOException e) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    // metodo para enviar un mensaje privado
    private void enviarMensajePrivado(String nickname, String mensaje) {
        synchronized (ClientesConectados) {
            ClienteCli cli = ClientesConectados.get(nickname);
            if (cli != null) {
                try {
                    cli.getDos().writeUTF(ANSI_CYAN + "[Servidor] (Mensaje privado): " + mensaje + ANSI_RESET);
                    ps.println(ANSI_GREEN + "Mensaje enviado a " + nickname + ": " + mensaje + ANSI_RESET);
                } catch (IOException e) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                ps.println(ANSI_RED + "No se encontró al cliente con nickname: " + nickname + ANSI_RESET);
            }
        }
    }
}

