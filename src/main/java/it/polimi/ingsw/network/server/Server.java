package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.network.NetConfiguration;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.view.ViewInterface;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;


public class Server {
    public static Logger log = Logger.getLogger("ServerLogger");

    public static void main(String[] args){
        ViewInterface virtualView;
        RMIServer serverRMI;
        SocketServer serverSocket;
        Map<String,ServerInterface> mapUserServer = new HashMap<>();
        Event message;
        try{
            serverRMI = new RMIServer();
            serverSocket = new SocketServer();
            serverSocket.start();
            Thread rmiThread = new Thread(serverRMI);
            rmiThread.start();
            log.info("Server ready to accept clients");
            boolean gameCouldStart = false;
            boolean shutDown = false;
            ArrayList<String> clientList = new ArrayList<>();

            while(!shutDown){
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                boolean setUpComplete = false;

                //Aggiorna il numero di client connessi e li mappa verso il server giusto
                while(!gameCouldStart){
                    if(clientList.size() != serverSocket.getClientList().size() +
                            (serverRMI.getClientList()).size() ){
                        for (String currUser: serverSocket.getClientList()
                             ) { if(!clientList.contains(currUser)){
                                    clientList.add(currUser);
                                    mapUserServer.put(currUser,serverSocket);
                                }

                        }
                        for (String currUser: serverRMI.getClientList()
                             ) {
                            if(!clientList.contains(currUser)){
                                clientList.add(currUser);
                                mapUserServer.put(currUser,serverRMI);
                            }
                        }
                    }
                    if(clientList.size()==1&&!setUpComplete){
                        String firstUser = clientList.get(0);
                        ServerInterface currServer = mapUserServer.get(firstUser);
                        currServer.sendMessage(new GameChoiceEvent(firstUser,0,0));
                        message = currServer.listenMessage();
                        setUpComplete = true;
                        log.info( "Listened message from:\t" + message.getUser());

                    }
                    //todo parte subito dopo la terza connessione
                    if(clientList.size() > 2) {
                        serverRMI.gameCouldStart();
                        serverSocket.gameCouldStart();
                        gameCouldStart = true;
                        log.info("Game could start;\t\tthere are "+clientList.size()+" players");
                    }

                }
                //todo ora si richiederanno personaggi ecc, legge da input se richiesto QUIT però non terminano i client
                String inputCommand = reader.readLine();
                if(inputCommand.equalsIgnoreCase("QUIT"))
                {
                    shutDown=true;
                }
            }


            serverRMI.shutDown();
            serverSocket.shutDown();
            log.info("Server shutDown");
        }catch(IOException e){
            CustomLogger.logException(e);
        }
    }




}
