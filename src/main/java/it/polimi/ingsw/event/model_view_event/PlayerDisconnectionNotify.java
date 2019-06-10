package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

public class PlayerDisconnectionNotify extends ModelViewEvent {
    Character disconnectedCharacter;

    public PlayerDisconnectionNotify(Character disconnectedCharacter) {
        super();
        this.disconnectedCharacter = disconnectedCharacter;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        // TODO: 2019-06-09
        return null;
    }
}
