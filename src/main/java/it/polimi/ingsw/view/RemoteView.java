package it.polimi.ingsw.view;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.serverviewevent.ServerClientEvent;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.utils.NetConfiguration;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This abstract class represent the RemoteView, implemented on each Client
 *
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
public abstract class RemoteView implements RemoteViewInterface {

    /**
     * Is the client username
     */
    private String user;
    /**
     * Is the final client Logger, used for errors notification
     */
    private static final Logger log = Logger.getLogger("ClientLogger");
    /**
     * Is the chosen ClientInterface implementation, handling the network connection
     */
    private ClientInterface clientImplementation;
    /**
     * This boolean indicate if the client is connected to the server
     */
    private boolean connected;
    /**
     * Is the last listened message
     */
    private Event currentMessage;
    /**
     * This final string is used as addressee for broadcast messages
     */
    protected static final String BROADCAST_STRING = "BROADCAST";

    /**
     * Getter method:
     *
     * @return the client username
     */
    public String getUser() {
        return user;
    }

    /**
     * Setter method: set the client user value
     *
     * @param user is the set username
     */
    public void setUser(String user) {
        this.user = user;
    }


    /**
     * This method handle the life-cycle of the client: from connection to messages receiving and elaborating, until the disconnection
     */
    public void startInterface() {

        connected = false;
        String[] userInput = gameInit();
        String username = userInput[0];
        String connectionType = userInput[1];
        String serverIPAddress = userInput[2];
        try {
            if (connectionType.equalsIgnoreCase(NetConfiguration.ConnectionType.RMI.name())) {

                clientImplementation = new RMIClient(username,
                        NetConfiguration.RMISERVERPORTNUMBER + 1000 + new Random().nextInt(1000), serverIPAddress);

            } else {
                clientImplementation = new SocketClient(username, serverIPAddress);
            }
            connected = true;
        } catch (ConnectException | RemoteException e) {
            log.warning("Can't reach the Lobby!\nClosing the app..");
            CustomLogger.logException(e);
        }

        while (connected) {
            messageControl();
        }

        try {
            clientImplementation.disconnectClient();
        } catch (NullPointerException nullPointer) {
            log.warning("Client implementation doesn't exist, nothing to disconnect..");
        } catch (Exception closingException) {
            log.warning("Can't close correctly the client connection!");
            CustomLogger.logException(closingException);
        } finally {
            log.info("Shutting-down the game.");
        }
    }

    /**
     * This private method handle the message receiving, elaborating and answering to the server
     */
    private void messageControl() {
        boolean waiting = clientImplementation.isConnected();

        while (waiting) {
            try {
                currentMessage = clientImplementation.listenMessage();
                if (isGameSet() && !currentMessage.getUser().equals(BROADCAST_STRING)) {
                    printScreen();
                }
                currentMessage = ((ClientEvent) currentMessage).performAction(this);
                //invia il messaggio solo se non è Update -> BROADCAST
                if (!currentMessage.getUser().equals(BROADCAST_STRING)) {
                    clientImplementation.sendMessage(currentMessage);
                }


                waiting = false;
            } catch (NullPointerException e) {

                waiting = true;
            } catch (ClassCastException e) {
                Event returnedEvent = ((ServerClientEvent) currentMessage).performAction(clientImplementation, this);
                if (returnedEvent != null) {
                    clientImplementation.sendMessage(returnedEvent);
                }
                waiting = true;
            } catch (Exception e) {
                waiting = false;
                connected = false;
                CustomLogger.logException(e);
                log.info("Lobby was disconnected!");
            }
            connected = clientImplementation.isConnected();
        }
    }


    /**
     * This method handle the client disconnection
     */
    public void disconnect() {
        connected = false;
        currentMessage = null;
        try {
            clientImplementation.disconnectClient();
        } catch (Exception e) {
            log.severe("Unable to disconnect client: ");
            CustomLogger.logException(e);
        }
    }
}
