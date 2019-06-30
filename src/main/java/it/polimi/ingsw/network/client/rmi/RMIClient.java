package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.event.ErrorEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.net.ConnectException;
import java.net.InetAddress;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;


public class RMIClient extends UnicastRemoteObject implements ClientInterface, NetworkHandler, RemoteInterface{
    private transient RemoteInterface server;
    //private transient Registry clientRegistry;
    //private transient Registry serverRegistry;
    private String bindName;
    private String user;
    private String clientIPAddress;
    private int port;
    private String serverIPAddress;
    private int serverPort;
    private Event currMessage;
    private boolean connected = false;




    /**
     * we need the RemoteException in the constructor
     * @throws RemoteException
     */
    public RMIClient(String user, int port, String serverIPAddress) throws RemoteException, ConnectException{
        super(port);
        this.port= port;
        this.user=user;
        this.serverIPAddress = serverIPAddress;
        serverPort = NetConfiguration.RMISERVERPORTNUMBER;
        try{
            this.clientIPAddress = InetAddress.getLocalHost().getHostAddress();
        }catch(Exception e){
            CustomLogger.logException(e);
        }

        connectClient();
    }


    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void reconnectClient() {
        try{
            acceptRemoteClient(serverPort,serverIPAddress,"RMIServer:"+serverPort);
            server.acceptRemoteClient(port,clientIPAddress,bindName);

            //disconnectClient();
            //connectClient();
        }catch(Exception e){
            CustomLogger.logException(e);
        }
    }

    @Override
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
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
    public int remoteGetPort(){
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
    public void connectClient() throws ConnectException {
        bindName = "RMIClient:"+clientIPAddress+":"+port;

        try {
            acceptRemoteClient(serverPort, serverIPAddress, "RMIServer:"+serverPort);
        }
        catch(RemoteException unknownHost){
            disconnectClient();
            throw new ConnectException("Couldn't reach the server!");
        }

        try {
            RemoteInterface clientStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry clientRegistry = LocateRegistry.createRegistry(port);
            clientRegistry.rebind(bindName, clientStub);
            server.acceptRemoteClient(port, clientIPAddress, bindName);
            connected = true;
            //run();
        }
        catch (ExportException alreadyExported){
            try {
                disconnectClient();
                //int clientNumber = server.getClientListNumber()+1;
                RemoteInterface clientStub = (RemoteInterface) UnicastRemoteObject.exportObject(this,0);
                Registry clientRegistry = LocateRegistry.createRegistry(port);
                clientRegistry.rebind(bindName,clientStub);
                server.acceptRemoteClient(port,clientIPAddress,bindName);
                connected = true;
            }
            catch(Exception exc){
                CustomLogger.logException(exc);
                throw new ConnectException("Couldn't reach the server!");
            }
        }
        catch(RemoteException exc){
            CustomLogger.logException(exc);
            throw new ConnectException("Couldn't reach the server!");

        }

    }

    @Override
    public void disconnectClient(){
        try {
            connected = false;
            UnicastRemoteObject.unexportObject(this, false);
        }
        catch(NoSuchObjectException exc){
            return;
        }
    }


    @Override
    public void changeUsername(String user, String newUsername) {
        if(!user.equals(newUsername)){
            this.user=newUsername;
        }
    }

    /**
     * RemoteInterface's methods Implementations
     */

    /**
     *
     * @param remotePort is the server Port, from the NetConfiguration Class
     * @param remoteIPAddress
     * @throws RemoteException
     */
    @Override
    public void acceptRemoteClient( int remotePort, String remoteIPAddress, String bindName) throws RemoteException {
       try{
           Registry serverRegistry = LocateRegistry.getRegistry(remoteIPAddress, remotePort);
           server = (RemoteInterface) serverRegistry.lookup(bindName);
       }catch (Exception e){
           CustomLogger.logException(e);
           throw new RemoteException(e.getMessage());
       }
    }

    @Override
    public void clientConnectionGuard() throws RemoteException {

    }

    @Override
    public void remoteCleanCurrEvent() throws RemoteException {
        currMessage = null;
    }

    @Override
    public synchronized void remoteSetCurrEvent(RemoteInterface remoteImplementation) throws RemoteException {
        try{
            while(currMessage!=null) wait(50);
        }catch (InterruptedException exc){
            CustomLogger.logException(exc);
        }

        currMessage=remoteImplementation.getCurrMessage();
    }

    @Override
    public Event remoteGetCurrEvent() throws RemoteException {
        return currMessage;
    }

    @Override
    public void remoteSendMessage(Event message) throws RemoteException{
            currMessage = message;
            server.remoteSetCurrEvent(this);
            remoteCleanCurrEvent();
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
        if(currMessage != null) {
            currMessage = null;
        }
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
            disconnectClient();
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
}
