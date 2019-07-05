package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.serverviewevent.LobbySettingsEvent;
import it.polimi.ingsw.event.serverviewevent.ReconnectionRequestEvent;
import it.polimi.ingsw.event.serverviewevent.UsernameModificationEvent;
import it.polimi.ingsw.event.viewcontrollerevent.DisconnectedEvent;
import it.polimi.ingsw.event.controllerviewevent.GameRequestEvent;
import it.polimi.ingsw.event.viewcontrollerevent.GameChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.UpdateChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.utils.CustomTimer;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.view.VirtualView;

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Is the Lobby implementation
 *
 * @author Francesco Masciulli
 */
public class Lobby extends Thread {
    /**
     * Lobby logger
     */
    private Logger log = Logger.getLogger("LobbyLogger");
    /**
     * Is the Lobby ID
     */
    private String lobbyName;
    /**
     * An ArrayList that contains all of the connected clients VirtualViews
     */
    private ArrayList<VirtualView> virtualViewList = new ArrayList<>();
    /**
     * An ArrayList that contains all of the connected and active clients username
     */
    private ArrayList<String> activeClientList = new ArrayList<>();
    /**
     * An ArrayList that contains all of the disconnected from game clients username
     */
    private ArrayList<String> disconnectedClientList = new ArrayList<>();
    /**
     * Is the Lobby RMIServer
     */
    private RMIServer serverRMI;
    /**
     * Is the RMIServer port number
     */
    private int portRMI;
    /**
     * Is the Lobby SocketServer
     */
    private SocketServer serverSocket;
    /**
     * Is the SocketServer port number
     */
    private int portSocket;
    /**
     * This Map give the respectively ServerInterface given a client username
     */
    private Map<String, ServerInterface> mapUserServer = new HashMap<>();
    /**
     * This Map give the respectively VirtualView given a client username
     */
    private Map<String, VirtualView> mapUserView = new HashMap<>();
    /**
     * Is a CustomTimer handling the game start
     */
    private CustomTimer gameTimer;
    /**
     * It contains a message listened or that must be sent
     */
    private Event message;
    /**
     * This boolean is true if the game is started
     */
    private boolean gameCouldStart = false;
    /**
     * This boolean is true if the Lobby must be shut-down
     */
    private boolean shutDown = false;
    /**
     * Is the match map choice
     */
    private int mapChoice = 404;

    /**
     * Getter method:
     *
     * @return RMIServer port number
     */
     int getPortRMI() {
        return portRMI;
    }

    /**
     * Getter method:
     *
     * @return the SocketServer port number
     */
    int getPortSocket() {
        return portSocket;
    }

    /**
     * Getter method:
     *
     * @return all of the Lobby disconnected clients username
     */
    ArrayList<String> getDisconnectedClientList() {
        return disconnectedClientList;
    }

    /**
     * Called by the Server when a new Lobby is created: depending on the parameters set RMI and Socket ports and starts them
     *
     * @param lobbyName    is the lobby creator's username
     * @param aliveLobbies is the number of already created lobbies
     */
    void setLobby(String lobbyName, int aliveLobbies) {
        this.lobbyName = lobbyName + "'s lobby";
        portRMI = NetConfiguration.RMISERVERPORTNUMBER + 6 * aliveLobbies + 1;
        portSocket = NetConfiguration.SOCKETSERVERPORTNUMBER + aliveLobbies + 1;
        try {
            serverRMI = new RMIServer(portRMI);
            serverSocket = new SocketServer();
            serverSocket.setServerPort(portSocket);
        } catch (RemoteException exc) {
            CustomLogger.logException(exc);
        }
    }


    /**
     * Getter method:
     *
     * @return lobbyName String
     */
    String getLobbyName() {
        return lobbyName;
    }


    /**
     * Is the Thread run() implementation:
     * it start the already allocated and set RMI/Socket servers and handle the network from the beginning to the end of the match;
     */
    @Override
    public void run() {
        Controller lobbyController;
        serverSocket.start();
        Thread rmiThread = new Thread(serverRMI);
        rmiThread.start();
        log.info(lobbyName.concat(":\tReady to accept clients\n"));
        boolean setUpComplete = false;


        while (!shutDown) {

            //Aggiorna il numero di client connessi e li mappa verso il server giusto, allocando una VirtualView
            //Gestione dell'inizializzazione della partita
            while (!gameCouldStart) {
                ArrayList<Event> disconnectedClients = ping();
                if (disconnectedClients.isEmpty()) {
                    if (activeClientList.size() <= 2) {

                        if (gameTimer != null) {
                            gameTimer.interrupt();
                            log.info("GameTimer stopped!");
                        }
                        gameTimer = null;
                    }
                    checkNewClient();
                    if (!activeClientList.isEmpty() && !setUpComplete) {
                        String firstUser = activeClientList.get(0);
                        ServerInterface currServer = mapUserServer.get(firstUser);
                        currServer.sendMessage(new GameRequestEvent(firstUser));
                        log.info(lobbyName.concat(":\tSending message to:\t".concat(firstUser.concat("\n"))));
                        while (message == null) {
                            message = currServer.listenMessage();
                        }
                        mapChoice = ((GameChoiceEvent) message).getMap();
                        setUpComplete = true;
                        log.info(lobbyName.concat("\tListened message from:\t".concat(message.getUser().concat("\n"))));
                        message = null;
                    }
                }


                if (activeClientList.size() > 2) {
                    if (gameTimer == null) {
                        gameTimer = new CustomTimer(NetConfiguration.getStartGameTimer());
                        gameTimer.start();
                        log.info(lobbyName.concat(":\tStarted the match countdown!\n\nGame start in ".concat(
                                Integer.toString(NetConfiguration.getStartGameTimer()).concat(" seconds.\n"))));
                    } else if (!gameTimer.isAlive()) {
                        serverRMI.gameCouldStart();
                        serverSocket.gameCouldStart();
                        gameCouldStart = true;
                        //istanzia controller e inizia computazione
                        lobbyController = new Controller(mapUserView, mapChoice);
                        GameModel model = lobbyController.getGameManager().getModel();
                        for (VirtualView connectedPlayer : virtualViewList) {
                            connectedPlayer.addObserver(lobbyController);
                            model.addObserver(connectedPlayer);
                        }
                        log.info(lobbyName.concat(":\tGame could start; There are " + activeClientList.size() + " players\n"));
                    }
                }


            }

            // ora si gestisce il turno, il controller ha settato nextMessage
            message = null;
            //Update dei giocatori riconnessi, all'inizio di ogni turno di un giocatore
            checkNewClient();
            //Update dei giocatori disconnessi, all'inizio di ogni cambio di contesto
            ArrayList<Event> disconnectedClients = ping();
            if (disconnectedClients.isEmpty()) {
                Event nextMessage = findNextMessage();
                try {
                    String currentUser = nextMessage.getUser();

                    message = sendAndWaitNextMessage(nextMessage);
                    if (message == null) {
                        message = new DisconnectedEvent(currentUser);
                        disconnectClient(currentUser);
                    } else if (!message.getUser().equals("BROADCAST")) {
                        try {
                            mapUserView.get(message.getUser()).toController(message);
                            log.info(lobbyName.concat(":\tListened message from:\t" + message.getUser() + "\n"));

                        } catch (ClassCastException exc) {
                            message = null;
                        }
                    }
                } catch (NullPointerException noNewMessage) {

                    gameCouldStart = false;
                    shutDown = true;
                    break;
                }
            } else {

                log.severe("Client disconnected in other player's turn" + "\n");


            }


            //todo controllo se gioco terminato || dopo WinnerEvent??
            shutDown = !gameCouldStart;

        }
        serverRMI.shutDown();
        serverSocket.shutDown();
        log.info(lobbyName.concat("\t: ShutDown\n"));

    }

    /**
     * Getter method:
     *
     * @return the activeClientList usernames
     */
    ArrayList<String> getActiveClientList() {
        return activeClientList;
    }

    boolean isGameCouldStart() {
        return gameCouldStart;
    }

    boolean isShutDown() {
        return shutDown;
    }

    /**
     * this method set the Lobby shutdown signal to false;
     */
    void shutDownLobby() {
        shutDown = true;
    }

    /**
     * Send the message that has to be sent, depending on its type:
     * the ControllerViewEvent message is sent to the respectively user and wait for an answer (the listenMessage return a DisconnectedUserEvent if the RoundTimer elapses);
     * the broadcast message is sent to everyone and is returned directly an UpdateChoiceEvent
     *
     * @param toSend is the message that must be sent
     * @return the answer message
     */
    private Event sendAndWaitNextMessage(Event toSend) {
        String currentUser = toSend.getUser();
        Event returnedEvent = null;
        log.info(lobbyName.concat(":\tSending message to:\t" + currentUser + "\n"));
        if (toSend.getUser().equals("BROADCAST")) {
            serverRMI.sendBroadcast(toSend);
            serverSocket.sendBroadcast(toSend);
            returnedEvent = new UpdateChoiceEvent("BROADCAST");

        } else {
            ServerInterface server = mapUserServer.get(currentUser);
            server.sendMessage(toSend);
            returnedEvent = server.listenMessage();
        }
        return returnedEvent;
    }

    /**
     * it clean eventually messages that were already sent (are more than one if Broadcast)
     *
     * @param isBroadcast     is true if was a Broadcast message
     * @param toRemoveMessage is the sent message
     */
    private void cleanVirtualViews(boolean isBroadcast, Event toRemoveMessage) {
        for (VirtualView currentView : virtualViewList) {
            if (isBroadcast) {
                currentView.getModelUpdateQueue().remove(toRemoveMessage);
            } else {
                if (currentView.getToRemoteView() != null) {
                    currentView.callRemoteView(null);
                    return;
                }
            }
        }
    }

    /**
     * Iterate on the VirtualViews and find the message that will be sent:
     * if is Broadcast, and this messages have higher priority than the Controller-View ones, it is dequeued from all the Views
     *
     * @return the next message to send from VirtualViews, null if the match is over
     */
    private Event findNextMessage() {
        message = null;
        Event currMessage;
        for (VirtualView currView : virtualViewList) {
            currMessage = currView.getModelUpdateQueue().poll();
            if (currMessage != null) {
                cleanVirtualViews(true, currMessage);
                return currMessage;
            }
        }
        for (VirtualView currentView : virtualViewList) {
            currMessage = currentView.getToRemoteView();
            if (currMessage != null) {
                cleanVirtualViews(false, currMessage);
                return currMessage;
            }
        }
        return null;
    }


    /**
     * This method, depending on gameCouldStart value, handle the incoming client connections
     */
    private void checkNewClient() {
        if (activeClientList.size() != serverSocket.getClientList().size() + serverRMI.getClientList().size()) {
            if (!gameCouldStart) {
                String connectedUser = updateMixedActiveClientList();
                if (connectedUser.isEmpty()) {
                    connectedUser = updateActiveClientList();
                }
                if (!mapUserView.containsKey(connectedUser)) {
                    VirtualView userView = new VirtualView(connectedUser);
                    virtualViewList.add(userView);
                    mapUserView.putIfAbsent(connectedUser, userView);

                }
                if (mapChoice != 404&&mapUserServer.containsKey(connectedUser)) {
                    mapUserServer.get(connectedUser).sendMessage(new LobbySettingsEvent(connectedUser, mapChoice));
                }
            } else {
                reconnectClient();
            }
        }

    }

    /**
     * It handle the clients reconnection during the game
     */
    private void reconnectClient() {
        ArrayList<String> reconnectedClients = serverRMI.getClientList();
        reconnectedClients.addAll(serverSocket.getClientList());
        ServerInterface serverImplementation;
        for (String user : reconnectedClients) {
            if (reconnectedClients.indexOf(user) < serverRMI.getClientList().size()) {
                serverImplementation = serverRMI;
            } else {
                serverImplementation = serverSocket;
            }
            if (!activeClientList.contains(user)) {
                serverImplementation.sendMessage(new ReconnectionRequestEvent(user, disconnectedClientList));
                log.info(lobbyName.concat(":\tSending message to:\t" + user + "\n"));
                Event listenedMessage = serverImplementation.listenMessage();
                if (listenedMessage != null) {
                    String connectedUsername = user;
                    user = listenedMessage.getUser();
                    serverImplementation.updateUsername(connectedUsername, user);
                    mapUserServer.remove(connectedUsername);
                    mapUserServer.put(user, serverImplementation);
                    mapUserView.get(listenedMessage.getUser()).toController(listenedMessage);
                    disconnectedClientList.remove(user);
                    activeClientList.add(user);
                    serverRMI.cleanDisconnectedEventList(user);
                    log.info(lobbyName.concat(":\tReconnected client:\t" + user + "\n"));

                } else {
                    serverImplementation.disconnectClient(user);
                }
            }
        }


    }

    /**
     * This method is called before the sendMessage, it update the clients that disconnected during the last round
     *
     * @return the DisconnectedEvents from the disconnected clients
     */
    private ArrayList<Event> ping() {
        ArrayList<Event> currentDisconnectedClients = serverSocket.ping();
        currentDisconnectedClients.addAll(serverRMI.ping());
        if (!currentDisconnectedClients.isEmpty()) {
            log.info(lobbyName.concat(currentDisconnectedClients.size() + " disconnected clients in this turn!\n"));
            for (Event currEvent : currentDisconnectedClients) {
                message = currEvent;
                disconnectClient(currEvent.getUser());
                message = null;
            }
        }
        serverRMI.cleanDisconnectedEventList();
        return currentDisconnectedClients;
    }


    /**
     * Is called when the listenMessage() return null, exactly when its timer elapses, or by ping()
     * handle the Kick out of the selected user calling the toController view method
     *
     * @param user is the user that must be kicked out
     */
    private void disconnectClient(String user) {
        activeClientList.remove(user);
        disconnectedClientList.add(user);
        if (mapUserServer.containsKey(user)) {
            message = mapUserServer.get(user).disconnectClient(user);
        }
        mapUserServer.remove(user);
        if (isGameCouldStart()) {
            mapUserView.get(user).toController(message);
        }
        log.info(lobbyName.concat(":\tListened message from:\t" + message.getUser() + "\n"));
        log.warning(lobbyName.concat(":\tClient Disconnected:\t" + user + "\n"));
        serverRMI.cleanDisconnectedEventList(user);
    }

    /**
     * Add and map the last connected user with his server implementation
     *
     * @param currUser             his the client username
     * @param serverImplementation is the server implementation depending on user's network preferences
     */
    private void userAddAndMap(String currUser, ServerInterface serverImplementation) {
        activeClientList.add(currUser);
        mapUserServer.put(currUser, serverImplementation);
    }

    /**
     * If there isn't any username conflict (when a client try to connect with an already token username),
     * this method update
     *
     * @return the last connected username
     */
    private String updateActiveClientList() {
        String connectedUser = updateActiveClientList(serverRMI);
        if (connectedUser.isEmpty()) {
            connectedUser = updateActiveClientList(serverSocket);
        }
        return connectedUser;
    }

    /**
     * This method update the client list from the given server implementation
     *
     * @param serverImplementation is the chosen implementation
     * @return the last connected user, or an empty string if there isn't
     */
    private String updateActiveClientList(ServerInterface serverImplementation) {
        String connectedUser = "";
        ArrayList<String> connectedList = serverImplementation.getClientList();

        for (String currUser : connectedList) {
            if (!activeClientList.contains(currUser)) {
                connectedUser = currUser;
                userAddAndMap(connectedUser, serverImplementation);
                return connectedUser;
            }
        }
        return connectedUser;
    }

    /**
     * this method update the last connected client that use an already registered username,
     * it calls method that change it in the server implementation and send the notification to the client
     *
     * @return the new username
     */
    private String updateMixedActiveClientList() {
        int usernameNumber;
        String connectedUser = "";
        ServerInterface serverImplementation;
        ArrayList<String> mixedList = new ArrayList<>(serverRMI.getClientList());
        mixedList.addAll(serverSocket.getClientList());
        for (String currUser : mixedList) {
            usernameNumber = Collections.frequency(mixedList, currUser);
            if (usernameNumber > 1) {
                int rmiUsers = Collections.frequency(serverRMI.getClientList(), currUser);
                connectedUser = currUser + usernameNumber + new Random().nextInt(100);
                if (mixedList.indexOf(currUser) < serverRMI.getClientList().size() &&
                        !(rmiUsers == 1 && mapUserServer.get(currUser).equals(serverRMI))) {
                    serverImplementation = serverRMI;
                } else {
                    serverImplementation = serverSocket;
                }
                userAddAndMap(connectedUser, serverImplementation);
                log.info(lobbyName.concat("Sending message to:\t" + currUser + "\n"));
                serverImplementation.sendMessage(new UsernameModificationEvent(currUser, connectedUser));
                serverImplementation.updateUsername(currUser, connectedUser);
                return connectedUser;
            }
        }
        return connectedUser;
    }


}
