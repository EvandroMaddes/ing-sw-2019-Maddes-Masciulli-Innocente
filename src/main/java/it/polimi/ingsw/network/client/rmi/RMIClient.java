package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.event.ErrorEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.custom_exceptions.CustomConnectException;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.net.ConnectException;
import java.net.InetAddress;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;


/**
 * The RMI implementation of ClientInterface
 *
 * @author Francesco Masciulli
 */
public class RMIClient extends UnicastRemoteObject implements ClientInterface, RemoteInterface {
    /**
     * Is the RMIServer RemoteInterface, transient because it couldn't be send on network
     */
    private transient RemoteInterface server;
    /**
     * Is the name used in binding
     */
    private String bindName;
    /**
     * Is the client username
     */
    private String user;
    /**
     * Is the client IP address
     */
    private String clientIPAddress;
    /**
     * Is the client port number
     */
    private int port;
    /**
     * is the server IP address
     */
    private String serverIPAddress;
    /**
     * Is the RMIServer port numver
     */
    private int serverPort;
    /**
     * Is the last set message
     */
    private Event currMessage;
    /**
     * This boolean is true until the client is connected to the server
     */
    private boolean connected = false;


    /**
     * Create a UnicastRemoteObject with the given port, set username, server and client IpAddress and port;
     * try to reach the server
     *
     * @param user            is client username
     * @param port            is the client port
     * @param serverIPAddress is the server IP address
     * @throws RemoteException  because extend UnicastRemoteObject
     * @throws ConnectException if couldn't reach the server
     */
    public RMIClient(String user, int port, String serverIPAddress) throws RemoteException, ConnectException {
        super(port);
        this.port = port;
        this.user = user;
        this.serverIPAddress = serverIPAddress;
        serverPort = NetConfiguration.RMISERVERPORTNUMBER;
        try {
            this.clientIPAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            CustomLogger.logException(e);
        }

        connectClient();
    }

    /**
     * Getter method:
     *
     * @return attribute connected value
     */
    @Override
    public boolean isConnected() {
        return connected;
    }

    /**
     * This method reconnect the client to the requested port, on the same server
     */
    @Override
    public void reconnectClient() {
        try {
            acceptRemoteClient(serverPort, serverIPAddress, "RMIServer:" + serverPort);
            server.acceptRemoteClient(port, clientIPAddress, bindName);

        } catch (Exception e) {
            CustomLogger.logException(e);
        }
    }

    /**
     * Setter method:
     * set serverPort attribute
     *
     * @param serverPort is the server port number
     */
    @Override
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * RemoteInterface Getter methods Implementations
     */

    /**
     * Getter method:
     *
     * @return currMessage
     */
    @Override
    public Event getCurrMessage() {
        return currMessage;
    }

    /**
     * Getter method:
     *
     * @return the number of client connected to the server
     * @throws RemoteException if couldn't reach the server properly
     */
    @Override
    public int getClientListNumber() throws RemoteException {
        return (server.getClientListNumber());
    }

    /**
     * Getter method:
     *
     * @return the client username
     */
    @Override
    public String getUser() {
        return user;
    }


    //ClientInterface's methods Implementations

    /**
     * this method handle the lookup and the creation of the client RemoteRegistry
     * calling the method on the server to add the Remote reference to this last created registry;
     *
     * @throws ConnectException if couldn't connect properly
     */
    @Override
    public void connectClient() throws ConnectException {
        bindName = "RMIClient:" + clientIPAddress + ":" + port;

        try {
            acceptRemoteClient(serverPort, serverIPAddress, "RMIServer:" + serverPort);
        } catch (RemoteException unknownHost) {
            disconnectClient();
            throw new CustomConnectException();
        }

        try {
            RemoteInterface clientStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry clientRegistry = LocateRegistry.createRegistry(port);
            clientRegistry.rebind(bindName, clientStub);
            server.acceptRemoteClient(port, clientIPAddress, bindName);
            connected = true;
        } catch (ExportException alreadyExported) {
            try {
                disconnectClient();
                RemoteInterface clientStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, 0);
                Registry clientRegistry = LocateRegistry.createRegistry(port);
                clientRegistry.rebind(bindName, clientStub);
                server.acceptRemoteClient(port, clientIPAddress, bindName);
                connected = true;
            } catch (Exception exc) {
                CustomLogger.logException(exc);
                throw new CustomConnectException();
            }
        } catch (RemoteException exc) {
            CustomLogger.logException(exc);
            throw new CustomConnectException();

        }

    }

    /**
     * this method update the username after a modification request
     *
     * @param user        is the old username
     * @param newUsername is the updated username
     */
    @Override
    public void changeUsername(String user, String newUsername) {
        if (!user.equals(newUsername)) {
            this.user = newUsername;
        }
    }

    /**
     * this method handle the RMIClient disconnection
     */
    @Override
    public void disconnectClient() {
        try {
            connected = false;
            UnicastRemoteObject.unexportObject(this, false);
        } catch (NoSuchObjectException exc) {
            return;
        }
    }

    /**
     * RemoteInterface's methods Implementations
     */

    /**
     * connect the client to the server
     *
     * @param remotePort      is the server Port, from the NetConfiguration Class
     * @param remoteIPAddress is the remote host ip address
     * @throws RemoteException if couldn't connect a client properly
     */
    @Override
    public void acceptRemoteClient(int remotePort, String remoteIPAddress, String bindName) throws RemoteException {
        try {
            Registry serverRegistry = LocateRegistry.getRegistry(remoteIPAddress, remotePort);
            server = (RemoteInterface) serverRegistry.lookup(bindName);
        } catch (Exception e) {
            CustomLogger.logException(e);
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * this method is call by server to check the client livability
     *
     * @throws RemoteException if couldn't be called by remote
     */
    @Override
    public void clientConnectionGuard() throws RemoteException {

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
     * Set the currMessage, is called by server during a remoteSendMessage
     *
     * @param remoteImplementation is the implementation from which the message must be retrieved
     * @throws RemoteException if couldn't reach the server
     */
    @Override
    public synchronized void remoteSetCurrEvent(RemoteInterface remoteImplementation) throws RemoteException {
        try {
            while (currMessage != null) wait(50);
        } catch (InterruptedException exc) {
            CustomLogger.logException(exc);
            Thread.currentThread().interrupt();
        }

        currMessage = remoteImplementation.getCurrMessage();
    }

    /**
     * remoteSendMessage implementation
     *
     * @param message the message that must be sent
     * @throws RemoteException if couldn't send properly
     */
    @Override
    public void remoteSendMessage(Event message) throws RemoteException {
        currMessage = message;
        server.remoteSetCurrEvent(this);
        remoteCleanCurrEvent();
    }


    /**
     * wasn't implemented, just the server could send Broadcast;
     * it could be implemented, in the future, to send broadcast defined messages to each client
     *
     * @param message could be a client broadcast message
     * @throws RemoteException if couldn't be called by remote
     */
    @Override
    public void remoteSendBroadcast(Event message) throws RemoteException {

    }


    /**
     * remoteListenMessage implementation
     *
     * @return the listened message, null if the currMessage isn't updated
     * @throws RemoteException if couldn't be called by remote
     */
    @Override
    public Event remoteListenMessage() throws RemoteException {
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
     * ClientInterface's sendMessage implementation
     *
     * @param message is the message that must be sent
     */
    @Override
    public void sendMessage(Event message) {
        try {
            remoteSendMessage(message);
        } catch (RemoteException rmtException) {
            disconnectClient();
        }
    }

    /**
     * ClientInterface's listenMessage implementation
     *
     * @return the listened message, null if no message was retrieved
     */
    @Override
    public Event listenMessage() {
        try {
            return remoteListenMessage();

        } catch (RemoteException rmtException) {
            return new ErrorEvent(user);
        }
    }
}
