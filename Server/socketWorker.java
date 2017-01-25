/*
 * socketWorker.java ha il compito di gestire la connessione al socket da parte di un Client.
 * Elabora il testo ricevuto che in questo caso viene semplicemente mandato indietro con l'aggiunta 
 * di una indicazione che e' il testo che viene dal Server.
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Prof. Matteo Palitto
 * @author Jianu Alina
 * @author Ragusa Daniel
 * @author Stropiana Alessandro
 */
class SocketWorker implements Runnable {
    private Socket client;
    String nickname; //dichiaro la variabile di tipo string

    
    //Construttore: inizializza le variabili
    SocketWorker(Socket client) {
        this.client = client;
        System.out.println("Connessione con: " + client);
    }
    
    // Questa è la funzione che viene creata quando il nuovo "Thread" si genera
    public void run(){
        
        try{
            
            BufferedReader in = null;
            PrintWriter out = null;
            try{
                // connessione con il socket per ricevere (input) e mandare(output) il testo
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Errore: input/output fallito");
                System.exit(-1);
            }
            // Codice che permette di stampare la lista degli uteni connessi
            String line = "";
            int clientPort = client.getPort(); //il "nome" del mittente
            nickname = in.readLine();
            ServerTestoMultiThreaded.nicks.add(nickname);
            while(line != null){
                try{
                    line = in.readLine();
                    if(line.equals("ListaUtenti")){
                        for(int i = 0; i < ServerTestoMultiThreaded.nicks.size(); i++){
                            System.out.println((String) ServerTestoMultiThreaded.nicks.get(i));
                           
                        }
                    }
                    else{   
                        //Manda lo stesso messaggio con in aggiunta il "nome" del client
                        out.println(nickname + ">> " + line);
                        //scrivi il messaggio ricevuto su terminale
                        System.out.println(nickname + ">> " + line);
                    }
                } catch (IOException e) {
                    System.out.println("lettura da socket fallito");
                    System.exit(-1);
                }
                
            }
            try {
                client.close();
                System.out.println("connessione con il client: " + client + " terminata!");
            } catch (IOException e) {
                System.out.println("Errore connessione con il client: " + client);
            }
        } catch (IOException ex) {
          Logger.getLogger(SocketWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
