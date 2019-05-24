package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.GameRequestEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.view.VirtualView;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class Server {
    private static Logger log = Logger.getLogger("ServerLogger");


    //forse copyonwriteArraylist
    private static ArrayList<VirtualView> virtualViewList = new ArrayList<>();
    private static RMIServer serverRMI;
    private static SocketServer serverSocket;
    private static Map<String,ServerInterface> mapUserServer = new HashMap<>();
    private static Map<String, VirtualView> mapUserView = new HashMap<>();
    private static Event message;


    public static void main(String[] args){
/*        ArrayList<VirtualView> virtualViewList = new ArrayList<VirtualView>();
        RMIServer serverRMI;
        SocketServer serverSocket;
        Map<String,ServerInterface> mapUserServer = new HashMap<>();
        Event message;
*/
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

                //Aggiorna il numero di client connessi e li mappa verso il server giusto, allocando una VirtualView
                while(!gameCouldStart){
                    if(clientList.size() != serverSocket.getClientList().size() +
                            (serverRMI.getClientList()).size() ){
                        int usernameNumber=0;
                        String connectedUser ="";
                        ArrayList<String> connectedList = serverSocket.getClientList();
                        for (String currUser: connectedList) {
                            usernameNumber = Collections.frequency(connectedList,currUser);
                            if(!clientList.contains(currUser)){
                                userAddAndMap(clientList,currUser+usernameNumber,serverRMI);
                                connectedUser = currUser;
                            }
                            else if(usernameNumber>1){
                                userAddAndMap(clientList,currUser+usernameNumber,serverSocket);
                                connectedUser = currUser;
                                //todo deve cambiare l'user in serverImplementation

                            }


                        }
                        connectedList = serverRMI.getClientList();
                        for (String currUser: connectedList) {
                            usernameNumber = Collections.frequency(connectedList,currUser);
                            if(!clientList.contains(currUser)){
                                userAddAndMap(clientList,currUser,serverRMI);
                                connectedUser = currUser;
                            }
                            else if(usernameNumber>1){
                                userAddAndMap(clientList,currUser+usernameNumber,serverRMI);
                                connectedUser = currUser;
                                //todo deve cambiare l'user in serverImplementation
                            }
                        }
                        VirtualView userView = new VirtualView(connectedUser);
                        virtualViewList.add(userView);
                        mapUserView.put(connectedUser,userView);
                    }
                    if(clientList.size()==1&&!setUpComplete){
                        String firstUser = clientList.get(0);
                        ServerInterface currServer = mapUserServer.get(firstUser);
                        currServer.sendMessage(new GameRequestEvent(firstUser));
                        message = currServer.listenMessage();
                        VirtualView currentView = mapUserView.get(message.getUser());
                        //todo controlla se message è null
                        currentView.toController(message);
                        setUpComplete = true;
                        log.info( "Listened message from:\t" + message.getUser());

                    }
                    //todo aggiungere timer parte 60 secondi dopo la terza connessione
                    if(clientList.size() > 2) {
                        serverRMI.gameCouldStart();
                        serverSocket.gameCouldStart();
                        gameCouldStart = true;
                        log.info("Game could start;\t\tthere are "+clientList.size()+" players");
                    }

                }
                //todo ora si richiederanno personaggi ecc, legge da input se richiesto QUIT però non terminano i client
                String inputCommand = reader.readLine();
                if(inputCommand.equalsIgnoreCase("QUIT")) {
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

    private static void userAddAndMap(ArrayList<String> clientList, String currUser, ServerInterface serverImplementation){
        clientList.add(currUser);
        mapUserServer.put(currUser,serverImplementation);
    }


}
