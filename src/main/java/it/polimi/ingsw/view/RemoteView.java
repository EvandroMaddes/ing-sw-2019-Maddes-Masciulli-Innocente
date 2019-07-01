package it.polimi.ingsw.view;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.server_view_event.ServerClientEvent;
import it.polimi.ingsw.event.view_controller_event.GenericPayChoiceEvent;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.power_ups.Newton;
import it.polimi.ingsw.model.game_components.cards.weapons.TractorBeam;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.cli.graph.CLIGameTrack;
import it.polimi.ingsw.view.cli.graph.CLIMap;
import it.polimi.ingsw.view.gui.DecodeMessage;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.logging.Logger;

public abstract class RemoteView implements RemoteViewInterface {

    private String user;
    private Event toVirtualView;
    private static final Logger log = Logger.getLogger("CLILogger");
    private ClientInterface clientImplementation;
    private boolean connected;
    private Event currentMessage;


    public void startInterface() {
        /**
         * INIZIO PROVA
         */

        CLI cli = new CLI();
      /*  CLIMap map = new CLIMap(2);
        CLIGameTrack  track = new CLIGameTrack();
        track.createGameTrack();
        cli.getDisplay().setMap(map);
        cli.getDisplay().setGameTrack(track);
        cli.addAmmoTileUpdate(1,1,"red","red","red");
        cli.addAmmoTileUpdate(0,0,"red","red","POWERUP");
        cli.addAmmoTileUpdate(0,1,"red","red","POWERUP");
        cli.addAmmoTileUpdate(0,2,"red","red","POWERUP");
        cli.addAmmoTileUpdate(1,0,"red","red","POWERUP");
        cli.addAmmoTileUpdate(1,1,"red","red","POWERUP");
        cli.addAmmoTileUpdate(1,2,"red","red","POWERUP");
        cli.addAmmoTileUpdate(2,0,"red","red","POWERUP");
        cli.addAmmoTileUpdate(2,1,"red","red","POWERUP");
        cli.addAmmoTileUpdate(2,2,"red","red","POWERUP");
        cli.addAmmoTileUpdate(3,0,"red","red","POWERUP");
        cli.addAmmoTileUpdate(3,1,"red","red","POWERUP");
        cli.addAmmoTileUpdate(3,2,"red","red","POWERUP");
        cli.removeAmmoTileUpdate(0,0);
        cli.removeAmmoTileUpdate(0,1);
        cli.printScreen();
*/
        boolean[] available = {true, false, true};
        String[] powerUpName = {"granata", "mirino"};
        CubeColour[] powerUpColor = {CubeColour.Red, CubeColour.Yellow};

        /**
         * FINE PROVA
         */
        connected = false;
        String[] userInput = gameInit();
        String user = userInput[0];
        String connectionType = userInput[1];
        String serverIPAddress = userInput[2];
        try {
            if (connectionType.equalsIgnoreCase(NetConfiguration.ConnectionType.RMI.name())) {

                clientImplementation = new RMIClient(user,
                        NetConfiguration.RMISERVERPORTNUMBER + 1000 + new Random().nextInt(1000), serverIPAddress);

            } else {
                clientImplementation = new SocketClient(user, serverIPAddress);
            }
            connected = true;
        } catch (ConnectException | RemoteException e) {
            log.warning("Can't reach the Lobby!\nClosing the app..");
            CustomLogger.logException(e);
        }

        //todo sempre connected finchè non si sconnette il server
        while (connected) {
            boolean waiting = clientImplementation.isConnected();

            while (waiting) {
                try {
                    currentMessage = clientImplementation.listenMessage();
                    log.info("Listened message for:\t".concat(currentMessage.getUser()).concat(currentMessage.toString()));
                    if (isGameSet() && !currentMessage.getUser().equals("BROADCAST")) {
                        printScreen();
                    }
                    //todo i ModelUpdate, eseguendo performAction ritornano null?, non un Event;
                    currentMessage = ((ClientEvent) currentMessage).performAction(this);
                    //invia il messaggio solo se non è Update -> BROADCAST
                    if (!currentMessage.getUser().equals("BROADCAST")) {
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

        try {
            clientImplementation.disconnectClient();
        } catch (NullPointerException nullPointer) {
            log.warning("Client implementation doesn't exist, nothing to disconnect..");
        } catch (Exception closingException) {
            log.warning("Can't close correctly the client connection!");
            CustomLogger.logException(closingException);
        } finally {
            log.info("Please, try again restarting the game.");
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return
     */
    public Event getToVirtualView() {
        return toVirtualView;
    }

    /**
     * @param toServer
     */
    public void setToVirtualView(Event toServer) {
        this.toVirtualView = toServer;
    }

    /**
     * send a messaage to virtual view
     */
    public void toVirtualView() {
        //todo metodi che dal client inviano al server passando il messaggio toVirtualView
    }

    /**
     *
     */
    public void fromVirtualView() {
        //todo riceve un messagio dalla virtual view e chiama metodi coder(?)
    }


}
