package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
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

public class WaitServer {

    private static Logger log = Logger.getLogger("ServerLogger");
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    //setta RMIPORTNUMB e SOCKETPORTNUMB(--)
    private static final int startPortNumber = NetConfiguration.RMISERVERPORTNUMBER-4;
    private static boolean shutDown = false;
    private static ArrayList<Server> activeLobby = new ArrayList<>();
    private static ArrayList<String> connectedUsers = new ArrayList<>();
    private static Map<String,ServerInterface> mapUserServer = new HashMap<>();
    private static ServerInterface acceptingRMI;
    private static ServerInterface acceptingSocket;

    public static void main(String[] args){
        try {

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

            checkNewClient();


            if(connectedUsers.size()>0){
                log.info("Type Quit for server shutdown\n");
                try{
                    String inputCommand = reader.readLine();
                    if(inputCommand.equalsIgnoreCase("QUIT")) {
                        shutDown=true;
                    }
                }catch (IOException exc){
                    shutDown = false;
                }
            }


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
     * This method, depending on gameCouldStart value, handle the incoming client connections
     */
    private static void checkNewClient(){
        if (connectedUsers.size() != acceptingSocket.getClientList().size() + acceptingRMI.getClientList().size()) {

            //gameCouldstart = WelcomeEvent.boh
            //vuol dire che player vuole entrare in partita già in corso, chiama la p
            //if(!gameCouldStart) {
                String connectedUser = updateMixedConnectedUser();
                if (connectedUser.isEmpty()) {
                    connectedUser = updateConnectedUser();
                }
            /*}
            else{
                reconnectClient();
            }*/
        }

    }

    /**
     * If there isn't any username conflict (when a client try to connect with an already token username),
     *  this method update
     * @return the last connected username
     */
    private static String updateConnectedUser() {
        String connectedUser = updateConnectedUser(acceptingRMI);
        if (connectedUser.isEmpty()) {
            connectedUser = updateConnectedUser(acceptingSocket);
        }
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



}
