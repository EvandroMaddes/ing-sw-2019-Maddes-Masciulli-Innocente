package it.polimi.ingsw.network;

import it.polimi.ingsw.event.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is implemented by RMIServer and RMIClient
 * its methods handle the remote connection beetwen Lobby and Client(the latter will not implements the Broadcast)
 * @author Francesco Masciulli
 */
public interface RemoteInterface extends Remote {

    void acceptRemoteClient( int remotePort, String remoteIPAddress, String bindName) throws RemoteException;
    void clientConnectionGuard() throws RemoteException;
    void remoteSendMessage(Event message) throws RemoteException;

    void remoteSetCurrEvent(RemoteInterface remoteImplementation) throws RemoteException;
    void remoteCleanCurrEvent() throws RemoteException;
    void remoteSendBroadcast(Event message) throws RemoteException;
    Event remoteListenMessage() throws RemoteException;

    int getClientListNumber() throws  RemoteException;
    Event getCurrMessage() throws RemoteException;
    String getUser() throws RemoteException;
}
