package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.DisconnectedEvent;
import it.polimi.ingsw.utils.CustomTimer;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * This class is the RMIServer implementation
 * @author Francesco Masciulli
 */
public class RMIServer extends UnicastRemoteObject implements Runnable, RemoteInterface, ServerInterface {
    private CopyOnWriteArrayList<RemoteInterface> clientList;
    private Map<Integer, String> clientUserOrder = new HashMap<>();
    //private transient ArrayList<Registry> clientRegistries;
    private Event currMessage;
    private ArrayList<Event> disconnectedClients = new ArrayList<>();
    //private transient Registry registry;
    private String ipAddress;
    private int portRMI;
    private boolean gameCouldStart = false;
    private boolean gameCouldTerminate = false;


    /**
     * @throws RemoteException
     */
    public RMIServer() throws RemoteException {
        super(NetConfiguration.RMISERVERPORTNUMBER);
        portRMI = NetConfiguration.RMISERVERPORTNUMBER;
        clientList = new CopyOnWriteArrayList<>();
      //  clientRegistries = new ArrayList<>();
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            CustomLogger.logException(e);
        }

    }
    /**
     * @throws RemoteException
     */
    public RMIServer(int portNumber) throws RemoteException {
        super(portNumber);
        portRMI = portNumber;
        clientList = new CopyOnWriteArrayList<>();
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            CustomLogger.logException(e);
        }

    }

    /**
     * Getter method
     *
     * @return the connected Clients ArrayList
     */
    public ArrayList<String> getClientList() {
        ArrayList<String> clientUserList = new ArrayList<>();
        Iterator iterator = clientList.iterator();
        while (iterator.hasNext()) {
            RemoteInterface currRemoteClient = (RemoteInterface) iterator.next();
            try {
                clientUserList.add(currRemoteClient.getUser());
            } catch (RemoteException e) {

            }
        }
        return clientUserList;
    }

    /**
     * Getter method
     * @return the port on which the RMIServer is exported
     */
    @Override
    public int getPort() {
        return portRMI;
    }


    /**
     * This method clear the DisconnectedEventList after that the Lobby save it
     */
    public void cleanDisconnectedEventList(){
        for (Event currEvent : disconnectedClients) {
            clientUserOrder.remove(getClientList().indexOf(currEvent.getUser()));
        }
        disconnectedClients.clear();
    }

    /**
     * This method clean the DisconnectedEvent relative for a player
     * @param user is the player Username
     */
    public void cleanDisconnectedEventList(String user){
        for (Event currEvent : disconnectedClients) {
            if(currEvent.getUser().equals(user)){
                disconnectedClients.remove(currEvent);
                return;
            }
        }
    }

    /**
     * During the setup, it accept new connection;
     *  while running, it check that the connection is alive, until the game could terminate.
     */
    @Override
    public void run() {
        runServer();

        while(!gameCouldTerminate){
            while (!gameCouldStart) {
                acceptClient();
            }
            try{
                clientConnectionGuard();
            }catch (RemoteException disconnected){
                CustomLogger.logException(disconnected);
            }
        }

    }


    /**
     * Getter RemoteInterface Implementations
     */


    /**
     * @return the number of connected clients
     * @throws RemoteException
     */
    @Override
    public int getClientListNumber() {
        return clientList.size();
    }


    @Override
    public String getIPAddress() {
        return ipAddress;
    }

    @Override
    public int remoteGetPort() {
        return getPort();
    }

    @Override
    public String getUser() {
        return "SERVER";
    }

    @Override
    public Event getCurrMessage() throws RemoteException {
        return currMessage;
    }

    /**
     * SeverInterface's methods Implementations
     */

    /**
     * It start the server, taking the parameters he needs from the NetConfiguration class.
     */
    @Override
    public void runServer() {

        try {
            RemoteInterface serverStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, portRMI);
            Registry registry = LocateRegistry.createRegistry(portRMI);
            registry.rebind("RMIServer:"+portRMI, serverStub);

        } catch (ExportException e) {
            try {
                UnicastRemoteObject.unexportObject(this, false);
                RemoteInterface serverStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, portRMI);
                Registry registry = LocateRegistry.createRegistry(portRMI);
                registry.rebind("RMIServer:"+portRMI, serverStub);
            } catch (RemoteException exc) {
                CustomLogger.logException(exc);
            }
        } catch (RemoteException exception) {
            CustomLogger.logException(exception);
        }

    }

    /**
     * try a clientConnectionGuard() and return all the disconnected clients from the last call
     * @return
     */
    @Override
    public ArrayList<Event> ping() {
        ArrayList<Event> currentDisconnectedClients = disconnectedClients;
        try{
            clientConnectionGuard();
            return currentDisconnectedClients;
        }catch (RemoteException disconnected){
            currentDisconnectedClients = disconnectedClients;
            return currentDisconnectedClients;
        }

    }

    /**
     * This method remove the selected client (given by username) from the clientList and return the Event to handle it.
     * @param user is the disconnected client's user;
     * @return a DisconnectedEvent
     */
    @Override
    public Event disconnectClient(String user) {
        for (RemoteInterface currClient: clientList) {
            try{
                if(currClient.getUser().equals(user)){
                    clientList.remove(currClient);
                }

            }catch (RemoteException alreadyDisconnected){
                String disconnectedUser = clientUserOrder.get(clientList.indexOf(currClient));
                clientUserOrder.remove(clientList.indexOf(currClient));
                clientList.remove(currClient);
                return new DisconnectedEvent(disconnectedUser);
            }
        }
        return new DisconnectedEvent(user);

    }

    /**
     * if there are 5 RMIClient connected, the game could start and no more client could connect;
     */
    @Override
    public void acceptClient() {
        if (clientList.size() == 5) {
            gameCouldStart();
        }
    }


    @Override
    public void sendBroadcast(Event message) {
        try {
            remoteSendBroadcast(message);
        } catch (RemoteException rmtException) {
            CustomLogger.logException(rmtException);
        }
    }

    @Override
    public void shutDown() {
        try {
            unexportObject(this,false);
        } catch (RemoteException e) {

        }

    }

    @Override
    public void gameCouldStart() {
        gameCouldStart = true;
    }

    @Override
    public void updateUsername(String username, String newUser) {
        CustomTimer timer = new CustomTimer(1);
        timer.start();
        while (timer.isAlive()){
        }
    }

    /**
     * RemoteInterface's methods implementations
     */

    /**
     * Called by a client during the connection
     *
     * @param remotePort      is the client port
     * @param remoteIPAddress is the client IP address
     */
    @Override
    public void acceptRemoteClient(int remotePort, String remoteIPAddress, String bindName) {
        if (clientList.size() < 5) {
            try {
                if(remoteIPAddress.equals("localhost")){
                    remoteIPAddress = ipAddress;
                }
                Registry currRegistry = LocateRegistry.getRegistry(remoteIPAddress, remotePort);
                //clientRegistries.add(currRegistry);
                RemoteInterface remoteClient = (RemoteInterface)currRegistry.lookup(bindName);
                clientList.add(remoteClient);
                clientUserOrder.put(clientList.size()-1,remoteClient.getUser());
            } catch (RemoteException | NotBoundException e) {
                CustomLogger.logException(e);
            }

        }
    }

    /**
     * this method check that the clientList's clients are still connected calling a method on each one,
     *  if this call throw an exception, the method update the disconnectedClients Event ArrayList;
     * @throws RemoteException
     */
    @Override
    public synchronized void  clientConnectionGuard() throws RemoteException {
        for (RemoteInterface currClient : clientList) {
            try {
                currClient.clientConnectionGuard();
            }catch (RemoteException disconnected){
                String disconnectedUser = clientUserOrder.get(clientList.indexOf(currClient));
                clientList.remove(currClient);
                clientUserOrder.remove(clientList.indexOf(currClient));
                disconnectedClients.add(new DisconnectedEvent(disconnectedUser));
            }
        }
    }

    /**
     *  Handle the RMI implementation of sendMessage()
     * @param message is the message to send
     * @throws RemoteException if the client is unreachable
     */
    @Override
    public synchronized void remoteSendMessage(Event message) throws RemoteException {
        currMessage = message;
        for (int i = clientList.size() - 1; i >= 0; i--) {
            RemoteInterface currentClient = clientList.get(i);

            if (currentClient.getUser().equals(message.getUser())) {
                (clientList.get(i)).remoteSetCurrEvent(this);
                remoteCleanCurrEvent();
                return;
            }
        }
    }


    @Override
    public void remoteCleanCurrEvent() throws RemoteException {
        currMessage = null;
    }

    /**
     * is called during the remoteSendMessage() from a RMIClient
     * @param message is the received message
     * @throws RemoteException
     */
    @Override
    public synchronized void remoteSetCurrEvent(RemoteInterface remoteImplementation) throws RemoteException {
        try{
            while(currMessage!=null) wait(50);
        }catch (InterruptedException exc){
            CustomLogger.logException(exc);
        }
        currMessage = remoteImplementation.getCurrMessage();
    }

    @Override
    public Event remoteGetCurrEvent() throws RemoteException {
        return currMessage;
    }

    /**
     * This method set, on each RemoteClient, the current message and wait for 100 milliSeconds between each setting
     * @param message is the Event that must be sent
     * @throws RemoteException if couldn't reach the server
     */
    @Override
    public synchronized void remoteSendBroadcast(Event message) throws RemoteException {
        currMessage = message;
        for (RemoteInterface client : clientList) {
            client.remoteSetCurrEvent(this);
        }
        remoteCleanCurrEvent();
    }

    @Override
    public synchronized Event remoteListenMessage() throws RemoteException {
        Event listenedMessage = currMessage;
        if (currMessage != null){
            currMessage = null;
        }
        return listenedMessage;
    }

    /**
     * NetworkHandler's methods Implementations
     */

    /**Is the RMI implementation of listenMessage()
     *  start the timer and wait for an update on this currMessage attribute
     * @return the listened message
     */
    @Override
    public Event listenMessage() {
        Event currEvent = null;
        CustomTimer timer = new CustomTimer(NetConfiguration.roundTimer);
        timer.start();
        Logger log = Logger.getLogger("Logger");
        log.info("Started the round countdown!\nPlayer disconnected in " + NetConfiguration.roundTimer + " seconds.\n");
        while (currEvent == null&&timer.isAlive()) {

            try {
                currEvent = remoteListenMessage();

            } catch (RemoteException rmtException) {
                CustomLogger.logException(rmtException);
            }
        }
        return currEvent;
    }


    @Override
    public void sendMessage(Event message) {
        try {
            remoteSendMessage(message);
        } catch (RemoteException rmtException) {
            for (String currClient :getClientList()) {
                int i = getClientList().indexOf(currClient);
                if(currClient.equals(message.getUser())){
                    clientList.remove(i);
                }
            }
                disconnectedClients.add(new DisconnectedEvent(currMessage.getUser()));
            CustomLogger.logException(rmtException);
        }
    }


}
