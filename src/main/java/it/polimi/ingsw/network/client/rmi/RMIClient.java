package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.event.ErrorEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;


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

            acceptRemoteClient(NetConfiguration.RMISERVERPORTNUMBER, serverIPAddress);
            int clientNumber = server.getClientListNumber()+1;
            RemoteInterface clientStub = (RemoteInterface) UnicastRemoteObject.exportObject(this,0);
            clientRegistry = LocateRegistry.createRegistry(port);
            clientRegistry.rebind("RMIClient"+clientNumber,clientStub);
            bindName = "RMIClient"+clientNumber;
            server.acceptRemoteClient(port,clientIPAddress);
            //run();
        }
        catch (ExportException alreadyExported){
            try {
                UnicastRemoteObject.unexportObject(this,false);
                int clientNumber = server.getClientListNumber()+1;
                RemoteInterface clientStub = (RemoteInterface) UnicastRemoteObject.exportObject(this,clientNumber);
                clientRegistry = LocateRegistry.createRegistry(port);
                clientRegistry.rebind("RMIClient"+clientNumber,clientStub);
                bindName = "RMIClient"+clientNumber;
                server.acceptRemoteClient(port,clientIPAddress);
            }catch(Exception exc){
                CustomLogger.logException(exc);
            }
        }
        catch(RemoteException exc){
            CustomLogger.logException(exc);
        }

    }

    @Override
    public void disconnectClient() throws Exception {
            clientRegistry.unbind(bindName);
    }


    @Override
    public void changeUsername(String user, String newUsername) {
        if(!user.equalsIgnoreCase(newUsername)){
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
    public void acceptRemoteClient( int remotePort, String remoteIPAddress) throws RemoteException {
       try{
           serverRegistry = LocateRegistry.getRegistry(serverIPAddress, remotePort);
           server = (RemoteInterface) serverRegistry.lookup("RMIServer");
       }catch (Exception e){
           CustomLogger.logException(e);
       }
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

    //todo NON SERVE THREAD??
    @Override
    public void run() {

    }
}
