package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteCli implements Runnable {
	private boolean isBanned;
	private long banEndTime;
    
    String nickName = "";
    Socket sock;
    Thread hilo;
    final DataInputStream disCliente;
    final DataOutputStream dosCliente;
    boolean isConected;
    PrintStream ps;
    
    public ClienteCli(Socket sock, String nick, DataInputStream in, DataOutputStream out) {
        this.nickName = nick;
        this.sock = sock;
        this.disCliente = in;
        this.dosCliente = out;    
        
        ps = new PrintStream(System.out);
        this.isConected = true;
        this.hilo = new Thread(this, nickName);
    }

    
    @Override
    public void run() {
        String msgRecibido = "";
        String destino = "";

        while (this.sock.isConnected() && this.isConected) {
            try {
                // Leer mensaje recibido
                msgRecibido = this.disCliente.readUTF().trim();

                // validar palabras prohibidas
                if (contienePalabraProhibida(msgRecibido)) {
                    this.dosCliente.writeUTF(Servidor.ANSI_RED 
                            + "[ERROR] Tu mensaje contiene palabras no permitidas y fue cancelado." 
                            + Servidor.ANSI_RESET);
                    continue;
                }

                // identificar el destino y contenido del mensaje
                if (msgRecibido.contains("#")) {
                    StringTokenizer token = new StringTokenizer(msgRecibido, "#");
                    destino = token.nextToken().trim().toLowerCase();
                    msgRecibido = token.nextToken().trim();
                } else {
                    destino = "";
                }

                ps.println("\n"
                        + Servidor.ANSI_PURPLE
                        + "El cliente "
                        + Servidor.ANSI_GREEN
                        + this.nickName
                        + Servidor.ANSI_PURPLE
                        + " envia: "
                        + Servidor.ANSI_YELLOW
                        + msgRecibido + "\n\t"
                        + Servidor.ANSI_PURPLE
                        + " al cliente =>"
                        + Servidor.ANSI_CYAN
                        + (destino.equalsIgnoreCase("") ? " Todos" : " ".concat(destino.toUpperCase()))
                        + "\n"
                        + Servidor.ANSI_RESET
                );

                // enviar mensaje al destino (misma lógica actual)
                enviarMensaje(destino, msgRecibido);

            } catch (IOException ex) {
                Logger.getLogger(ClienteCli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // método para verificar si un mensaje contiene palabras prohibidas
    private boolean contienePalabraProhibida(String mensaje) {
        String mensajeLower = mensaje.toLowerCase(); // Convertir a minúsculas
        for (String palabra : Servidor.PALABRAS_PROHIBIDAS) {
            if (mensajeLower.contains(palabra)) {
                return true;
            }
        }
        return false;
    }

    // método para enviar el mensaje (lógica existente, optimizada)
    private void enviarMensaje(String destino, String mensaje) {
        boolean clienteEncontrado = false;
        for (ClienteCli cli : Servidor.ClientesConectados.values()) {
            if (mensaje.equalsIgnoreCase("")) break;

            // mensaje Privado
            if (cli.getNickName().equalsIgnoreCase(destino) && this.isConected) {
                clienteEncontrado = true;
                try {
                    cli.dosCliente.writeUTF("[MP] " + this.nickName + ": " + mensaje);
                    this.dosCliente.writeUTF("[MP] enviado a " + cli.getNickName() + ": " + mensaje);
                } catch (IOException ex) {
                    ps.println(Servidor.ANSI_RED + "<Cliente no disponible: " + destino.toUpperCase() + ">" + Servidor.ANSI_RESET);
                }
                break;
            }

            // mensaje Global
            if (destino.equalsIgnoreCase("") && this.isConected && !cli.getNickName().equalsIgnoreCase(this.nickName)) {
                try {
                    cli.dosCliente.writeUTF(this.nickName + ": " + mensaje);
                } catch (IOException ex) {
                    ps.println(Servidor.ANSI_RED + "<Cliente no disponible: " + cli.getNickName().toUpperCase() + ">" + Servidor.ANSI_RESET);
                }
            }
        }

        // notificar si el destinatario no fue encontrado
        if (!clienteEncontrado && !destino.equalsIgnoreCase("")) {
            try {
                this.dosCliente.writeUTF("[ERROR] El cliente " + destino.toUpperCase() + " no está conectado o no existe.");
            } catch (IOException ex) {
                Logger.getLogger(ClienteCli.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps.println(Servidor.ANSI_RED + "<Cliente no encontrado: " + destino.toUpperCase() + ">" + Servidor.ANSI_RESET);
        }
    }



    
    
    void notificarClientes(boolean estado) {
    	for (ClienteCli cli : Servidor.ClientesConectados.values()) {
            if (!cli.getNickName().equals(this.nickName) && cli.isConected()) {
                try {
                    if (estado) {
                        cli.dosCliente.writeUTF(Servidor.ANSI_GREEN
                                + "\t---"
                                + this.getNickName()
                                + " se ha CONECTADO---"
                                + Servidor.ANSI_RESET
                        );                        
                    } else {
                        cli.dosCliente.writeUTF(Servidor.ANSI_RED
                                + "\t---"
                                + this.getNickName()
                                + " se ha DESCONECTADO---"
                                + Servidor.ANSI_RESET
                        );
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ClienteCli.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        }
    }
    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean isBanned, long banDuration) {
        this.isBanned = isBanned;
        this.banEndTime = System.currentTimeMillis() + banDuration;
    }
    
    public String getNickName() {
        return nickName;
    }

    public Socket getSock() {
        return sock;
    }

    public Thread getHilo() {
        return hilo;
    }

    public boolean isConected() {
        return isConected;
    }

    public DataOutputStream getDos() {
        return dosCliente;
    }
	public long getBanEndTime() {
	    return banEndTime;
	}
}