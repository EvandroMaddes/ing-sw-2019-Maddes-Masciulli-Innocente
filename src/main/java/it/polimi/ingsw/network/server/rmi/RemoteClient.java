package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.event.ErrorEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.server.ServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RemoteClient extends UnicastRemoteObject implements NetworkHandler, RemoteInterface, ServerInterface {
    private ArrayList<RemoteInterface> clientList;
    private Event currMessage;


    /**
     *
     * @throws RemoteException
     */
    public RemoteClient() throws RemoteException{
        clientList = new ArrayList<>();
    }


    @Override
    public void runServer() {
       try{
           Naming.rebind("RMIServer", this);
       }catch(RemoteException rExc) {

        }
       catch(MalformedURLException mURLExc){

       }

    }

    @Override
    public void acceptClient(ClientInterface currentClient) {
            acceptRemoteClient((RemoteInterface)currentClient);

    }

    @Override
    public void acceptRemoteClient(RemoteInterface remoteClient) {
        if(clientList.size()<5) {
            clientList.add(remoteClient);
        }
    }

    @Override
    public void sendBroadcast(Event message) {
        try{
            remoteSendMessage(message);
        }catch (RemoteException rmtException){

        }
    }

    @Override
    public void shutDown() {
        try{
            Naming.unbind("RMIServer");
        }catch(RemoteException rmtException){

        }
        catch (NotBoundException notBoundException){

        }
        catch (MalformedURLException mURLException){

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
            if(((RMIClient)clientList.get(i)).getUser().equals(message.getUser())){
                (clientList.get(i)).remoteSetCurrEvent(message);
                clientList.get(i).remoteListenMessage();
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
            message.setUser(((RMIClient)client).getUser());
            remoteSendMessage(message);
        }
    }

    @Override
    public synchronized Event remoteListenMessage() throws RemoteException {
        return currMessage;
    }

    @Override
    public Event listenMessage() {
        try {
            return remoteListenMessage();
        }catch (RemoteException rmtException){
            return new ErrorEvent(currMessage.getUser());
        }
    }

    @Override
    public void sendMessage(Event message) {
       try {
           remoteSendMessage(message);
       }catch (RemoteException rmtException){

       }
    }
}
