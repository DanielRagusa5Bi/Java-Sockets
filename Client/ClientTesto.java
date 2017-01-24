/*
 * "ClientTesto.java" è il programma che serve per connettersi ad un Server, tramite
 * Sockets, ed inviare il testo ricevuto dalla linea di comando.
 */

/**
 * @author Professor Matteo Palitto
 * @author Jianu Alina
 * @author Ragusa Daniel
 * @author Stropiana Alessandro
 */

import java.net.*;//libreria per l' inclusione di tutte le classi di Socket
import java.io.*;//libreria per i files

public class ClientTesto {
    /**
     * @param args the command line arguments
     */
    
    //main
    public static void main(String[] args) {
        
	// verifica se i parametri sono corretti
	if (args.length != 2) {
            //visualizzazione messaggio di output
            System.out.println("Uso: java ClientTesto <indirizzo IP Server> <Porta Server>");
            return;
        }
	
        //dichiarazione della stringa "nickname" e assegnazione
	String nickname = null;
        
        try{
            //messaggio che indica di inserire il nickname
            System.out.println("Inserire il nickname");
            
            //lettura del file attraverso un Buffer
            nickname = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        } 
        
        catch(IOException e) 
        { 
            //visualizzazione messaggio di errore di Input o Output
            System.out.println("I/O Error");
            //quando si verifica un errore, è possibile utilizzare valori diversi, per diversi tipi di errore
            System.exit(-1); 
        }
	  
	String hostName = args[0];
        
        //parsificazione del numero di porta
	int portNumber = Integer.parseInt(args[1]);
        
	try {
            // viene preso l' indirizzo IP del server, della linea di comando
            InetAddress address = InetAddress.getByName(hostName); //"InetAddress" rappresenta un indirizzo IP e/o un nome di dominio. 
                                                                   //Inoltre usa un servizio DNS
			
            // creazione  di un Socket 
            Socket clientSocket = new Socket(address, portNumber);
		
            // visualizzazione delle indicazioni per poter finire o spedire 
            System.out.println("ClientTesto: Digitare Ctrl C ,per terminare la comunicazione, o Enter ,per spedire la linea di testo.\n");
			
            // connessione concorrente al Socket per ricevere i dati da Server
            listener l;
            try {
                //Al fine di ricevere i messaggi dal server viene utilizzato un 
                //Thread il quale viene eseguito in modo concorrente e quindi 
                //indipendente e separata dalla parte di codice del Client. 
                //Quest ultimo aspetta che l’utente inserisca il messaggio e lo inoltra al Server
                l = new listener(clientSocket);
                Thread t = new Thread(l); //creazione di un oggetto 
                t.start();
            } 
            
            catch (Exception e) 
            { 
                //messaggio che viene visualizzato in caso di connessione non riuscita
                System.out.println("Connessione con Server non riuscita"); 
            }
		
            // connessione al socket
            PrintWriter out =  new PrintWriter(clientSocket.getOutputStream(), true);
			
            //connessione al StdIn in modo da poter inserire il testo da linea di comando
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            
            //lettura del testo che deve essere spedito al Server
            System.out.print(">"); //visualizzazione del prompt dei comandi
            while ((userInput = stdIn.readLine()) != null) 
            {
            	// scrittura del messaggio che deve essere spedito nel Socket 
		out.println(userInput);
                System.out.println("Messaggio da spedire al Server: " + userInput);
                System.out.print(">"); //visualizzazione del prompt dei comandi
            }
            
            // chiusura del Socket
            clientSocket.close();
            
            //visualizzazione del messaggio che avvisa della fine della connessione (Client)
            System.out.println("Connessione terminata (Client)");
	}
        
        //eccezzione
        catch (IOException e) 
        { 
            //visualizzazione del messaggio che avvisa della fine della connessione (Server)
            System.out.println("Connessione terminata (Server): "); e.printStackTrace(); 
        }
    }
