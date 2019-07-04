package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message to notify the disconnection of a player to other clients
 *
 * @author Federico Innocente
 * @author Francesco Masciulli
 */
public class PlayerDisconnectionNotify extends ModelViewBroadcastEvent {
    /**
     * Is the disconnected character
     */
    private Character disconnectedCharacter;

    /**
     * Constructor
     * @param disconnectedCharacter is the disconnected character
     */
    public PlayerDisconnectionNotify(Character disconnectedCharacter) {
        super();
        this.disconnectedCharacter = disconnectedCharacter;
    }

    /**
     * performAction implementation: handle the player disconnection
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        remoteView.playerReconnectionNotify(getUser(), disconnectedCharacter, true);
        return remoteView.positionUpdate(disconnectedCharacter,404,404);//404 is used to remove one player from the map
    }
}
