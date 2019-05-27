package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.UsernameModificationEvent;
import it.polimi.ingsw.event.controller_view_event.GameRequestEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.view.VirtualView;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class Server {
    private static Logger log = Logger.getLogger("ServerLogger");


    //forse copyonwriteArraylist
    private static ArrayList<VirtualView> virtualViewList = new ArrayList<>();
    private static ArrayList<String> clientList = new ArrayList<>();
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
            boolean setUpComplete = false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


            while(!shutDown){

                //Aggiorna il numero di client connessi e li mappa verso il server giusto, allocando una VirtualView
                while(!gameCouldStart){
                    if(clientList.size() != serverSocket.getClientList().size() +
                            serverRMI.getClientList().size() ){

                        String connectedUser = updateMixedClientList();
                        if(connectedUser.isEmpty()){
                            connectedUser = updateClientList(serverRMI);
                            if(connectedUser.isEmpty()){
                                connectedUser = updateClientList(serverSocket);
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
                    while(message == null) {
                        message = currServer.listenMessage();
                    }
                            VirtualView currentView = mapUserView.get(message.getUser());
                            currentView.toController(message);
                            setUpComplete = true;
                            log.info("Listened message from:\t" + message.getUser());
                            message = null;
                    }

                    //todo PROVA!!! funge socket, provato RMI OK
                    if(clientList.size()==2) {
                        message = null;
                        serverSocket.sendMessage(new GameRequestEvent(clientList.get(0)));
                        while (message == null) {
                            message = serverSocket.listenMessage();
                        }
                        System.out.println(message.getUser());
                        message = null;
                        serverSocket.sendMessage(new GameRequestEvent(clientList.get(1)));
                        while (message == null) {
                            message = serverSocket.listenMessage();
                        }
                        System.out.println(message.getUser());
                    }
                    //todo FINE PROVA!!

                    //todo aggiungere timer parte ?60? secondi dopo la terza connessione
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

    private static void userAddAndMap( String currUser, ServerInterface serverImplementation){
        clientList.add(currUser);
        mapUserServer.put(currUser,serverImplementation);
    }
    private static String updateClientList(ServerInterface serverImplementation){
        //int usernameNumber;
        String connectedUser ="";
        ArrayList<String> connectedList = serverImplementation.getClientList();

            for (String currUser: connectedList) {
                //usernameNumber = Collections.frequency(connectedList, currUser);
                if (!clientList.contains(currUser)) {
                    connectedUser = currUser;
                    userAddAndMap(connectedUser, serverImplementation);
                    return  connectedUser;
                } /* else if (usernameNumber > 1) {
                    connectedUser = currUser + (usernameNumber + new Random().nextInt(100));
                    userAddAndMap(connectedUser , serverImplementation);
                    serverImplementation.sendMessage(new UsernameModificationEvent(currUser, connectedUser));
                    serverImplementation.updateUsername(currUser, connectedUser);
                    return connectedUser;
                }*/

        }    return connectedUser;
    }
//todo tra RMI e socket, sostituisce l'utente in RMI, risolvibile con int orderConnection nei client..
    private static String updateMixedClientList(){
        int usernameNumber;
        String connectedUser = "";
        ServerInterface serverImplementation;
        ArrayList<String> mixedList = new ArrayList<>(serverRMI.getClientList());
        mixedList.addAll(serverSocket.getClientList());
        for (String currUser: mixedList) {
            usernameNumber = Collections.frequency(mixedList, currUser);
            if(usernameNumber>1){
                connectedUser = currUser + usernameNumber + new Random().nextInt(100);
                if(mixedList.indexOf(currUser)<serverRMI.getClientList().size()){
                    serverImplementation = serverRMI;
                }
                else{
                    serverImplementation = serverSocket;
                }
                userAddAndMap(connectedUser, serverImplementation);
                serverImplementation.sendMessage(new UsernameModificationEvent(currUser,connectedUser));
                serverImplementation.updateUsername(currUser,connectedUser);
                return connectedUser;
            }
        }
        return connectedUser;
    }


}
