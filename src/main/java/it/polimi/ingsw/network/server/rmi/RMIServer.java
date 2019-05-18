package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.event.ErrorEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetConfiguration;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.network.server.socket.SocketServerThread;
import it.polimi.ingsw.utils.CustomLogger;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class RMIServer extends UnicastRemoteObject implements Runnable, RemoteInterface, ServerInterface {
    private ArrayList<RemoteInterface> clientList;
    private ArrayList<Registry> clientRegistries;
    private Event currMessage;
    private transient Registry registry;
    private String ipAddress;
    private boolean gameCouldStart = false;


    /**
     *
     * @throws RemoteException
     */
    public RMIServer() throws RemoteException{
        super(NetConfiguration.RMISERVERPORTNUMBER);
        clientList = new ArrayList<>();
        clientRegistries = new ArrayList<>();
        try{
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        }catch(Exception e){
            CustomLogger.logException(e);
        }
    }

    /**
     * Getter method
     * @return the connected Clients ArrayList
     */
    public ArrayList<String> getClientList() {
        ArrayList<String> clientUserList = new ArrayList<>();
        Iterator iterator = clientList.iterator();
        while(iterator.hasNext()){
            RemoteInterface currRemoteClient = (RemoteInterface) iterator.next();
            try {
                clientUserList.add(currRemoteClient.getUser());
            }catch(RemoteException e){
                CustomLogger.logException(e);
            }
        }
        return clientUserList;
    }


    @Override
    public void run(){
        runServer();
        while(!gameCouldStart){
            acceptClient();
        }

    }


    /**
     * Getter RemoteInterface Implementations
     */


    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public int getClientListNumber() {
        return clientList.size();
    }
    @Override
    public String getIPAddress(){
        return ipAddress;
    }

    @Override
    public int getPort(){
        return NetConfiguration.RMISERVERPORTNUMBER;
    }

    @Override
    public String getUser(){
        return "SERVER";
    }

    @Override
    public Event getCurrMessage() throws RemoteException{
        return currMessage;
    }

    /**
     * SeverInterface's methods Implementations
     */
    @Override
    public void runServer() {
        try{
            registry = LocateRegistry.createRegistry(NetConfiguration.RMISERVERPORTNUMBER);
            registry.rebind("RMIServer",this);

        }catch(RemoteException e) {
            CustomLogger.logException(e);
        }

    }

    /**
     * if there are 5 RMIClient connected, the game could start and no more client could connect;
     */
    @Override
    public void acceptClient() {
           if(clientList.size()==5){
               gameCouldStart();
           }
    }


    @Override
    public void sendBroadcast(Event message) {
        try{
            remoteSendBroadcast(message);
        }catch (RemoteException rmtException){
            CustomLogger.logException(rmtException);
        }
    }

    @Override
    public void shutDown() {
        try{
            registry.unbind("RMIServer");
        }catch(RemoteException|NotBoundException e){
            CustomLogger.logException(e);
        }

    }

    @Override
    public void gameCouldStart() {
        gameCouldStart=true;
    }



    /**
     * RemoteInterface's methods implementations
     */

    /**
     *
     * @param remoteClient
     */
    @Override
    public void acceptRemoteClient(RemoteInterface remoteClient) {
        if(clientList.size()<5) {
            try{
                int clientPort = remoteClient.getPort();
                int clientNumber = getClientListNumber()+1;
                String clientIPAddress = remoteClient.getIPAddress();
                clientRegistries.add(LocateRegistry.getRegistry(clientIPAddress, clientPort));
                remoteClient = (RemoteInterface) clientRegistries.get(getClientListNumber()).lookup("RMIClient"+clientNumber);
                clientList.add(remoteClient);
            }catch(RemoteException|NotBoundException e){
                CustomLogger.logException(e);
            }

        }
    }


    /**
     * it must handle the thread clients
     * @param message
     * @throws RemoteException
     */
    @Override
    public synchronized void remoteSendMessage(Event message) throws RemoteException {
        for(int i=0; i<clientList.size();i++){
            RemoteInterface currentClient =  clientList.get(i);

            if(currentClient.getUser().equals(message.getUser())){
                (clientList.get(i)).remoteSetCurrEvent(message);
                //clientList.get(i).remoteListenMessage();
            }
        }
    }

    @Override
    public void remoteSetCurrEvent(Event message) throws RemoteException {
        this.currMessage=message;
    }

    @Override
    public synchronized void remoteSendBroadcast(Event message) throws RemoteException {
        for (RemoteInterface client: clientList){
         //todo se modelUpdate che cos'è user?
            message.setUser(((RMIClient)client).getUser());
            client.remoteSendMessage(message);
        }
    }

    @Override
    public synchronized Event remoteListenMessage() throws RemoteException {
        return currMessage;
    }

    /**
     * NetworkHandler's methods Implementations
     */

    /**
     *
     * @return
     */
    @Override
    public Event listenMessage() {
       Event currEvent = null;
        while(currEvent == null){
            try {
                currEvent = remoteListenMessage();
            }catch (RemoteException rmtException){
                CustomLogger.logException(rmtException);
            }
        }
        return currEvent;
    }

    @Override
    public void sendMessage(Event message) {
       try {
           remoteSendMessage(message);
       }catch (RemoteException rmtException){
            CustomLogger.logException(rmtException);
       }
    }
}
