package it.polimi.ingsw.network.client;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.UsernameModificationEvent;
import it.polimi.ingsw.event.model_view_event.AmmoTileUpdateEvent;
import it.polimi.ingsw.event.model_view_event.PlayerPowerUpUpdateEvent;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.cli.graph.*;
import it.polimi.ingsw.view.gui.GUI;


import java.util.*;
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
        CLI currentView = (CLI)remoteViewImplementation;
        CLIPlayerBoard testPlayerboard = new CLIPlayerBoard("user", Character.BANSHEE, currentView.getMapCharacterNameColors());
        testPlayerboard.markDamageUpdate(1,2,Character.SPROG);
        testPlayerboard.markDamageUpdate(2,0,Character.D_STRUCT_OR);
        /*
        String[] coloredPowerUp = {Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_RED.escape()+"Teleporter",
                Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_RED.escape()+"Newton"};
        testPlayerboard.gadgetsUpdate('P', coloredPowerUp);
        CubeColour[] colours = {CubeColour.Red,CubeColour.Yellow};
        */
        CLIMap map = new CLIMap(2);
        String[] weapon = {"sparo","oo",};
        CLIGameTrack gametrack = new CLIGameTrack();
        CLIDisplay display = new CLIDisplay();
        CLIPlayerBoard player1 = new CLIPlayerBoard("raul",Character.D_STRUCT_OR,currentView.getMapCharacterNameColors());
        CLIPlayerBoard player2 = new CLIPlayerBoard("lu",Character.DOZER,currentView.getMapCharacterNameColors());
        CLIPlayerBoard player3 = new CLIPlayerBoard("fede",Character.SPROG,currentView.getMapCharacterNameColors());
        CLIPlayerBoard player4 = new CLIPlayerBoard("fra",Character.VIOLET,currentView.getMapCharacterNameColors());

        gametrack.createGameTrack();
        display.setGameTrack(gametrack);
        display.setMap(map);
        display.setPlayerBoard(testPlayerboard);
        display.setPlayerBoard(player1);
        display.setPlayerBoard(player2);
        display.setPlayerBoard(player3);
        display.setPlayerBoard(player4);

        display.createDisplay();
        display.weaponsSpawnSquare(0,1,weapon);
        display.weaponsSpawnSquare(2,0,weapon);
        display.weaponsSpawnSquare(3,2,weapon);
        display.printDisplay();
        //remoteViewImplementation.playerPowerUpUpdate(Character.BANSHEE, powerUp,colours);
      //  testPlayerboard.printPlayerBoard();
    /*    remoteViewImplementation.gameChoice();
        remoteViewImplementation.printScreen();
        System.out.println();
        System.out.flush();
        remoteViewImplementation.addAmmoTileUpdate(3,2,"RED", "RED", "RED");
        remoteViewImplementation.addAmmoTileUpdate(1,2,"YELLOW", "BLUE", "POWERUP");
        remoteViewImplementation.printScreen();
        remoteViewImplementation.positionUpdate(Character.BANSHEE,3,2);
        remoteViewImplementation.positionUpdate(Character.SPROG,3,2);
        remoteViewImplementation.positionUpdate(Character.D_STRUCT_OR,3,2);
        remoteViewImplementation.positionUpdate(Character.DOZER,3,2);
        remoteViewImplementation.positionUpdate(Character.VIOLET,0,0);
        remoteViewImplementation.positionUpdate(Character.VIOLET,2,2);
        remoteViewImplementation.printScreen();
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
                    NetConfiguration.RMISERVERPORTNUMBER + new Random().nextInt(2000), serverIPAddress);

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
