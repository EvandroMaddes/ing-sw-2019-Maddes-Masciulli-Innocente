
_____/\\\\\\\_____/\\\\\\\\\\______/\\\\\\\______/\\\\\\\\\\\\\__/\\\\\_____/\\\_____/\\\\\\\_____/\\\_________/\\\\\\\\\__/\\\\\_____/\\\_/\\\\\\\\\\\\\_
 ___/\\\\\\\\\\\__\/\\\//////\\\___/\\\/////\\\___\/\\\/////////__\/\\\\\\___\/\\\___/\\\\\\\\\\\__\/\\\________\////\\\//__\/\\\\\\___\/\\\_\/\\\/////////_
  __/\\\///////\\\_\/\\\____\//\\\_\/\\\___\/\\\___\/\\\___________\/\\\/\\\__\/\\\__/\\\///////\\\_\/\\\___________\/\\\____\/\\\/\\\__\/\\\_\/\\\__________
   _\/\\\_____\/\\\_\/\\\_____\/\\\_\/\\\\\\\\\/____\/\\\\\\\\\_____\/\\\//\\\_\/\\\_\/\\\_____\/\\\_\/\\\___________\/\\\____\/\\\//\\\_\/\\\_\/\\\\\\\\\____
    _\/\\\\\\\\\\\\\_\/\\\_____\/\\\_\/\\\////\\\____\/\\\/////______\/\\\\//\\\\/\\\_\/\\\\\\\\\\\\\_\/\\\___________\/\\\____\/\\\\//\\\\/\\\_\/\\\/////_____
     _\/\\\///////\\\_\/\\\_____\/\\\_\/\\\__\//\\\___\/\\\___________\/\\\_\//\\\/\\\_\/\\\///////\\\_\/\\\___________\/\\\____\/\\\_\//\\\/\\\_\/\\\__________
      _\/\\\_____\/\\\_\/\\\_____/\\\__\/\\\___\//\\\__\/\\\___________\/\\\__\//\\\\\\_\/\\\_____\/\\\_\/\\\___________\/\\\____\/\\\__\//\\\\\\_\/\\\__________
       _\/\\\_____\/\\\_\/\\\\\\\\\\/___\/\\\____\//\\\_\/\\\\\\\\\\\\\_\/\\\___\//\\\\\_\/\\\_____\/\\\_\/\\\\\\\\\\__/\\\\\\\\\_\/\\\___\//\\\\\_\/\\\\\\\\\\\\\
        _\///______\///__\//////////____ \///______\///__\/////////////__\///_____\/////__\///______\///__\//////////__\/////////__\///_____\/////__\/////////////_

ADRENALINE: THE GAME
Progetto di ingegneria del software, Politecnico di Milano A.A. 2018-2019


Esecuzione del programma:
    
   * Per avviare il Server, aprire il terminale; chiamare dalla directory in cui risiede il file Server.jar (Deliveries/final/JAR/ServerJAR) il seguente comando per iniziare l'esecuzione:
       
            java -jar Server.jar 
            
            
            //in fase di inizializzazione del Server verrà richiesto, prima di poter gestire le connessioni in entrata, di configurare i parametri dei timer (in secondi) di attesa per l'inizio della partita e dopo ogni invio di una richiesta ai client;
            //per terminare l'esecuzione del Server è necessario inserire sul terminale il comando "quit" 
  
    
   
    
   * Per avviare un'istanza del gioco aprire il terminale; chiamare dalla directory in cui risiede il file Client.jar il comando:
     
     
        java -jar Client.jar [interface]
        
        
        //[interface] = "CLI" or "GUI"; default (no argument given) = "CLI"
        //si ipotizza che al momento della connessione si conosca l'indirizzo ip del server
        
    
    
Funzionalità implementate:
    
   * le funzionalità implementate in questo progettono seguono quelle indicate nel documento de requisiti;
    	- è implementato il set di regole complete ("[..] tutte le modalità dellze armi; sono presenti la “frenesia finale” e le “azioni adrenaliniche”."); 
   	- insieme ai requisiti indicati nella sezione 2.2 del suddetto documento, è stata implementata la funzionalità avanzata che gestisce multiple partite in contemporanea sul Server, allocando una Lobby per ogni match creato;
   	- le tecnologie di connessione usate sono, a scelta indistinta di ogni client, sia RMI che Socket; 
    	- l'utente potrà scegliere se utilizzare l' interfaccia grafica (GUI) o da console (CLI)
    
    
     
Tools e librerie esterne: 
il progetto è stato sviluppato con l'ausilio dell'IDE IntelliJ V.2019.1.3; con quest'ultima è stato generato i file .jar da eseguire. Durante il corso di tutta l'implementazione sono stati usati i tools Sonar e Maven, oltre che SceneBuilder fornito da Oracle come ausilio allo sviluppo dell'interfaccia grafica;  
inoltre sono state utilizzate le seguenti librerie esterne:
    * junit per il test d'unità, 
    * gson per l'utilizzo di file JSON come risorse del Model
    * JavaFX per lo sviluppo della GUI


-------------------------------------------------------
Federico Innocente, Evandro Maddes, Francesco Masciulli
