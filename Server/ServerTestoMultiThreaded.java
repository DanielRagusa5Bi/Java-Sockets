/*
 * ServerTesto MultiThreaded.java Server che attende per richieste di connessioni da Clients
 * e li gestisce in modo contemporaneo generando un socket "worker" per ogni connessione.
 * 
 */

/**
 *
 * @author Prof. Matteo Palitto
 * @author Jianu Alina
 * @author Ragusa Daniel
 * @author Stropiana Alessandro
 */
import java.net.*;
import java.io.*;
import java.util.Vector;

public class ServerTestoMultiThreaded {
    public static Vector<String> nicks = new Vector<String>();
    
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Uso: java ServerTestoMultithreaded <Porta Server>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);
        try{
            ServerSocket server = new ServerSocket(portNumber);
            System.out.println("Server di Testo in esecuzione...  (CTRL-C quits)\n");

            while(true){
                SocketWorker w;
                try {
                    //server.accept returns a client connection
                    w = new SocketWorker(server.accept());
                    Thread t = new Thread(w);
                    t.start();
                } catch (IOException e) {
                    System.out.println("Connessione NON riuscita con il client: ");
                    System.exit(-1);
                }
                
            }
        } catch (IOException e) {
            System.out.println("Errore! Porta: " + portNumber + " non disponibile");
            System.exit(-1);
        }

        
    }
}

