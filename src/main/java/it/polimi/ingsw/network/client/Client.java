package it.polimi.ingsw.network.client;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.CharacterRequestEvent;
import it.polimi.ingsw.event.server_view_event.ServerClientEvent;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
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
import it.polimi.ingsw.view.gui.LoginMain;
import javafx.application.Application;
import javafx.application.Platform;


import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Francesco Masciulli
 * It's the Client main class, it will start a connection to the server and handle the game
 * todo verrà richiesta, all'avvio, solo la scelta per l'interfaccia di gioco, il resto da view
 * todo interfaccia network client deve avere metodo disconnect client, connectClient è chiamato dal costruttore
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

              //todo spostata prova della gui solo se interfaccia è gui; dovrebbe andare, remoteViewImpl ha user giusto
              Application.launch(LoginMain.class);
              remoteViewImplementation= LoginMain.getGui();


          }
          else{
              remoteViewImplementation = new CLI();
          }


        /**
         *!!!!
         * !!!!!PROVA
         * !!!
         */
        /*
       boolean[] available=new boolean[3];
        available[0]=true;
        available[1]=false;
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

        String[] powerUp = {"Teleporter","Newton"};

        CubeColour[] colours = {CubeColour.Red,CubeColour.Yellow};
        int[] min = {1,1,2};
        int[] max = {3,3,3};
        CLI currentView = (CLI)remoteViewImplementation;
        currentView.weaponGrabPaymentChoice(powerUp,colours,min, max);

        /*        CLIDisplay display = currentView.getDisplay();

        CLIPlayerBoard testPlayerboard = new CLIPlayerBoard("user", Character.BANSHEE, currentView.getMapCharacterNameColors());
        display.setPlayerBoard(testPlayerboard);
        CLIPlayerBoard player3 = new CLIPlayerBoard("fede",Character.SPROG,((CLI)remoteViewImplementation).getMapCharacterNameColors());
        display.setPlayerBoard(player3);
        CLIPlayerBoard player1 = new CLIPlayerBoard("raul",Character.D_STRUCT_OR,((CLI) remoteViewImplementation).getMapCharacterNameColors());
        display.setPlayerBoard(player1);
        CLIPlayerBoard player2 = new CLIPlayerBoard("lu",Character.DOZER,((CLI)remoteViewImplementation).getMapCharacterNameColors());
        display.setPlayerBoard(player2);
        CLIPlayerBoard player4 = new CLIPlayerBoard("fra",Character.VIOLET,((CLI)remoteViewImplementation).getMapCharacterNameColors());
        display.setPlayerBoard(player4);
        CLIGameTrack gametrack = new CLIGameTrack();
        gametrack.createGameTrack();
        display.setGameTrack(gametrack);
        CLIMap map = new CLIMap(2);
        display.setMap(map);

        String[] weapon = {"sparo","oo",};
        String[] coloredPowerUp = {Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_RED.escape()+"Teleporter",
                Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_BLUE.escape()+"Newton"};
        CubeColour[] colours = {CubeColour.Red,CubeColour.Yellow};


        display.createDisplay();
        currentView.weaponReplaceUpdate(0,1,coloredPowerUp);

        currentView.positionUpdate(Character.BANSHEE,0,1);

        Character[] marks = new Character[]{Character.VIOLET,Character.D_STRUCT_OR,Character.VIOLET};
        Character[] damage = new Character[]{Character.VIOLET,Character.D_STRUCT_OR,Character.SPROG};

        currentView.playerBoardUpdate(testPlayerboard.getCharacter(),2,marks,damage);
      //  currentView.printScreen();
        currentView.gameTrackSkullUpdate(marks,new int[]{1,1,2});

        currentView.positionUpdate(Character.BANSHEE,1,2);
        currentView.positionUpdate(Character.SPROG,2,2);
        currentView.positionUpdate(Character.D_STRUCT_OR,3,2);
        currentView.positionUpdate(Character.DOZER,0,1);
        currentView.positionUpdate(Character.VIOLET,0,0);
        currentView.positionUpdate(Character.VIOLET,2,2);

        currentView.getDisplay().getPlayerBoard(Character.BANSHEE).gadgetsUpdate('W',weapon);
        currentView.getDisplay().getPlayerBoard(Character.BANSHEE).gadgetsUpdate('P', coloredPowerUp);

        currentView.printScreen();
        System.out.println();
        System.out.flush();


        /**
         * !!!
         * !!!FINE PROVA
         * !!!
         */

        String[] userInput = remoteViewImplementation.gameInit();
        user = userInput[0];
        connectionType = userInput[1];
        serverIPAddress = userInput[2];


        boolean connected = false;
    try {
        if (connectionType.equalsIgnoreCase(NetConfiguration.ConnectionType.RMI.name())) {

            clientImplementation = new RMIClient(user,
                    NetConfiguration.RMISERVERPORTNUMBER +1000 + new Random().nextInt(1000), serverIPAddress);

        } else {
            clientImplementation = new SocketClient(user, serverIPAddress);
        }
        connected = true;
    }
    catch(ConnectException| RemoteException e){
        log.warning("Can't reach the Lobby!\nClosing the app..");
        CustomLogger.logException(e);

    }



        //todo sempre connected finchè non si sconnette il server
        while(connected) {
            boolean waiting = clientImplementation.isConnected();

            while (waiting) {
                try {
                    currentMessage = clientImplementation.listenMessage();
                    log.info("Listened message for:\t" + currentMessage.getUser());
                    if(remoteViewImplementation.isGameSet()&&!currentMessage.getUser().equals("BROADCAST")){
                        remoteViewImplementation.printScreen();
                    }
                    //todo i ModelUpdate, eseguendo performAction ritornano null?, non un Event;
                    currentMessage = ((ClientEvent)currentMessage).performAction(remoteViewImplementation);
                    //invia il messaggio solo se non è Update -> BROADCAST
                    if(!currentMessage.getUser().equals("BROADCAST")){
                        clientImplementation.sendMessage(currentMessage);
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
                    log.info("Lobby was disconnected!");
                }
                connected = clientImplementation.isConnected();
            }
        }

        try {
            clientImplementation.disconnectClient();
        }catch (NullPointerException nullPointer){
            log.warning("Client implementation doesn't exist, nothing to disconnect..");
        }
        catch (Exception closingException){
            log.warning("Can't close correctly the client connection!");
            CustomLogger.logException(closingException);
        }
        finally {
            log.info("Please, try again restarting the game.");
        }

    }



}
