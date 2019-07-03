package it.polimi.ingsw.event.serverviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

/**
 * This message is sent to every player that join a created lobby, set the map chosen by the lobby creator
 *
 * @author Francesco Masciulli
 */
public class LobbySettingsEvent extends ServerClientEvent {
    /**
     * Is the number that identifies the chosen map
     */
    private int mapNumber;

    /**
     * Constructor: set the map number and call the ServerClientEvent constructor
     *
     * @param user      is the client username
     * @param mapNumber is the number that identifies the chosen map
     */
    public LobbySettingsEvent(String user, int mapNumber) {
        super(user);
        this.mapNumber = mapNumber;
    }


    /**
     * ServerClientEvent method implementation: it set the game on the RemoteView implementation
     *
     * @param clientImplementation is the client ClientInterface implementation
     * @param remoteView           is the client RemoteView Implementation
     * @return null, it doesn't need an answer;
     */
    @Override
    public Event performAction(ClientInterface clientImplementation, RemoteView remoteView) {
        remoteView.setGame(mapNumber);
        remoteView.printScreen();
        return null;
    }
}
