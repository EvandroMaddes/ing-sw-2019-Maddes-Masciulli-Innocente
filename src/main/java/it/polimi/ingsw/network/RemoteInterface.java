package it.polimi.ingsw.network;

import it.polimi.ingsw.event.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * This interface is implemented by RMIServer and RMIClient
 * its methods handle the remote connection beetwen Lobby and Client(the latter will not implements the Broadcast)
 */
public interface RemoteInterface extends Remote {
    void acceptRemoteClient( int remotePort, String remoteIPAddress, String bindName) throws RemoteException;
    void clientConnectionGuard() throws RemoteException;
    void remoteSendMessage(Event message) throws RemoteException;

    void remoteSetCurrEvent(RemoteInterface remoteImplementation) throws RemoteException;
    void remoteCleanCurrEvent() throws RemoteException;
    Event remoteGetCurrEvent() throws RemoteException;
    void remoteSendBroadcast(Event message) throws RemoteException;
    Event remoteListenMessage() throws RemoteException;

    int getClientListNumber() throws  RemoteException;
    Event getCurrMessage() throws RemoteException;
    int remoteGetPort() throws RemoteException;
    String getIPAddress() throws RemoteException;
    String getUser() throws RemoteException;
}
