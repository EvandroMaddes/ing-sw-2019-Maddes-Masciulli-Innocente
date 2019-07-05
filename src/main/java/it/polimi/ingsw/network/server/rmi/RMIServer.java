package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.viewcontrollerevent.DisconnectedEvent;
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
 *
 * @author Francesco Masciulli
 */
public class RMIServer extends UnicastRemoteObject implements Runnable, RemoteInterface, ServerInterface {
    /**
     * A Multi-Threading safe ArrayList implementation that contains all of the RMIClient connected
     */
    private CopyOnWriteArrayList<RemoteInterface> clientList;
    /**
     * This Map return the RemoteClient username given its index in the clientList
     */
    private Map<Integer, String> clientUserOrder = new HashMap<>();
    /**
     * Is the last set message
     */
    private Event currMessage;
    /**
     * This ArrayList contains a DisconnectedEvent for each disconnected client that wasn't updated
     */
    private ArrayList<Event> disconnectedClients = new ArrayList<>();
    /**
     * Is the server registry
     */
    private transient Registry registry;

    /**
     * Is the server IP address
     */
    private String ipAddress;
    /**
     * Is the RMIServer port number
     */
    private int portRMI;
    /**
     * This boolean is true if the game is started
     */
    private boolean gameCouldStart = false;
    /**
     * this boolean is true if the server could be shut-down
     */
    private boolean gameCouldTerminate = false;
    /**
     * This static String is the binding name prefix
     */
    private static final String BINDINGPREFIX = "RMIServer:";


    /**
     * Constructor:
     * Create an UnicastRemoteObject on default port 1099 (see NetConfiguration) and set server ipAdress
     *
     * @throws RemoteException from UnicastRemoteObject constructor
     */
    public RMIServer() throws RemoteException {
        super(NetConfiguration.RMISERVERPORTNUMBER);
        portRMI = NetConfiguration.RMISERVERPORTNUMBER;
        clientList = new CopyOnWriteArrayList<>();
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            CustomLogger.logException(e);
        }

    }

    /**
     * Overloaded constructor, is given the portNumber given to the UnicastRemoteObject constructor
     * @param portNumber is the port number
     * @throws RemoteException from UnicastRemoteObject constructor
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
    @Override
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
     *
     * @return the port on which the RMIServer is exported
     */
    @Override
    public int getPort() {
        return portRMI;
    }


    /**
     * This method clear the DisconnectedEventList after that the Lobby save it
     */
    public void cleanDisconnectedEventList() {
        for (Event currEvent : disconnectedClients) {
            clientUserOrder.remove(getClientList().indexOf(currEvent.getUser()));
        }
        disconnectedClients.clear();
    }

    /**
     * This method clean the DisconnectedEvent relative for a player
     *
     * @param user is the player Username
     */
    public void cleanDisconnectedEventList(String user) {
        for (Event currEvent : disconnectedClients) {
            if (currEvent.getUser().equals(user)) {
                disconnectedClients.remove(currEvent);
                return;
            }
        }
    }

    /**
     * During the setup, it accept new connection;
     * while running, it check that the connection is alive, until the game could terminate.
     */
    @Override
    public void run() {
        runServer();

        while (!gameCouldTerminate) {
            while (!gameCouldStart) {
                acceptClient();
            }
            try {
                clientConnectionGuard();
            } catch (RemoteException disconnected) {
                CustomLogger.logException(disconnected);
            }
        }

    }


    //Getter RemoteInterface Implementations

    /**
     * Getter method:
     * @return the number of connected clients
     */
    @Override
    public int getClientListNumber() {
        return clientList.size();
    }


    /**
     * Remote getter method
     *
     * @return the "SERVER" string
     */
    @Override
    public String getUser() {
        return "SERVER";
    }

    /**
     * Remote getter method:
     *
     * @return this.currMessage
     * @throws RemoteException if called from remote host and couldn't complete
     */
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
        RemoteInterface serverStub;
        try {
            serverStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, portRMI);
            registry = LocateRegistry.createRegistry(portRMI);
            registry.rebind(BINDINGPREFIX + portRMI, serverStub);

        } catch (ExportException e) {
            try {
                UnicastRemoteObject.unexportObject(this, false);

                serverStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, portRMI);
                registry = LocateRegistry.createRegistry(portRMI);
                registry.rebind(BINDINGPREFIX + portRMI, serverStub);
            } catch (RemoteException exc) {
                CustomLogger.logException(exc);
            }
        } catch (RemoteException exception) {
            CustomLogger.logException(exception);
        }

    }

    /**
     * try a clientConnectionGuard() and return all the disconnected clients from the last call
     *
     * @return the list of DisconnectedEvent, one for each client disconnected from last ping();
     */
    @Override
    public ArrayList<Event> ping() {
        ArrayList<Event> currentDisconnectedClients = disconnectedClients;
        try {
            clientConnectionGuard();
            return currentDisconnectedClients;
        } catch (RemoteException disconnected) {
            currentDisconnectedClients = disconnectedClients;
            return currentDisconnectedClients;
        }

    }

    /**
     * This method remove the selected client (given by username) from the clientList and return the Event to handle it.
     *
     * @param user is the disconnected client's user;
     * @return a DisconnectedEvent
     */
    @Override
    public Event disconnectClient(String user) {
        for (RemoteInterface currClient : clientList) {
            try {
                if (currClient.getUser().equals(user)) {
                    clientUserOrder.remove(clientList.indexOf(currClient));
                    clientList.remove(currClient);
                }

            } catch (RemoteException alreadyDisconnected) {
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

    /**
     * ServerInterface method implementation
     * Send to each client, connected to this implementation, the message given
     *
     * @param message is the message that must be sent
     */
    @Override
    public void sendBroadcast(Event message) {
        try {
            remoteSendBroadcast(message);
        } catch (RemoteException rmtException) {
            CustomLogger.logException(rmtException);
        }
    }

    /**
     * ServerInterface method implementation
     * shut-down the server
     */
    @Override
    public void shutDown() {
        try {
            UnicastRemoteObject.unexportObject(this, false);
            registry.unbind(BINDINGPREFIX + portRMI);
        } catch (RemoteException | NotBoundException e) {
            CustomLogger.logException(e);
        } finally {
            gameCouldTerminate = true;
        }

    }

    /**
     * ServerInterface method implementation
     * Set the game state
     */
    @Override
    public void gameCouldStart() {
        gameCouldStart = true;
    }

    /**
     * ServerInterface method implementation
     * after an Username Modification request, update the username of the respectively client
     *
     * @param username is the old username
     * @param newUser  is the new username
     */
    @Override
    public void updateUsername(String username, String newUser) {
        CustomTimer timer = new CustomTimer(1);
        timer.start();
        while (timer.isAlive()) {
        }
        for (int i = 0; i < clientList.size(); i++) {
            if (clientUserOrder.get(i).equals(username)) {
                clientUserOrder.replace(i, username, newUser);
                return;
            }
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
                if (remoteIPAddress.equals("localhost")) {
                    remoteIPAddress = ipAddress;
                }
                Registry currRegistry = LocateRegistry.getRegistry(remoteIPAddress, remotePort);
                RemoteInterface remoteClient = (RemoteInterface) currRegistry.lookup(bindName);
                clientList.add(remoteClient);
                clientUserOrder.put(clientList.size() - 1, remoteClient.getUser());
            } catch (RemoteException | NotBoundException e) {
                CustomLogger.logException(e);
            }

        }
    }

    /**
     * this method check that the clientList's clients are still connected calling a method on each one,
     * if this call throw an exception, the method update the disconnectedClients Event ArrayList;
     *
     * @throws RemoteException if couldn't be called by remote
     */
    @Override
    public synchronized void clientConnectionGuard() throws RemoteException {
        for (RemoteInterface currClient : clientList) {
            try {
                currClient.clientConnectionGuard();
            } catch (RemoteException disconnected) {
                String disconnectedUser = clientUserOrder.get(clientList.indexOf(currClient));
                clientList.remove(currClient);
                clientUserOrder.remove(clientList.indexOf(currClient));
                disconnectedClients.add(new DisconnectedEvent(disconnectedUser));
            }
        }
    }

    /**
     * Handle the RMI implementation of sendMessage()
     *
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


    /**
     * clean the currMessage, is called by server when the client message is retrieved
     *
     * @throws RemoteException if couldn't be called by remote
     */
    @Override
    public void remoteCleanCurrEvent() throws RemoteException {
        currMessage = null;
    }

    /**
     * is called during the remoteSendMessage() from a RMIClient, wait for a latch
     *
     * @param remoteImplementation is the client remote implementation in which will be retrieved the message
     * @throws RemoteException if client isn't reachable
     */
    @Override
    public synchronized void remoteSetCurrEvent(RemoteInterface remoteImplementation) throws RemoteException {
        try {
            while (currMessage != null) wait(50);
        } catch (InterruptedException exc) {
            CustomLogger.logException(exc);
        }
        currMessage = remoteImplementation.getCurrMessage();
    }


    /**
     * This method set, on each RemoteClient, the current message and wait for 100 milliSeconds between each setting
     *
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

    /**
     * remoteListenMessage implementation
     *
     * @return the listened message, null if the currMessage isn't updated
     * @throws RemoteException if couldn't be called by remote
     */
    @Override
    public synchronized Event remoteListenMessage() throws RemoteException {
        Event listenedMessage = currMessage;
        if (currMessage != null) {
            currMessage = null;
        }
        return listenedMessage;
    }

    /**
     * NetworkHandler's methods Implementations
     */

    /**
     * Is the RMI implementation of listenMessage()
     * start the timer and wait for an update on this currMessage attribute
     *
     * @return the listened message
     */
    @Override
    public Event listenMessage() {
        Event currEvent = null;
        CustomTimer timer = new CustomTimer(NetConfiguration.getRoundTimer());
        timer.start();
        Logger log = Logger.getLogger("Logger");
        log.info("Started the round countdown!\nPlayer disconnected in " + NetConfiguration.getRoundTimer() + " seconds.\n");
        while (currEvent == null && timer.isAlive()) {

            try {
                currEvent = remoteListenMessage();

            } catch (RemoteException rmtException) {
                CustomLogger.logException(rmtException);
            }
        }
        return currEvent;
    }


    /**
     * ServerInterface's sendMessage implementation
     *
     * @param message is the message that must be sent
     */
    @Override
    public void sendMessage(Event message) {
        try {
            remoteSendMessage(message);
        } catch (RemoteException rmtException) {
            for (String currClient : getClientList()) {
                int i = getClientList().indexOf(currClient);
                if (currClient.equals(message.getUser())) {
                    clientList.remove(i);
                }
            }
            disconnectedClients.add(new DisconnectedEvent(currMessage.getUser()));
            CustomLogger.logException(rmtException);
        }
    }


}
