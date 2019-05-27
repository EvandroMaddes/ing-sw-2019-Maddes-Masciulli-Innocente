package it.polimi.ingsw.network.client;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.UsernameModificationEvent;
import it.polimi.ingsw.event.controller_view_event.CharacterRequestEvent;
import it.polimi.ingsw.event.controller_view_event.GameRequestEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.model.player.Character;
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

        RemoteView remoteViewImplementation;
        ClientInterface clientImplementation = null;
        String gameInterface;
        String user;
        String connectionType;
        String serverIPAddress;
        Event  currentMessage = null;
        try {
            gameInterface = args[0];
        }catch(IndexOutOfBoundsException e){
            //Default: CLI, if no arguments are passed to main()
            gameInterface = "CLI";
        }

          if(gameInterface.equalsIgnoreCase("GUI")){
            remoteViewImplementation = new GUI();

          }
          else{
              remoteViewImplementation = new CLI();
          }


        /**
         *!!!!
         * !!!!!PROVA
         * !!!
         */
    /*    remoteViewImplementation.gameChoice();
        remoteViewImplementation.printScreen();
        System.out.println();
        System.out.flush();
        ArrayList<Character> availableTestedCharacter = new ArrayList<>();
        availableTestedCharacter.add(Character.SPROG);
        availableTestedCharacter.add(Character.BANSHEE);
        availableTestedCharacter.add(Character.D_STRUCT_OR);
        availableTestedCharacter.add(Character.DOZER);
        availableTestedCharacter.add(Character.VIOLET);
        ClientEvent testEvent = new CharacterRequestEvent(remoteViewImplementation.getUser(), availableTestedCharacter);
        Event returnedEvent = testEvent.performAction(remoteViewImplementation);

     */
        /**
         * !!!
         * !!!FINE PROVA
         * !!!
         */

        String[] userInput = remoteViewImplementation.gameInit();
        user = userInput[0];
        connectionType = userInput[1];
        serverIPAddress = userInput[2];



    try {
        if (connectionType.equalsIgnoreCase(NetConfiguration.ConnectionType.RMI.name())) {

            clientImplementation = new RMIClient(user,
                    NetConfiguration.RMISERVERPORTNUMBER + new Random().nextInt(2000) + 1, serverIPAddress);

        } else {
            clientImplementation = new SocketClient(user, serverIPAddress);
        }
    }
    catch(Exception e){
        log.warning("Can't reach the Server!!\n\nClosing the app..");
        CustomLogger.logException(e);
    }

        boolean connected = true;

        //todo sempre connected finchè non si sconnette il server
        while(connected) {
            boolean waiting = true;
            while (waiting) {
                try {
                    currentMessage = clientImplementation.listenMessage();
                    log.info("Listened message for:\t" + currentMessage.getUser());
                    //todo i ModelUpdate, eseguendo performAction ritornano null, non un Event;
                    currentMessage = ((ClientEvent)currentMessage).performAction(remoteViewImplementation);
                    //todo if currentMessage != null invia al server, dopo deve tornare in questol ciclo??
                    clientImplementation.sendMessage(currentMessage);
                    waiting = false;
                } catch (NullPointerException e) {

                    waiting = true;
                }
                catch (ClassCastException e){
                    ((UsernameModificationEvent)currentMessage).performAction(clientImplementation);
                    user = ((UsernameModificationEvent)currentMessage).getNewUser();
                    System.out.println("Username already connected, yours is now:\t"+user);
                    waiting = true;
                }
                catch (Exception e){
                    waiting = false;
                    connected = false;
                    CustomLogger.logException(e);
                    log.info("Server was disconnected!");
                }
            }

            log.info("Message sent to Server");
        }

        try {
            clientImplementation.disconnectClient();
        } catch (Exception e){
            CustomLogger.logException(e);
        }





    }


}
