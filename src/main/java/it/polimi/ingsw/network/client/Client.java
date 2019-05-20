package it.polimi.ingsw.network.client;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.network.NetConfiguration;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;


import java.io.EOFException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Francesco Masciulli
 * It's the Client main class, it will start a connection to the server and handle the game
 * todo verrà richiesta, all'avvio, solo la scelta per l'interfaccia di gioco, il resto da view
 * todo interfaccia client può avere metodo disconnect client, connectClient è chiamato dal costruttore
 */
public class Client {

    private static Logger log = Logger.getLogger("ClientLogger");
    public static void main(String[] args) {

        RemoteView remoteViewImplementation = null;
        ClientInterface clientImplementation = null;
        String gameInterface = "";
        String user = "";
        String connectionType = "";
        String serverIPAddress = "";
        Event  currentMessage;
       //todo aggiustare i parametri, ora tutti da main, dopo solo gameInterface;
        try {
            gameInterface = args[0];
            /*user = args[1];
            connectionType = args[2];
            serverIPAddress = args[3];*/
        }catch(IndexOutOfBoundsException e){
            //Default: CLI, if no arguments are passed to main()
            CustomLogger.logException(e);
            gameInterface = "CLI";
        }

          if(gameInterface.equalsIgnoreCase("GUI")){
            remoteViewImplementation = new GUI();

          }
          else{
              remoteViewImplementation = new CLI();
          }
          String[] userInput = remoteViewImplementation.gameInit();
          user = userInput[0];
          connectionType = userInput[1];
          serverIPAddress = userInput[2];

        if (connectionType.equalsIgnoreCase(NetConfiguration.ConnectionType.RMI.name())) {
            try {
                clientImplementation = new RMIClient(user,
                        NetConfiguration.RMISERVERPORTNUMBER + new Random().nextInt(2000) + 1,
                                        serverIPAddress);
            } catch (RemoteException e) {
                CustomLogger.logException(e);
            }
        }
        else if (connectionType.equalsIgnoreCase(NetConfiguration.ConnectionType.SOCKET.name())) {
            clientImplementation = new SocketClient(user, serverIPAddress);
        }
        else return;

        boolean connected = true;

        //todo sempre connected finchè non si sconnette il server
        while(connected) {
            boolean waiting = true;
            while (waiting) {
                try {
                    currentMessage = clientImplementation.listenMessage();
                    log.info("Listened message for:\t" + currentMessage.getUser());
                    waiting = false;
                } catch (NullPointerException e) {

                    waiting = true;
                }
                catch (Exception e){
                    waiting = false;
                    connected = false;
                    CustomLogger.logException(e);
                    log.info("Server was disconnected!");
                }
            }
            clientImplementation.sendMessage(new GameChoiceEvent(user, 1, 1));
            log.info("Message sent to Server");
        }

        try {
            clientImplementation.disconnectClient();
        } catch (Exception e){
            CustomLogger.logException(e);
        }





    }


}
