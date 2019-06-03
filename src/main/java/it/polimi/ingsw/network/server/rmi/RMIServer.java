package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.RemoteInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class RMIServer extends UnicastRemoteObject implements Runnable, RemoteInterface, ServerInterface {
    private CopyOnWriteArrayList<RemoteInterface> clientList;
    private ArrayList<Registry> clientRegistries;
    private Event currMessage;
    private transient Registry registry;
    private String ipAddress;
    private boolean gameCouldStart = false;


    /**
     * @throws RemoteException
     */
    public RMIServer() throws RemoteException {
        super(NetConfiguration.RMISERVERPORTNUMBER);
        clientList = new CopyOnWriteArrayList<>();
        clientRegistries = new ArrayList<>();
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            CustomLogger.logException(e);
        }
    }

    /**
     * Getter method
     *
     * @return the connected Clients ArrayList
     */
    public ArrayList<String> getClientList() {
        ArrayList<String> clientUserList = new ArrayList<>();
        Iterator iterator = clientList.iterator();
        while (iterator.hasNext()) {
            RemoteInterface currRemoteClient = (RemoteInterface) iterator.next();
            try {
                clientUserList.add(currRemoteClient.getUser());
            } catch (RemoteException e) {
                CustomLogger.logException(e);
            }
        }
        return clientUserList;
    }


    @Override
    public void run() {
        runServer();
        while (!gameCouldStart) {
            acceptClient();
        }

    }


    /**
     * Getter RemoteInterface Implementations
     */


    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public int getClientListNumber() {
        return clientList.size();
    }

    @Override
    public String getIPAddress() {
        return ipAddress;
    }

    @Override
    public int getPort() {
        return NetConfiguration.RMISERVERPORTNUMBER;
    }

    @Override
    public String getUser() {
        return "SERVER";
    }

    @Override
    public Event getCurrMessage() throws RemoteException {
        return currMessage;
    }

    /**
     * SeverInterface's methods Implementations
     */
    @Override
    public void runServer() {
        try {
            RemoteInterface serverStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, 1099);
            registry = LocateRegistry.createRegistry(NetConfiguration.RMISERVERPORTNUMBER);
            registry.rebind("RMIServer", serverStub);

        } catch (ExportException e) {
            try {
                UnicastRemoteObject.unexportObject(this, false);
                RemoteInterface serverStub = (RemoteInterface) UnicastRemoteObject.exportObject(this, 1099);
                registry = LocateRegistry.createRegistry(NetConfiguration.RMISERVERPORTNUMBER);
                registry.rebind("RMIServer", serverStub);
            } catch (RemoteException exc) {
                CustomLogger.logException(exc);
            }
        } catch (RemoteException exception) {
            CustomLogger.logException(exception);
        }

    }

    /**
     * if there are 5 RMIClient connected, the game could start and no more client could connect;
     */
    @Override
    public void acceptClient() {
        if (clientList.size() == 5) {
            gameCouldStart();
        }
    }


    @Override
    public void sendBroadcast(Event message) {
        try {
            remoteSendBroadcast(message);
        } catch (RemoteException rmtException) {
            CustomLogger.logException(rmtException);
        }
    }

    @Override
    public void shutDown() {
        try {
            registry.unbind("RMIServer");
        } catch (RemoteException | NotBoundException e) {
            CustomLogger.logException(e);
        }

    }

    @Override
    public void gameCouldStart() {
        gameCouldStart = true;
    }

    @Override
    public void updateUsername(String username, String newUser) {

    }

    /**
     * RemoteInterface's methods implementations
     */

    /**
     * is called by a client during the connection
     *
     * @param remotePort      is the client port
     * @param remoteIPAddress is the client IP address
     */
    @Override
    public void acceptRemoteClient(int remotePort, String remoteIPAddress) {
        if (clientList.size() < 5) {
            try {
                int clientNumber = getClientListNumber() + 1;
                Registry currRegistry = LocateRegistry.getRegistry(remoteIPAddress, remotePort);
                clientRegistries.add(currRegistry);
                RemoteInterface remoteClient = (RemoteInterface) currRegistry.lookup("RMIClient" + clientNumber);
                clientList.add(remoteClient);
            } catch (RemoteException | NotBoundException e) {
                CustomLogger.logException(e);
            }

        }
    }


    /**
     * it must handle the thread clients
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public synchronized void remoteSendMessage(Event message) throws RemoteException {
        for (int i = clientList.size() - 1; i >= 0; i--) {
            RemoteInterface currentClient = clientList.get(i);

            if (currentClient.getUser().equals(message.getUser())) {
                (clientList.get(i)).remoteSetCurrEvent(message);
                return;
                //clientList.get(i).remoteListenMessage();
            }
        }
    }

    @Override
    public void remoteSetCurrEvent(Event message) throws RemoteException {
        this.currMessage = message;
    }

    @Override
    public synchronized void remoteSendBroadcast(Event message) throws RemoteException {
        for (RemoteInterface client : clientList) {
            //todo se modelUpdate che cos'è user?
            message.setUser(((RMIClient) client).getUser());
            client.remoteSendMessage(message);
        }
    }

    @Override
    public synchronized Event remoteListenMessage() throws RemoteException {
        Event listenedMessage = currMessage;
        currMessage = null;
        return listenedMessage;
    }

    /**
     * NetworkHandler's methods Implementations
     */

    /**
     * @return
     */
    @Override
    public Event listenMessage() {
        Event currEvent = null;
        while (currEvent == null) {
            try {
                currEvent = remoteListenMessage();
            } catch (RemoteException rmtException) {
                CustomLogger.logException(rmtException);
            }
        }
        return currEvent;
    }

    @Override
    public void sendMessage(Event message) {
        try {
            remoteSendMessage(message);
        } catch (RemoteException rmtException) {
            CustomLogger.logException(rmtException);
        }
    }
}
