package it.polimi.ingsw.network.client;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.server_view_event.ServerClientEvent;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.gui.LoginMain;
import javafx.application.Application;


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
    private static LoginMain guiMain;
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

              LoginMain guiInterface = new LoginMain();
              // TODO: 2019-06-23 fra mi ha detto di commentarlo momentaneamente
              //guiInterface.run();
              remoteViewImplementation = guiInterface.getLoginController().getGui();



          }
          else{
              remoteViewImplementation = new CLI();
          }


        /**
         *!!!!
         * !!!!!PROVA
         * !!!
         */
    /*    boolean[] available=new boolean[3];
        available[0]=true;
        available[1]=true;
        available[2]=true;

        ArrayList<String> wait = new ArrayList<String>();
        wait.add("wait1");
        wait.add("wait2");
        wait.add("wait3");

        ArrayList<String> started = new ArrayList<String>();
        started.add("started1");
        started.add("started2");
        started.add("started3");

        ArrayList<String> name = new ArrayList<String>();
        name.add("name1");
        name.add("name2");
        name.add("name3");

        remoteViewImplementation.weaponGrabChoice(name);

        CLI currentView = (CLI)remoteViewImplementation;
        CLIPlayerBoard testPlayerboard = new CLIPlayerBoard("user", Character.BANSHEE, currentView.getMapCharacterNameColors());
        testPlayerboard.markDamageUpdate(1,2,Character.SPROG);
        testPlayerboard.markDamageUpdate(2,0,Character.D_STRUCT_OR);

        String[] coloredPowerUp = {Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_RED.escape()+"Teleporter",
                Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_BLUE.escape()+"Newton"};
        testPlayerboard.gadgetsUpdate('P', coloredPowerUp);
        CubeColour[] colours = {CubeColour.Red,CubeColour.Yellow};

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
        remoteViewImplementation.playerPowerUpUpdate(Character.BANSHEE, powerUp,colours);
        testPlayerboard.printPlayerBoard();
       remoteViewImplementation.gameChoice();
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


       boolean[] weap = {true,false,false};
       int[] x = {1,2,0};
        int[] y = {2,1,2};
       ArrayList<Character> lobby = new ArrayList<>();

       lobby.add(Character.VIOLET);

        lobby.add(Character.VIOLET);

        lobby.add(Character.VIOLET);

        CubeColour[] cubetest = new CubeColour[2];
        cubetest[0] = CubeColour.Red;
        cubetest[1] = CubeColour.Blue;
       currentView.weaponEffectPaymentChoice(coloredPowerUp,cubetest,x,y);
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
                    NetConfiguration.RMISERVERPORTNUMBER +1000 + new Random().nextInt(1000), serverIPAddress);

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
                    //todo i ModelUpdate, eseguendo performAction ritornano null?, non un Event;
                    currentMessage = ((ClientEvent)currentMessage).performAction(remoteViewImplementation);
                    //todo if currentMessage != null invia al server, dopo deve tornare in questo ciclo??
                    if(!currentMessage.getUser().equals("BROADCAST")){
                        clientImplementation.sendMessage(currentMessage);
                    }
                    //todo
                    if(remoteViewImplementation.isGameSet()){
                        remoteViewImplementation.printScreen();
                    }

                    waiting = false;
                } catch (NullPointerException e) {

                    waiting = true;
                }
                catch (ClassCastException e){
                    Event returnedEvent = ((ServerClientEvent)currentMessage).performAction(clientImplementation,remoteViewImplementation);
                    if(returnedEvent != null){
                        clientImplementation.sendMessage(returnedEvent);
                    }
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
