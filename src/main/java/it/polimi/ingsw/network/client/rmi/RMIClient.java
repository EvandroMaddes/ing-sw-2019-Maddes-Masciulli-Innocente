package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIClient extends UnicastRemoteObject implements ClientInterface, NetworkHandler, RemoteInterface, Runnable{

    private RemoteInterface server;

    /**
     * we need the RemoteException
     * @throws RemoteException
     */
    public RMIClient() throws RemoteException{

    }

    @Override
    public void connectClient() {

    }

    @Override
    public void remoteSendMessage(Event message) throws RemoteException{

    }


    @Override
    public void remoteSendBroadcast(Event message) throws RemoteException{

    }

    @Override
    public void remoteListenMessage() throws RemoteException{

    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void listenMessage() {

    }

    @Override
    public void run() {

    }
}
