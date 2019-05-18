package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.event.ErrorEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetConfiguration;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.utils.CustomLogger;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;


public class RMIClient extends UnicastRemoteObject implements ClientInterface, NetworkHandler, RemoteInterface, Runnable{
    private transient RemoteInterface server;
    private transient Registry clientRegistry;
    private transient Registry serverRegistry;
    private String bindName;
    private String user;
    private String clientIPAddress;
    private int port;
    private String serverIPAddress;
    private Event currMessage;



    /**
     * we need the RemoteException in the constructor
     * @throws RemoteException
     */
    public RMIClient(String user, int port, String serverIPAddress) throws RemoteException{
        super(port);
        this.port= port;
        this.user=user;
        this.serverIPAddress = serverIPAddress;
        try{
            this.clientIPAddress = InetAddress.getLocalHost().getHostAddress();
        }catch(Exception e){
            CustomLogger.logException(e);
        }

        connectClient();
    }

    /**
     * RemoteInterface Getter methods Implementations
     */

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public Event getCurrMessage(){
        return currMessage;
    }

    @Override
    public String getIPAddress(){
        return clientIPAddress;
    }

    @Override
    public int getPort(){
        return port;
    }

    @Override
    public int getClientListNumber() throws RemoteException {
        return (server.getClientListNumber());
    }

    @Override
    public String getUser(){
        return user;
    }


    /**
     * ClientInterface's methods Implementations
     */
    /**
     * this method handle the lookup and the creation of the client RemoteRegistry
     * calling the method on the server to add the Remote reference to this last created registry;
     */
    @Override
    public void connectClient() {
        try {

            serverRegistry = LocateRegistry.getRegistry(serverIPAddress, NetConfiguration.RMISERVERPORTNUMBER);
            RemoteInterface remoteServer = (RemoteInterface) serverRegistry.lookup("RMIServer");
            acceptRemoteClient(remoteServer);
            int clientNumber = server.getClientListNumber()+1;
            clientRegistry = LocateRegistry.createRegistry(port);
            clientRegistry.rebind("RMIClient"+clientNumber,this);
            bindName = "RMIClient"+clientNumber;
            server.acceptRemoteClient(this);
            //run();
        }catch(RemoteException|NotBoundException exc){
            CustomLogger.logException(exc);
        }

    }

    @Override
    public void disconnectClient() throws Exception {
            clientRegistry.unbind(bindName);
    }

    /**
     * RemoteInterface's methods Implementations
     */

    /**
     * must accept the Server Binding;
     * @param remoteClient is the Remote Server, looked in the constructor
     * @throws RemoteException if it could not connect to the Server
     */
    @Override
    public void acceptRemoteClient(RemoteInterface remoteClient) throws RemoteException {
            server = remoteClient;
    }

    @Override
    public void remoteSetCurrEvent(Event message) throws RemoteException {
        currMessage=message;
    }

    @Override
    public void remoteSendMessage(Event message) throws RemoteException{
            server.remoteSetCurrEvent(message);
            //server.remoteListenMessage();
    }


    /**
     * must be not implemented, just the server could send Broadcast
     * @param message
     * @throws RemoteException
     */
    @Override
    public void remoteSendBroadcast(Event message) throws RemoteException{

    }



    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public Event remoteListenMessage() throws RemoteException{
        Event listenedMessage = currMessage;
        currMessage= null;
        return listenedMessage;
    }

    /**
     * NetworkHandler's methods Implementations
     */

    /**
     *
     * @param message
     */
    @Override
    public void sendMessage(Event message) {
        try {
            remoteSendMessage(message);
        }
        catch(RemoteException rmtException){

        }
    }

    @Override
    public Event listenMessage() {
        try{
            return remoteListenMessage();

        }catch (RemoteException rmtException){
            return new ErrorEvent(user);
        }
    }

    @Override
    public void run() {

    }
}
