package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

public class PlayerDisconnectionNotify extends ModelViewBroadcastEvent {
    Character disconnectedCharacter;

    public PlayerDisconnectionNotify(Character disconnectedCharacter) {
        super();
        this.disconnectedCharacter = disconnectedCharacter;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        remoteView.playerReconnectionNotify(getUser(), disconnectedCharacter, true);
        return remoteView.positionUpdate(disconnectedCharacter,404,404);//404 is used to remove one player from the map
    }
}
