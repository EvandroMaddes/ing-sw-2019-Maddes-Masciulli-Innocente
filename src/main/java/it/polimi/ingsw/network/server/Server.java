package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.server_view_event.ClientReconnectionEvent;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.event.server_view_event.WelcomeEvent;
import it.polimi.ingsw.event.view_server_event.ViewServerEvent;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.utils.NetConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Is the main Game Server, handles the incoming connection from new clients and redirects them to the chosen Lobby
 * @author Francesco Masciulli
 */
public class Server {

    private static Logger log = Logger.getLogger("ServerLogger");
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Scanner scanner = new Scanner(reader);
    private static ArrayList<Lobby> activeLobbies = new ArrayList<>();
    private static ArrayList<String> connectedUsers = new ArrayList<>();
    private static Map<String,ServerInterface> mapUserServer = new HashMap<>();
    private static ServerInterface acceptingRMI;
    private static ServerInterface acceptingSocket;
    private static int handledUsers = 0;
    private static ArrayList<String> waitingLobby = new ArrayList<>();
    private static ArrayList<String> startedLobby = new ArrayList<>();
    private static int gamesHandled = 0;

    public static void main(String[] args){
        boolean shutDown = false;
        try {
            boolean isSetted = false;
            int gameTimerValue;
            while(!isSetted){
                log.info("Please, setUp the ServerConfiguration:\n\tinsert the StartGameTimer value [in seconds]");
                try {
                     gameTimerValue = scanner.nextInt();
                    scanner.nextLine();

                } catch (Exception e){
                    scanner.nextLine();
                    gameTimerValue = -1;
                }
                if(gameTimerValue > 0){
                    NetConfiguration.setStartGameTimer(gameTimerValue);
                    isSetted=true;
                }
            }
            isSetted = false;
            while(!isSetted){
                log.info("Now, please, insert the RoundTimer value [in seconds]");
                try {
                    gameTimerValue = scanner.nextInt();
                    scanner.nextLine();

                } catch (Exception e){
                    scanner.nextLine();
                    gameTimerValue = -1;
                }
                if(gameTimerValue > 0){
                    NetConfiguration.setRoundTimer(gameTimerValue);
                    isSetted=true;
                }
            }
            acceptingRMI = new RMIServer();
            acceptingSocket = new SocketServer();
            ((SocketServer)acceptingSocket).start();
            Thread rmiThread = new Thread((RMIServer)acceptingRMI);
            rmiThread.start();
            log.info("Server ready to accept clients\n");
        } catch(RemoteException e){
            CustomLogger.logException(e);
        }
        while(!shutDown){
            cleanConnectedUsers();
            updateStartedLobbies();
            String incomingUser = checkNewClient();
            if (!incomingUser.isEmpty()) {
                welcomeUser(incomingUser);
            }
            try {
                if(reader.ready()&&scanner.nextLine().equalsIgnoreCase("quit")){
                    shutDown = true;
                }
            }
            catch (IOException nothingToRead){
                shutDown = false;
            }
        }

        shutDownServer();
    }


    /**
     * When a new client reaches the server, a WelcomeEvent is created and sent
     * @param incomingUser is the client username
     */
    private static void welcomeUser(String incomingUser){
        boolean[] availableChoices = {true, false, false};
        ArrayList<String> availableStartedLobbies = new ArrayList<>();
        if(!waitingLobby.isEmpty()){
            availableChoices[1] = true;
        }
        if(!startedLobby.isEmpty()){
            for (Lobby currLobby: activeLobbies) {
                if(!currLobby.getDisconnectedClientList().isEmpty()){
                    availableChoices[2] = true;
                    availableStartedLobbies.add(currLobby.getLobbyName());
                }
            }
        }
        ViewServerEvent message = null;
        ServerInterface currServer = mapUserServer.get(incomingUser);
        currServer.sendMessage(new WelcomeEvent(incomingUser, availableChoices, waitingLobby,availableStartedLobbies));
        message = (ViewServerEvent) currServer.listenMessage();
        if(message == null){
            currServer.disconnectClient(incomingUser);
            handledUsers--;
            log.warning("Disconnected client");
        }
        else if(message.isNewGame()){
            startNewGame(message.performAction());
        }
        else{
            joinLobby(message.performAction(),message.getUser());
        }

    }

    /**
     * If requested by a client, starts a new lobby and redirect to this the client who created it
     * @param user is the creator's username
     */
    private static void startNewGame(String user){
        Lobby newLobby;
        newLobby = new Lobby();
        activeLobbies.add(newLobby);
        newLobby.setLobby(user,gamesHandled);
        gamesHandled++;
        waitingLobby.add(newLobby.getLobbyName());
        newLobby.start();
        reconnectClient(user, newLobby);
    }

    /**
     * If requested by a client, it redirect the requesting client to the selected lobby
     * @param lobbyID is the chosen lobby ID ("CreatorName".concat("'s lobby"))
     * @param user is the joining client username
     */
    private static void joinLobby(String lobbyID, String user){
        Lobby returnedLobby;
            returnedLobby = findLobbyByID(lobbyID);
            reconnectClient(user,returnedLobby);
    }

    /**
     * It find, given a lobbyID, the respectively Lobby
     * @param lobbyID is the searched lobby ID ("CreatorName".concat("'s lobby"))
     * @return the Lobby that has the given lobbyID
     */
    private static Lobby findLobbyByID(String lobbyID){
        for (Lobby currLobby: activeLobbies) {
            if(currLobby.getLobbyName().equals(lobbyID)){
                return currLobby;
            }
        }
        return null;
    }

    /**
     * Redirect a client from the Server to the given Lobby
     * @param username is the Client username
     * @param lobby is the Lobby on which the client will be redirected
     */
    private static void reconnectClient(String username, Lobby lobby){
        int portNumber;
        ServerInterface currServer;
        if(mapUserServer.get(username).equals(acceptingRMI)){
            currServer = acceptingRMI;
            portNumber = lobby.getPortRMI();    
        }
        else{
            currServer = acceptingSocket;
            portNumber = lobby.getPortSocket();
        }
        currServer.sendMessage(new ClientReconnectionEvent(username, portNumber));
        currServer.disconnectClient(username);
        handledUsers--;
        
    }

    /**
     * Removes all the disconnected users, so a reconnecting client could reconnect with its old username
     */
    private static void cleanConnectedUsers(){
        for (Lobby currLobby: activeLobbies) {
            connectedUsers.removeAll(currLobby.getDisconnectedClientList());
        }
    }


    /**
     * Add and map the last connected user with his server implementation
     * @param currUser his the client username
     * @param serverImplementation is the server implementation depending on user's network preferences
     */
    private static void userAddAndMap( String currUser, ServerInterface serverImplementation){
        connectedUsers.add(currUser);
        mapUserServer.put(currUser,serverImplementation);
    }

    /**
     * Update the activeLobbies List, moving the Lobby with a begun match from the waitingLobby List.
     */
    private static void updateStartedLobbies(){
        ArrayList<Lobby> removingActiveLobbies = new ArrayList<>();
        for (Lobby currLobby: activeLobbies) {
            if(currLobby.isGameCouldStart()&& waitingLobby.remove(currLobby.getLobbyName())){
                    startedLobby.add(currLobby.getLobbyName());
            }
            if(currLobby.isShutDown()&&!waitingLobby.contains(currLobby.getLobbyName())){
                startedLobby.remove(currLobby.getLobbyName());
                removingActiveLobbies.add(currLobby);
                connectedUsers.removeAll(currLobby.getActiveClientList());
            }
        }
        activeLobbies.removeAll(removingActiveLobbies);
    }


    /**
     * This method, depending on gameCouldStart value, handle the incoming client connections.
     * @return true if there is a new client connection
     */
    private static String checkNewClient(){
        String connectedUser = "";
        if (handledUsers < acceptingSocket.getClientList().size() + acceptingRMI.getClientList().size()) {
            handledUsers = acceptingSocket.getClientList().size() + acceptingRMI.getClientList().size();
            connectedUser = updateMixedConnectedUser();
            if (connectedUser.isEmpty()) {
                connectedUser = updateConnectedUser();
            }
            return connectedUser;

        }
        return connectedUser;

    }

    /**
     * If there isn't any username conflict (when a client try to connect with an already token username),
     *  this method update
     * @return the last connected username
     */
    private static String updateConnectedUser() {
        ServerInterface serverImplementation = acceptingRMI;
        String connectedUser = updateConnectedUser(serverImplementation);
        if (connectedUser.isEmpty()) {
            serverImplementation = acceptingSocket;
            connectedUser = updateConnectedUser(serverImplementation);
        }
        if(connectedUsers.contains(connectedUser)){
            String currUser = connectedUser;
            connectedUser = currUser + new Random().nextInt(100);
            log.info("Sending message to:\t"+currUser+"\n");
            serverImplementation.sendMessage(new UsernameModificationEvent(currUser,connectedUser));
            serverImplementation.updateUsername(currUser,connectedUser);

        }


        userAddAndMap(connectedUser, serverImplementation);
        return connectedUser;
    }



    /**
     * This method update the client list from the given server implementation
     * @param serverImplementation is the chosen implementation
     * @return the last connected user, or an empty string if there isn't
     */
    private static String updateConnectedUser(ServerInterface serverImplementation){
        String connectedUser ="";
        ArrayList<String> connectedList = serverImplementation.getClientList();

        for (String currUser: connectedList) {
            if (!connectedUser.contains(currUser)) {
                connectedUser = currUser;

                return  connectedUser;
            }
        }    return connectedUser;
    }

    /**
     * this method update the last connected client that use an already registered username,
     *   it calls method that change it in the server implementation and send the notification to the client
     * @return the new username
     */
    private static String updateMixedConnectedUser(){
        int usernameNumber;
        String connectedUser = "";
        ServerInterface serverImplementation;
        ArrayList<String> mixedList = new ArrayList<>(acceptingRMI.getClientList());
        mixedList.addAll(acceptingSocket.getClientList());
        for (String currUser: mixedList) {
            usernameNumber = Collections.frequency(mixedList, currUser);
            if(usernameNumber>1){
                int rmiUsers = Collections.frequency(acceptingRMI.getClientList(), currUser);
                connectedUser = currUser + usernameNumber + new Random().nextInt(100);
                if(mixedList.indexOf(currUser)<acceptingRMI.getClientList().size() &&
                        !(rmiUsers==1 && mapUserServer.get(currUser).equals(acceptingRMI)) ){
                    serverImplementation = acceptingRMI;
                }
                else{
                    serverImplementation = acceptingSocket;
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

    private static void shutDownServer(){
        for (Lobby currLobby: activeLobbies) {
            currLobby.shutDownLobby();
        }
        acceptingRMI.shutDown();
        acceptingSocket.shutDown();
        log.info("Shutting-down the server..");
        System.exit(0);
    }


}
