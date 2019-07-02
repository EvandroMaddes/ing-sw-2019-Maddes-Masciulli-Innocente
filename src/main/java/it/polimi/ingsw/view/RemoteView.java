package it.polimi.ingsw.view;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.server_view_event.ServerClientEvent;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.cli.graph.Color;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.logging.Logger;

public abstract class RemoteView implements RemoteViewInterface{

    private String user;
    private Event toVirtualView;
    private static final Logger log = Logger.getLogger("CLILogger");
    private ClientInterface clientImplementation;
    private boolean connected;
    private Event currentMessage;
    public static final String BROADCASTSTRING = "BROADCAST";


    public ClientInterface getClientImplementation() {
        return clientImplementation;
    }

    public void disconnect(){
        connected = false;
        currentMessage = null;
        try {
            clientImplementation.disconnectClient();
        }catch (Exception e){
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+
                        "Unable to disconnect client: ");
            e.printStackTrace();
        }
    }

    public void startInterface() {

        connected = false;
        String[] userInput = gameInit();
        String user = userInput[0];
        String connectionType = userInput[1];
        String serverIPAddress = userInput[2];
        try {
            if (connectionType.equalsIgnoreCase(NetConfiguration.ConnectionType.RMI.name())) {

                clientImplementation = new RMIClient(user,
                        NetConfiguration.RMISERVERPORTNUMBER +1000 + new Random().nextInt(1000), serverIPAddress);

            } else {
                clientImplementation = new SocketClient(user, serverIPAddress);
            }
            connected = true;
        }
        catch(ConnectException | RemoteException e){
            log.warning("Can't reach the Lobby!\nClosing the app..");
            CustomLogger.logException(e);
        }

        //todo sempre connected finchè non si sconnette il server
        while(connected) {
            boolean waiting = clientImplementation.isConnected();

            while (waiting) {
                try {
                    currentMessage = clientImplementation.listenMessage();
                    log.info("Listened message for:\t".concat(currentMessage.getUser()).concat("\n\t"+currentMessage.toString()));
                    if(isGameSet()&&!currentMessage.getUser().equals("BROADCAST")){
                        printScreen();
                    }
                    //todo i ModelUpdate, eseguendo performAction ritornano null?, non un Event;
                    currentMessage = ((ClientEvent)currentMessage).performAction(this);
                    //invia il messaggio solo se non è Update -> BROADCAST
                    if(!currentMessage.getUser().equals("BROADCAST")){
                        clientImplementation.sendMessage(currentMessage);
                    }


                    waiting = false;
                } catch (NullPointerException e) {

                    waiting = true;
                }
                catch (ClassCastException e){
                    Event returnedEvent = ((ServerClientEvent)currentMessage).performAction(clientImplementation,this);
                    if(returnedEvent != null){
                        clientImplementation.sendMessage(returnedEvent);
                    }
                    waiting = true;
                }
                catch (Exception e){
                    waiting = false;
                    connected = false;
                    CustomLogger.logException(e);
                    log.info("Lobby was disconnected!");
                }
                connected = clientImplementation.isConnected();
            }
        }

        try {
            clientImplementation.disconnectClient();
        }catch (NullPointerException nullPointer){
            log.warning("Client implementation doesn't exist, nothing to disconnect..");
        }
        catch (Exception closingException){
            log.warning("Can't close correctly the client connection!");
            CustomLogger.logException(closingException);
        }
        finally {
            log.info("Shutting-down the game.");
        }
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
