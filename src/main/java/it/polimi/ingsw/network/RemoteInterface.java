package it.polimi.ingsw.network;

import it.polimi.ingsw.event.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Francesco Masciulli
 * This interface is implemented by RemoteClient and RMIClient
 * its methods handle the remote connection beetwen Server and Client(the latter will not implements the Broadcast)
 */
public interface RemoteInterface extends Remote {
    void remoteSendMessage(Event message) throws RemoteException;
    void remoteSendBroadcast(Event message) throws RemoteException;
    //potrebbe returnare un Event?
    void remoteListenMessage() throws RemoteException;
}
