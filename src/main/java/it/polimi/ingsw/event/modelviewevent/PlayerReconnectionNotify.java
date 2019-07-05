package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message to handle the player reconnection
 * @author Federico Inncente
 * @author Francesco Masciulli
 */
public class PlayerReconnectionNotify extends ModelViewBroadcastEvent {
    /**
     * Is the character of the reconnected player
     */
    private Character reconnectedCharacter;

    /**
     * Constructor
     * @param reconnectedCharacter is the reconnected character
     */
    public PlayerReconnectionNotify(Character reconnectedCharacter) {
        super();
        this.reconnectedCharacter = reconnectedCharacter;
    }


    /**
     * PerformAction implementation: handle the player reconnection
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.playerReconnectionNotify(getUser(),reconnectedCharacter, false);
    }
}
