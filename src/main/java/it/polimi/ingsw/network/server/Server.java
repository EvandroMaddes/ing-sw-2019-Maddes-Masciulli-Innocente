package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.UsernameModificationEvent;
import it.polimi.ingsw.event.controller_view_event.DisconnectedEvent;
import it.polimi.ingsw.event.controller_view_event.GameRequestEvent;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.utils.CustomTimer;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.view.VirtualView;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class Server {
    private static Logger log = Logger.getLogger("ServerLogger");


    private static ArrayList<VirtualView> virtualViewList = new ArrayList<>();
    private static ArrayList<String> clientList = new ArrayList<>();
    private static ArrayList<String> disconnectedClient = new ArrayList<>();
    private static RMIServer serverRMI;
    private static SocketServer serverSocket;
    private static Map<String,ServerInterface> mapUserServer = new HashMap<>();
    private static Map<String, VirtualView> mapUserView = new HashMap<>();
    private static CustomTimer gameTimer;
    private static Event message;


    public static void main(String[] args){

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
                        if(!mapUserView.containsKey(connectedUser)){
                        VirtualView userView = new VirtualView(connectedUser);
                        virtualViewList.add(userView);
                        mapUserView.putIfAbsent(connectedUser,userView);

                        }
                    }
                    if(!clientList.isEmpty()&&!setUpComplete){
                        String firstUser = clientList.get(0);
                        ServerInterface currServer = mapUserServer.get(firstUser);
                        currServer.sendMessage(new GameRequestEvent(firstUser));
                        while(message == null) {
                            message = currServer.listenMessage();
                        }
                            VirtualView currentView = mapUserView.get(message.getUser());
                            currentView.toController(message);
                            setUpComplete = true;
                            log.info("Listened message from:\t" + message.getUser());
                            message = null;
                    }

                    //todo PROVA!!! Qui si prova due client connessi, NB ordine chiamate
                    //todo da provare client disconnesso dopo ping e devo inviare messaggio a lui
                    if(clientList.size()==2) {
                        message = null;
                        String currentClient = clientList.get(0);
                        ServerInterface server = mapUserServer.get(currentClient);
                        ArrayList<Event> disconnectedClients = ping();
                        if(disconnectedClients.isEmpty()) {
                            //todo log.info(sendMessage)
                            server.sendMessage(new GameRequestEvent(clientList.get(0)));
                            message = server.listenMessage();
                            if (message == null) {
                                message = new DisconnectedEvent(clientList.get(0));
                                disconnectClient(currentClient);
                            }
                            else {
                                mapUserView.get(message.getUser()).toController(message);
                                log.info("Listened message from:\t" + message.getUser());
                            }
                        }
                        else{

                            log.severe("Client disconnected in other player's turn");

                        }
                        if (clientList.size()>1){

                            disconnectedClients = ping();
                            if(disconnectedClients.isEmpty()) {
                                currentClient = clientList.get(1);
                                server = mapUserServer.get(currentClient);
                                server.sendMessage(new GameRequestEvent(currentClient));
                                message = server.listenMessage();
                                if(message==null){
                                    message= new DisconnectedEvent(currentClient);
                                    disconnectClient(currentClient);
                                }
                                mapUserView.get(message.getUser()).toController(message);
                                log.info("Listened message from:\t" + message.getUser());
                            }
                            else {

                                log.severe("Client disconnected in other player's turn");
                            }
                        }
                        gameCouldStart = true;
                    }


                    //todo FINE PROVA!!

                    //todo aggiungere parsing tempo da command line, ora da NetConfiguration.java
                    if(clientList.size() > 2) {
                        if(gameTimer==null){
                            gameTimer = new CustomTimer(NetConfiguration.STARTGAMETIMER);
                            gameTimer.start();
                            log.info("Started the match countdown!\n\nGame start in "
                                    + NetConfiguration.STARTGAMETIMER+" seconds.");
                        }
                        else if(!gameTimer.isAlive()) {
                            serverRMI.gameCouldStart();
                            serverSocket.gameCouldStart();
                            gameCouldStart = true;
                            log.info("Game could start; There are " + clientList.size() + " players");
                        }
                    }


                }

                //todo ora si richiederanno personaggi ecc, legge da input se richiesto QUIT però non terminano i client
                //todo chiama ping();
                //ping();
                log.info("Type Quit for server shutdown");
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

    /**
     * this method is called before the sendMessage, it update the clients that disconnected during the last round
     * @return the DisconnectedEvents from the disconnected clients
     */
    private static ArrayList<Event> ping(){
        ArrayList<Event> currentDisconnectedClients = serverSocket.ping();
        currentDisconnectedClients.addAll(serverRMI.ping());
       if(!currentDisconnectedClients.isEmpty()){
           //todo gestire qui il controller con disconnectClient()
           log.info(currentDisconnectedClients.size()+" disconnected clients in this turn!");
           for (Event currEvent: currentDisconnectedClients) {

                   disconnectClient(currEvent.getUser());
           }
       }
       return currentDisconnectedClients;
    }


    //todo deve notificare il controller!

    /**
     * Is called when the listenMessage() return null, exactly when its timer elapses, or by ping()
     *  handle the Kick out of the selected user
     * @param user is the user that must be kicked out
     */
    private static void disconnectClient(String user){
        clientList.remove(user);
        message = mapUserServer.get(user).disconnectClient(user);
        mapUserView.get(user).toController(message);
        log.info("Listened message from:\t" + message.getUser());
        mapUserServer.remove(user);
        log.severe("Client Disconnected:\t"+user);
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
