package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.RemoteInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteClient extends UnicastRemoteObject implements NetworkHandler, RemoteInterface {
    private RemoteInterface client;

    /**
     *
     * @throws RemoteException
     */
    public RemoteClient() throws RemoteException{

    }

    /**
     * it must handle the thread clients
     * @param message
     * @throws RemoteException
     */
    @Override
    public synchronized void remoteSendMessage(Event message) throws RemoteException {

    }

    @Override
    public synchronized void remoteSendBroadcast(Event message) throws RemoteException {

    }

    @Override
    public synchronized void remoteListenMessage() throws RemoteException {

    }

    @Override
    public void listenMessage() {

    }

    @Override
    public void sendMessage() {

    }
}
