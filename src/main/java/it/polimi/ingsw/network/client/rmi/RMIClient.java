package it.polimi.ingsw.network.client.rmi;

import com.sun.nio.sctp.IllegalUnbindException;
import it.polimi.ingsw.event.ErrorEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIClient extends UnicastRemoteObject implements ClientInterface, NetworkHandler, RemoteInterface, Runnable{

    private RemoteInterface server;
    private String user;
    private Event currMessage;


    /**
     * we need the RemoteException
     * @throws RemoteException
     */
    public RMIClient(String user) throws RemoteException{
        this.user=user;
        connectClient();
    }


    public String getUser() {
        return user;
    }


    @Override
    public void connectClient() {
        try {
            RemoteInterface server = (RemoteInterface) Naming.lookup("rmi://localhost/RMIServer");
            acceptRemoteClient(server);
        }catch(RemoteException rmtException){

        }
        catch (NotBoundException boundingException){

        }
        catch(MalformedURLException mURLException){

        }

    }

    /**
     * not implemented, must accept the Server Binding;
     * @param remoteClient
     * @throws RemoteException
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
            server.remoteListenMessage();
    }


    /**
     * must be not implemented, just the server could send Broadcast
     * @param message
     * @throws RemoteException
     */
    @Override
    public void remoteSendBroadcast(Event message) throws RemoteException{

    }

    @Override
    public Event remoteListenMessage() throws RemoteException{
        return currMessage;
    }

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
