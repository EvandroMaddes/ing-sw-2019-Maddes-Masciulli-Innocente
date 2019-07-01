package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.CustomTimer;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * This class is the implementation of the MultiThreading SocketServer
 * @author Francesco Masciulli
 */
public class SocketServer extends Thread implements ServerInterface {
    private ServerSocket serverSocket ;
    private int serverPort= NetConfiguration.SOCKETSERVERPORTNUMBER;
    private CopyOnWriteArrayList<SocketServerThread> socketList = new CopyOnWriteArrayList<>();
    private boolean gameCouldStart = false;
    private boolean gameIsRunning = false;

    /**
     * Getter method
     * @return the port on which the server is started
     */
    @Override
    public int getPort() {
        return serverPort;
    }

    /**
     * Setter method
     * @param serverPort is the port number, depending on the Lobby utilization
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort ;
    }

    /**
     * Thread's run() implementation:
     * it wait the end of the match, accepting new client
     */
    @Override
    public void run(){
        runServer();
        while(!gameCouldStart){
            acceptClient();
        }
        gameIsRunning = true;
        while(gameIsRunning){
            acceptClient();
        }

    }


    /**
     * Iterates on the SocketThreads, returning a list of string
     * @return an ArrayList with the usernames.
     */
    public ArrayList<String> getClientList() {
        ArrayList<String> clientUserList = new ArrayList<>();
        Iterator iterator = socketList.iterator();
        while(iterator.hasNext()){
            SocketServerThread currClientThread = (SocketServerThread) iterator.next();
            clientUserList.add(currClientThread.getClientUser());
        }
        return clientUserList;
    }

    /**
     * Set gameCouldStart TRUE.
     */
    @Override
    public void gameCouldStart() {
        gameCouldStart=true;
    }

    /**
     * In case of a username change, this method update the SocketServerThread's client
     * @param username is the old username, that must be changed;
     * @param newUser is the new username, that is set;
     */
    @Override
    public void updateUsername(String username, String newUser) {
        for (int i = socketList.size()-1; i >=0 ; i--) {
            SocketServerThread currSocketThread = socketList.get(i);
            if(currSocketThread.getClientUser().equalsIgnoreCase(username)){
                currSocketThread.setClientUser(newUser);
                return;
            }
        }
    }


    /**
     * This ServerInterface's implementation start the server
     */
    @Override
    public void runServer() {
        try{

        serverSocket = new ServerSocket(serverPort);
        }catch(IOException e){
            CustomLogger.logException(e);
        }



    }

    /**
     * If the match isn't started, accept new connections
     * if the match is running, it handle
     */
    @Override
    public void acceptClient() {
        if (socketList.size()==5) {
            gameCouldStart = true;
        }
        else {
            try {
                Socket clientSocket = serverSocket.accept();
                SocketServerThread clientSocketThread = new SocketServerThread(clientSocket);
                clientSocketThread.start();

                socketList.add(clientSocketThread);
            } catch (IOException e) {
                gameIsRunning=false;
            }
        }
    }

    /**
     * Sends the message to each client connected
     * @param message is the Event that must be sent.
     */
    @Override
    public void sendBroadcast(Event message) {
        for (SocketServerThread currThread: socketList) {
            currThread.sendMessage(message);
        }
    }

    /**
     * This method shutDown the server
     */
    //todo non si arresta run() prima che si chiudano i socket a seguito di Lobby.disconnect(), da controllare;
    @Override
    public void shutDown() {
        for (SocketServerThread currThread: socketList) {

            currThread.disconnect();
        }
        try{
            gameIsRunning= false;
            serverSocket.close();
        }catch (IOException ioExc){
            CustomLogger.logException(ioExc);
        }
    }

    /**
     * Send the given message with the respectively ServerSocketThread, find with the username.
     * @param message is the Event that must be sent
     */
    @Override
    public void sendMessage(Event message) {

        for (int i = socketList.size()-1; i >= 0 ; i--) {
            if(socketList.get(i).getClientUser().equals(message.getUser())){

                socketList.get(i).sendMessage(message);
                return;
            }
        }

    }

    /**
     * This method start a custom timer and wait for a message delivery;
     * @return the listened message, or null if the timer elapses and any message is listened.
     */
    @Override
    public Event listenMessage() {
        Event currMessage;
        CustomTimer timer = new CustomTimer(NetConfiguration.roundTimer);
        timer.start();
        Logger log = Logger.getLogger("Logger");
        log.info("Started the round countdown!\nPlayer disconnected in " + NetConfiguration.roundTimer + " seconds.\n");
        for (int i = 0; i < socketList.size() ; i++) {
            SocketServerThread currSocket = socketList.get(i);
            if(currSocket.getCurrMessage()!=null&&currSocket.isConnected()) {
                currMessage = currSocket.getCurrMessage();
                currSocket.resetMessage();
                return currMessage;

            }
            else if (i == socketList.size()-1&&timer.isAlive()){
                i = -1;
            }
        }
        return null;
    }

    /**
     * Force a client KickOut after the timer elapses
     * @param user the client's username that must be kicked out
     * @return the DisconnectionEvent
     */
    @Override
    public Event disconnectClient(String user) {
        for (SocketServerThread currThread: socketList) {
            if(currThread.getClientUser().equals(user)){
                currThread.disconnect();
                Event currEvent = currThread.getCurrMessage();
                currThread.kill();
                currThread.interrupt();
                socketList.remove(currThread);
                return currEvent;
            }
        }
        return null;
    }

    /**
     * Call isConnected on each thread, eventually saving the relative DisconnectedClientEvent
     * @return all of the DisconnectedClientEvents of this round
     */
    @Override
    public synchronized   ArrayList<Event> ping(){
        ArrayList<Event> currentDisconnectedClients = new ArrayList<>();
        for (int i = 0; i < socketList.size(); i++) {
            SocketServerThread currThread= socketList.get(i);

            if(!currThread.isConnected()){
                currentDisconnectedClients.add(currThread.getCurrMessage());

            }
        }
        return currentDisconnectedClients ;
    }
}
