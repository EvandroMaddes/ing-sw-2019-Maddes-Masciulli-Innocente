package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.server_view_event.LobbySettingsEvent;
import it.polimi.ingsw.event.server_view_event.ReconnectionRequestEvent;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.event.model_view_event.NewPlayerJoinedUpdateEvent;
import it.polimi.ingsw.event.view_controller_event.DisconnectedEvent;
import it.polimi.ingsw.event.controller_view_event.GameRequestEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.UpdateChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;
import it.polimi.ingsw.model.GameModel;
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

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Logger;


public class Server extends Thread {
    private  Logger log = Logger.getLogger("ServerLogger");

    private String lobbyName;
    private Controller lobbyController;
    private  ArrayList<VirtualView> virtualViewList = new ArrayList<>();
    private  ArrayList<String> activeClientList = new ArrayList<>();
    private  ArrayList<String> disconnectedClientList = new ArrayList<>();
    private  RMIServer serverRMI;
    private int portRMI;
    private  SocketServer serverSocket;
    private int portSocket;
    private  Map<String,ServerInterface> mapUserServer = new HashMap<>();
    private  Map<String, VirtualView> mapUserView = new HashMap<>();
    private  CustomTimer gameTimer;
    private  Event message;
    private  boolean gameCouldStart = false;
    int mapChoice = 404;
    //todo cercare cambio turno != cambio di contesto
    // private  String lastRoundPlayer =


    public int getPortRMI() {
        return portRMI;
    }

    public int getPortSocket() {
        return portSocket;
    }

    public ArrayList<String> getDisconnectedClientList() {
        return disconnectedClientList;
    }

    public void setLobby(String lobbyName, int aliveLobbies){
        this.lobbyName = lobbyName+"'s lobby";
        portRMI = NetConfiguration.RMISERVERPORTNUMBER+6*aliveLobbies+1;
        portSocket = NetConfiguration.SOCKETSERVERPORTNUMBER + aliveLobbies + 1;
        try {
            serverRMI = new RMIServer(portRMI);
            serverSocket = new SocketServer();
            serverSocket.setServerPort(portSocket);
        }catch (RemoteException exc){
            CustomLogger.logException(exc);
        }
    }


    public String getLobbyName() {
        return lobbyName;
    }




    @Override
    public void run() {

            /*serverRMI = new RMIServer(portRMI);
            serverSocket = new SocketServer();
            serverSocket.setServerPort(portSocket);
            serverSocket.start();
            Thread rmiThread = new Thread(serverRMI);
            rmiThread.start();
*/
            serverSocket.start();
            Thread rmiThread = new Thread(serverRMI);
            rmiThread.start();
            log.info(lobbyName.concat(":\tReady to accept clients\n"));

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
                        log.info(lobbyName.concat(":\tSending message to:\t"+firstUser+"\n"));
                        while(message == null) {
                            message = currServer.listenMessage();
                        }
                        mapChoice = ((GameChoiceEvent)message).getMap();
                        setUpComplete = true;
                        log.info(lobbyName.concat("\tListened message from:\t" + message.getUser()+"\n"));
                        message = null;
                    }


                    //todo aggiungere parsing tempo da command line, ora da NetConfiguration.java
                    if(activeClientList.size() > 2) {
                        if(gameTimer==null){
                            gameTimer = new CustomTimer(NetConfiguration.STARTGAMETIMER);
                            gameTimer.start();
                            log.info(lobbyName.concat(":\tStarted the match countdown!\n\nGame start in "
                                    + NetConfiguration.STARTGAMETIMER+" seconds.\n"));
                        }
                        else if(!gameTimer.isAlive()) {
                            serverRMI.gameCouldStart();
                            serverSocket.gameCouldStart();
                            gameCouldStart = true;
                            //todo istanzia controller e inizia computazione
                            lobbyController = new Controller(mapUserView, mapChoice);
                            GameModel model = lobbyController.getGameManager().getModel();
                            for (VirtualView connectedPlayer: virtualViewList) {
                                connectedPlayer.addObserver(lobbyController);
                                model.addObserver(connectedPlayer);
                            }
                            log.info(lobbyName.concat(":\tGame could start; There are " + activeClientList.size() + " players\n"));
                        }
                    }


                }




                //todo PROVA: ci entra sempre se la partita può iniziare

                /*        nextMessage = new NewPlayerJoinedUpdateEvent("TESTING PLAYER RECONNECTION\n", Character.SPROG);
                        log.info("Testing player reconnection:");
*/

                //todo FINEPROVA


                //todo ora si gestisce il turno, il controller setta nextMessage
                message = null;
                Event nextMessage = findNextMessage();
                //Update dei giocatori riconnessi, all'inizio di ogni turno di un giocatore
                checkNewClient();
                //Update dei giocatori disconnessi, all'inizio di ogni cambio di contesto
                //TODO chiama ping solo se currUser != vecchioUser
                ArrayList<Event> disconnectedClients = ping();


                if(disconnectedClients.isEmpty()) {
                    String currentUser = nextMessage.getUser();
                    message = sendAndWaitNextMessage(nextMessage);
                    if (message == null) {
                        message = new DisconnectedEvent(currentUser);
                        disconnectClient(currentUser);
                    }
                    if (!message.getUser().equals("BROADCAST")) {
                        mapUserView.get(message.getUser()).toController(message);
                        log.info(lobbyName.concat(":\tListened message from:\t" + message.getUser() + "\n"));
                    }

                    //lobbyController.update(mapUserView.get(message.getUser()), message);
                }
                else {

                        log.severe("Client disconnected in other player's turn" + "\n");


                    }




                //todo controllo se gioco terminato || dopo WinnerEvent??
                shutDown=!gameCouldStart;
            }

            serverRMI.shutDown();
            serverSocket.shutDown();
            log.info(lobbyName.concat("\t: ShutDown\n"));

    }


    public  boolean isGameCouldStart() {
        return gameCouldStart;
    }

    private Event sendAndWaitNextMessage(Event toSend){
        String currentUser = toSend.getUser();
        Event returnedEvent = null;
        log.info(lobbyName.concat(":\tSending message to:\t"+currentUser+"\n"));
        if(toSend.getUser().equals("BROADCAST")){
            serverRMI.sendBroadcast(toSend);
            serverSocket.sendBroadcast(toSend);
            try{
                sleep(1000);
            }catch (InterruptedException e){
                CustomLogger.logException(e);
            }
            returnedEvent = new UpdateChoiceEvent("BROADCAST");

        }
        else{
            ServerInterface server = mapUserServer.get(currentUser);

            server.sendMessage(toSend);
            returnedEvent = server.listenMessage();
        }
        return returnedEvent;
    }

    private  void cleanVirtualViews(boolean isBroadcast, Event toRemoveMessage){
        for (VirtualView currentView: virtualViewList) {
            if(isBroadcast){
                currentView.getModelUpdateQueue().remove(toRemoveMessage);
            }
            else {
                if(currentView.getToRemoteView()!=null){
                    currentView.setToRemoteView(null);
                    return;
                }
            }
        }
    }

    private Event findNextMessage(){
        message = null;

        Event currMessage = virtualViewList.get(0).getModelUpdateQueue().poll();
        if(currMessage!=null){
            cleanVirtualViews(true, currMessage);
            return currMessage;
        }
        else{
            for (VirtualView currentView: virtualViewList) {
                currMessage = currentView.getToRemoteView();
                if(currMessage!=null){
                    cleanVirtualViews(false, currMessage);
                    return currMessage;
                }
            }
        }
        return null;
    }



    /**
     * This method, depending on gameCouldStart value, handle the incoming client connections
     */
    private void checkNewClient(){
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
                if(mapChoice!=404) {
                    sendAndWaitNextMessage(new LobbySettingsEvent(connectedUser, mapChoice));
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
    private  void reconnectClient(){
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
                log.info(lobbyName.concat(":\tSending message to:\t"+user+"\n"));
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
                    log.info(lobbyName.concat(":\tReconnected client:\t"+user+"\n"));

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
    private  ArrayList<Event> ping(){
        ArrayList<Event> currentDisconnectedClients = serverSocket.ping();
        currentDisconnectedClients.addAll(serverRMI.ping());
       if(!currentDisconnectedClients.isEmpty()){
           log.info(lobbyName.concat(currentDisconnectedClients.size()+" disconnected clients in this turn!\n"));
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
    private  void disconnectClient(String user){
        activeClientList.remove(user);
        disconnectedClientList.add(user);
        message = mapUserServer.get(user).disconnectClient(user);
        mapUserServer.remove(user);
        mapUserView.get(user).toController(message);
        log.info(lobbyName.concat(":\tListened message from:\t" + message.getUser()+"\n"));
        log.warning(lobbyName.concat(":\tClient Disconnected:\t"+user+"\n"));
    }

    /**
     * Add and map the last connected user with his server implementation
     * @param currUser his the client username
     * @param serverImplementation is the server implementation depending on user's network preferences
     */
    private  void userAddAndMap( String currUser, ServerInterface serverImplementation){
        activeClientList.add(currUser);
        mapUserServer.put(currUser,serverImplementation);
    }

    /**
     * If there isn't any username conflict (when a client try to connect with an already token username),
     *  this method update
     * @return the last connected username
     */
    private  String updateActiveClientList() {
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
    private  String updateActiveClientList(ServerInterface serverImplementation){
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
    private  String updateMixedActiveClientList(){
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
                log.info(lobbyName.concat("Sending message to:\t"+currUser+"\n"));
                serverImplementation.sendMessage(new UsernameModificationEvent(currUser,connectedUser));
                serverImplementation.updateUsername(currUser,connectedUser);
                return connectedUser;
            }
        }
        return connectedUser;
    }


}
