/**
 *
 * @author Prof. Matteo Palitto
 * @author Jianu Alina
 * @author Ragusa Daniel
 * @author Stropiana Alessandro
 */
class SocketWorker implements Runnable {
    private Socket client;
    String nickname; //dichiaro la variabile di tipo String

    
    //Constructor: inizializza le variabili
    SocketWorker(Socket client) {
        this.client = client;
        System.out.println("Connessione stabilita con: " + client); //
    }
    
    // Questa e' la funzione che viene lanciata quando il nuovo "Thread" viene lanciato
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
            // Codice che permette di stampare la lista degli uteni connessi, con il commando "ListaUtenti"
            String line = "";
            int clientPort = client.getPort(); //il "nome" del mittente
            nickname = in.readLine();
            ServerTestoMultiThreaded.nicks.add(nickname);
            while(line != null){
                try{
                    line = in.readLine();
                    //Verifico se il comando scritto è uguale a "ListaUtenti"
                    if(line.equals("ListaUtenti")){
                        for(int i = 0; i < ServerTestoMultiThreaded.nicks.size(); i++){
                            System.out.println((String) ServerTestoMultiThreaded.nicks.get(i));
                           
                        }
                    }
                    else{   
                        //Manda lo stesso messaggio appena ricevuto con in aggiunta il "nome" del client
                        out.println(nickname + ">> " + line);
                        //scrivi messaggio ricevuto sul terminale
                        System.out.println(nickname + ">> " + line);
                    }
                } catch (IOException e) {
                    System.out.println("lettura fallita");
                    System.exit(-1);
                }
                
            }
            try {
                client.close();
                System.out.println("connessione con client: " + client + " terminata!");
            } catch (IOException e) {
                System.out.println("Errore connessione con client: " + client);
            }
        } catch (IOException ex) {
          Logger.getLogger(SocketWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
