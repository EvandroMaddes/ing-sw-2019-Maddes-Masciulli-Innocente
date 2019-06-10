package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

public class PlayerReconnectionNotify extends ModelViewEvent{
    Character reconnectedCharacter;

    public PlayerReconnectionNotify(Character reconnectedCharacter) {
        super("BROADCAST");
        this.reconnectedCharacter = reconnectedCharacter;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return null;
    }
}
