/*
 * ClientTesto.java e' il programma per connettersi ad un Server usando i socket
 * ed inviare il testo ricevuto dalla linea di comando.
 * Utilizza una "Thread" per l'ascolto dei messaggi provenienti dal Server.
 */

/**
 *
 * @author Professore Matteo Palitto
 * @author Jianu Alina
 * @author Ragusa Daniel
 * @author Stropiana Alessandro
 */

//librerie
import java.net.*; //sarebbe "java.net.Socket", ma siccome sono state incluse tutte le classi, si mette '*'
import java.io.*; //serve per la gestione dei files

public class ClientTesto {
    
    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
	// verifica correttezza dei parametri
	if (args.length != 2) 
	{
	    //messaggio di output
            System.out.println("Uso: java client-Testo <indirizzo IP Server> <Porta Server>");
            return;
        }
	// inserimento del nickname
	String nick = null;
        try{
	    //messaggio di output
            System.out.println("Inserisci il nickname");
	    //lettura del file tramite un buffer
            nick = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        } 
	//eccezione  
	catch(IOException e) 
	{ 
		//messaggio di output
		System.out.println("I/O Error");
                System.exit(-1); 
	}	
	
	//dichiarazione della stringa "hostName"
	String hostName = args[0];
	//parsificazione
	int portNumber = Integer.parseInt(args[1]);
	    
	try {
            //viene preso l'indirizzo IP del server dalla linea di comando
            InetAddress address = InetAddress.getByName(hostName);
			
            // creazione di uno socket 
            Socket clientSocket = new Socket(address, portNumber);
		
            //visualizzazione delle istruzioni
            System.out.println("Client-Testo: usa EXIT per terminare, ENTER per spedire la linea di testo.\n");
			
            //connessione concorrente al socket per ricevere i dati da Server
            listener l;
            try {
                l = new listener(clientSocket);
                Thread t = new Thread(l);
                t.start();
            } 
		
	    catch (Exception e) 
	    { 
		    System.out.println("Connessione NON riuscita con server: "); 
	    }
		
            // connessione al socket (in uscita client --> server)
            PrintWriter out =  new PrintWriter(clientSocket.getOutputStream(), true);
			
            // connessione allo StdIn per inserire il testo dalla linea di comando
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
	    String userInput;
            // il primo stream è rappresentato dal nickname
            out.println(nick);
		
            //lettura da linea di comando del testo da spedire al Server
            System.out.print(">"); //visualizza il prompt
            while ((userInput = stdIn.readLine()) != null) 
	    {
            	// scrittura del messaggio da spedire nel socket 
		out.println(userInput);
                System.out.println("Messaggio spedito al server: " + userInput);
                System.out.print(">"); //visualizza il prompt
            }
		
            // chiusura socket
            clientSocket.close();
            System.out.println("connessione terminata!");
	}
        catch (IOException e) 
	{ 
		System.out.println("Connessione terminata dal server: "); e.printStackTrace(); }
    	}
    
}
