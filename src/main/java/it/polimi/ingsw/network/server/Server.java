package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.server_view_event.ReconnectionRequestEvent;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.event.model_view_event.NewPlayerJoinedUpdateEvent;
import it.polimi.ingsw.event.view_controller_event.DisconnectedEvent;
import it.polimi.ingsw.event.controller_view_event.GameRequestEvent;
import it.polimi.ingsw.event.view_controller_event.UpdateChoiceEvent;
import it.polimi.ingsw.model.player.Character;
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
import java.util.logging.Logger;


public class Server extends WaitServer{
    private static Logger log = Logger.getLogger("ServerLogger");


    private static ArrayList<VirtualView> virtualViewList = new ArrayList<>();
    private static ArrayList<String> activeClientList = new ArrayList<>();
    private static ArrayList<String> disconnectedClientList = new ArrayList<>();
    private static RMIServer serverRMI;
    private static SocketServer serverSocket;
    private static Map<String,ServerInterface> mapUserServer = new HashMap<>();
    private static Map<String, VirtualView> mapUserView = new HashMap<>();
    private static CustomTimer gameTimer;
    private static Event message;
    private static boolean gameCouldStart = false;
    //todo cercare cambio turno != cambio di contesto
    // private static String lastRoundPlayer =

    public static void main(String[] args){

        try{
            serverRMI = new RMIServer();
            serverSocket = new SocketServer();
            serverSocket.start();
            Thread rmiThread = new Thread(serverRMI);
            rmiThread.start();
            log.info("Server ready to accept clients\n");

            boolean shutDown = false;
            boolean setUpComplete = false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


            while(!shutDown){

                //Aggiorna il numero di client connessi e li mappa verso il server giusto, allocando una VirtualView
                //Gestione dell'inizializzazione della partita
                while(!gameCouldStart){
                    checkNewClient();

                    if(!activeClientList.isEmpty()&&!setUpComplete){
                        String firstUser = activeClientList.get(0);
                        ServerInterface currServer = mapUserServer.get(firstUser);
                        currServer.sendMessage(new GameRequestEvent(firstUser));
                        log.info("Sending message to:\t"+firstUser+"\n");
                        while(message == null) {
                            message = currServer.listenMessage();
                        }
                            VirtualView currentView = mapUserView.get(message.getUser());
                            currentView.toController(message);
                            setUpComplete = true;
                            log.info("Listened message from:\t" + message.getUser()+"\n");
                            message = null;
                    }

                    //todo PROVA!!! Qui si prova due client connessi, NB ordine chiamate
                    if(activeClientList.size()==2) {
                        message = null;
                        String currentUser = activeClientList.get(0);

                         message = sendAndWaitNextMessage(new NewPlayerJoinedUpdateEvent("Giorgio", Character.BANSHEE));
                        if (message == null) {
                            message = new DisconnectedEvent(currentUser);
                            disconnectClient(currentUser);
                        }
                        if(!message.getUser().equals("BROADCAST")){
                                mapUserView.get(message.getUser()).toController(message);
                        }
                        else{
                            for (VirtualView currView: virtualViewList) {
                                currView.toController(message);
                            }
                        }
                        log.info("Listened message from:\t" + message.getUser()+"\n");

                        }

                    //todo FINE PROVA!!

                    //todo aggiungere parsing tempo da command line, ora da NetConfiguration.java
                    if(activeClientList.size() > 2) {
                        if(gameTimer==null){
                            gameTimer = new CustomTimer(NetConfiguration.STARTGAMETIMER);
                            gameTimer.start();
                            log.info("Started the match countdown!\n\nGame start in "
                                    + NetConfiguration.STARTGAMETIMER+" seconds.\n");
                        }
                        else if(!gameTimer.isAlive()) {
                            serverRMI.gameCouldStart();
                            serverSocket.gameCouldStart();
                            gameCouldStart = true;
                            log.info("Game could start; There are " + activeClientList.size() + " players\n");
                        }
                    }


                }



                //todo ora si gestisce il turno
                message = null;

                Event nextMessage = findNextMessage();

                //Update dei giocatori riconnessi, all'inizio di ogni turno di un giocatore
                checkNewClient();
                //Update dei giocatori disconnessi, all'inizio di ogni cambio di contesto
                //TODO chiama ping solo se currUser != vecchioUser
                ArrayList<Event> disconnectedClients = ping();

                //todo cercare il client giusto a cui spedire msg


                if(disconnectedClients.isEmpty()) {
                    String currentUser = nextMessage.getUser();
                    message = sendAndWaitNextMessage(nextMessage);

                    if (message == null) {
                        message = new DisconnectedEvent(currentUser);
                        disconnectClient(currentUser);
                    }
                    mapUserView.get(message.getUser()).toController(message);
                    log.info("Listened message from:\t" + message.getUser()+"\n");

                }
                else{

                    log.severe("Client disconnected in other player's turn"+"\n");


                }
                //todo deve aspettare il controller


                ////todo dopo ogni esec legge da input se richiesto QUIT però non terminano i client
                log.info("Type Quit for server shutdown\n");
                String inputCommand = reader.readLine();
                if(inputCommand.equalsIgnoreCase("QUIT")) {
                    shutDown=true;
                }

            }


            serverRMI.shutDown();
            serverSocket.shutDown();
            log.info("Server shutDown\n");
        }catch(IOException e){
            CustomLogger.logException(e);
        }
    }

    private static Event sendAndWaitNextMessage(Event toSend){
        String currentUser = toSend.getUser();
        Event returnedEvent = null;
        log.info("Sending message to:\t"+currentUser+"\n");
        if(toSend.getUser().equals("BROADCAST")){
            serverRMI.sendBroadcast(toSend);
            serverSocket.sendBroadcast(toSend);
            returnedEvent = new UpdateChoiceEvent("BROADCAST");
        }
        else{
            ServerInterface server = mapUserServer.get(currentUser);

            server.sendMessage(toSend);
            returnedEvent = server.listenMessage();
        }
        return returnedEvent;
    }

    private static void cleanVirtualViews(boolean isBroadcast){
        for (VirtualView currentView: virtualViewList) {
            if(isBroadcast){
                currentView.setToRemoteView(null);
            }
            else{
                if(currentView.getToRemoteView()!=null){
                    currentView.setToRemoteView(null);
                }
            }
        }
    }

    private static Event findNextMessage(){
        message = null;
        for (VirtualView currentView: virtualViewList) {
            Event currMessage = currentView.getToRemoteView();
            if(currMessage!=null){
                cleanVirtualViews(currMessage.getUser().equals("BROADCAST"));
                return currMessage;
            }
        }
        return null;
    }



    /**
     * This method, depending on gameCouldStart value, handle the incoming client connections
     */
    private static void checkNewClient(){
        if (activeClientList.size() != serverSocket.getClientList().size() + serverRMI.getClientList().size()) {
            if(!gameCouldStart) {
                String connectedUser = updateMixedActiveClientList();
                if (connectedUser.isEmpty()) {
                   connectedUser = updateActiveClientList();
                }
                if (!mapUserView.containsKey(connectedUser)) {
                    VirtualView userView = new VirtualView(connectedUser);
                    virtualViewList.add(userView);
                    mapUserView.putIfAbsent(connectedUser, userView);

                }
            }
            else{
                reconnectClient();
            }
        }

    }

    /**
     * It handle the clients reconnection during the game
     */
    private static void reconnectClient(){
        ArrayList<String> reconnectedClients = serverRMI.getClientList();
        reconnectedClients.addAll(serverSocket.getClientList());
        ServerInterface serverImplementation;
        for (String user : reconnectedClients) {
            if(reconnectedClients.indexOf(user)<serverRMI.getClientList().size()){
                serverImplementation = serverRMI;
            }
            else{
                serverImplementation = serverSocket;
            }
            if(!activeClientList.contains(user)){
                serverImplementation.sendMessage(new ReconnectionRequestEvent(user, disconnectedClientList));
                log.info("Sending message to:\t"+user+"\n");
                Event listenedMessage = serverImplementation.listenMessage();
                if(listenedMessage != null){
                    String connectedUsername = user;
                    user = listenedMessage.getUser();
                    serverImplementation.updateUsername(connectedUsername, user);
                    mapUserServer.put(user,serverImplementation);
                    mapUserView.get(listenedMessage.getUser()).toController(listenedMessage);
                    disconnectedClientList.remove(user);
                    activeClientList.add(user);
                    serverRMI.cleanDisconnectedEventList(user);
                    log.info("Reconnected client:\t"+user+"\n");

                }
                else{
                    serverImplementation.disconnectClient(user);
                }
            }
        }


    }

    /**
     * This method is called before the sendMessage, it update the clients that disconnected during the last round
     * @return the DisconnectedEvents from the disconnected clients
     */
    private static ArrayList<Event> ping(){
        ArrayList<Event> currentDisconnectedClients = serverSocket.ping();
        currentDisconnectedClients.addAll(serverRMI.ping());
       if(!currentDisconnectedClients.isEmpty()){
           log.info(currentDisconnectedClients.size()+" disconnected clients in this turn!\n");
           for (Event currEvent: currentDisconnectedClients) {

                   disconnectClient(currEvent.getUser());
           }
       }
       serverRMI.cleanDisconnectedEventList();
       return currentDisconnectedClients;
    }



    /**
     * Is called when the listenMessage() return null, exactly when its timer elapses, or by ping()
     *  handle the Kick out of the selected user calling the toController view method
     * @param user is the user that must be kicked out
     */
    private static void disconnectClient(String user){
        activeClientList.remove(user);
        disconnectedClientList.add(user);
        message = mapUserServer.get(user).disconnectClient(user);
        mapUserServer.remove(user);
        mapUserView.get(user).toController(message);
        log.info("Listened message from:\t" + message.getUser()+"\n");
        log.warning("Client Disconnected:\t"+user+"\n");
    }

    /**
     * Add and map the last connected user with his server implementation
     * @param currUser his the client username
     * @param serverImplementation is the server implementation depending on user's network preferences
     */
    private static void userAddAndMap( String currUser, ServerInterface serverImplementation){
        activeClientList.add(currUser);
        mapUserServer.put(currUser,serverImplementation);
    }

    /**
     * If there isn't any username conflict (when a client try to connect with an already token username),
     *  this method update
     * @return the last connected username
     */
    private static String updateActiveClientList() {
        String connectedUser = updateActiveClientList(serverRMI);
        if (connectedUser.isEmpty()) {
            connectedUser = updateActiveClientList(serverSocket);
        }
        return connectedUser;
    }

    /**
     * This method update the client list from the given server implementation
     * @param serverImplementation is the chosen implementation
     * @return the last connected user, or an empty string if there isn't
     */
    private static String updateActiveClientList(ServerInterface serverImplementation){
        String connectedUser ="";
        ArrayList<String> connectedList = serverImplementation.getClientList();

            for (String currUser: connectedList) {
                if (!activeClientList.contains(currUser)) {
                    connectedUser = currUser;
                    userAddAndMap(connectedUser, serverImplementation);
                    return  connectedUser;
                }
        }    return connectedUser;
    }

    /**
     * this method update the last connected client that use an already registered username,
     *   it calls method that change it in the server implementation and send the notification to the client
     * @return the new username
     */
    private static String updateMixedActiveClientList(){
        int usernameNumber;
        String connectedUser = "";
        ServerInterface serverImplementation;
        ArrayList<String> mixedList = new ArrayList<>(serverRMI.getClientList());
        mixedList.addAll(serverSocket.getClientList());
        for (String currUser: mixedList) {
            usernameNumber = Collections.frequency(mixedList, currUser);
            if(usernameNumber>1){
                int rmiUsers = Collections.frequency(serverRMI.getClientList(), currUser);
                connectedUser = currUser + usernameNumber + new Random().nextInt(100);
                if(mixedList.indexOf(currUser)<serverRMI.getClientList().size() &&
                        !(rmiUsers==1 && mapUserServer.get(currUser).equals(serverRMI)) ){
                    serverImplementation = serverRMI;
                }
                else{
                    serverImplementation = serverSocket;
                }
                userAddAndMap(connectedUser, serverImplementation);
                log.info("Sending message to:\t"+currUser+"\n");
                serverImplementation.sendMessage(new UsernameModificationEvent(currUser,connectedUser));
                serverImplementation.updateUsername(currUser,connectedUser);
                return connectedUser;
            }
        }
        return connectedUser;
    }


}
