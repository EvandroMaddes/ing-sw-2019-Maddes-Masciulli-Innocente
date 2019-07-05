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

    /**
     * handle the connection between two remote hosts
     * @param remotePort is the server Port, from the NetConfiguration Class
     * @param remoteIPAddress is the ip address of the host to which is trying the connection
     * @throws RemoteException if couldn't connect properly
     */
    void acceptRemoteClient( int remotePort, String remoteIPAddress, String bindName) throws RemoteException;

    /**
     * this method check the livability of a host with a continue lifeline
     * @throws RemoteException
     */
    void clientConnectionGuard() throws RemoteException;

    /**
     * Implementation with RMI of the sendMessage()
     * @param message
     * @throws RemoteException
     */
    void remoteSendMessage(Event message) throws RemoteException;

    /**
     * is called during the remoteSendMessage() from a RMIClient, wait for a latch
     * @param remoteImplementation is the client remote implementation in which will be retrieved the message
     * @throws RemoteException if client isn't reachable
     */
    void remoteSetCurrEvent(RemoteInterface remoteImplementation) throws RemoteException;

    /**
     * clean the currMessage, is called by server when the client message is retrieved
     * @throws RemoteException
     */
    void remoteCleanCurrEvent() throws RemoteException;

    /**
     * This method send a message to each of the connected client
     * @param message is the Event that must be sent
     * @throws RemoteException if couldn't reach the server
     */
    void remoteSendBroadcast(Event message) throws RemoteException;

    /**
     * update a listened message
     * @return the listened message, null if the currMessage isn't updated
     * @throws RemoteException
     */
    Event remoteListenMessage() throws RemoteException;

    /**
     *  Getter method:
     * @return the number of client connected to the server
     * @throws RemoteException if couldn't get the resource properly
     */
    int getClientListNumber() throws  RemoteException;

    /**
     * Getter method:
     * @return currMessage
     * @throws RemoteException if couldn't get the resource properly
     */
    Event getCurrMessage() throws RemoteException;

    /**
     * Getter method:
     * @return the host username
     * @throws RemoteException
     */
    String getUser() throws RemoteException;
}
