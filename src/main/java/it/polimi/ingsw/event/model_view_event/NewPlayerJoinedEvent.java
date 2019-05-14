package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Player;

public class NewPlayerJoinedEvent extends Event {
    private Player newPlayer;

    public NewPlayerJoinedEvent(Player newPlayer){
        super(null);
        this.newPlayer = newPlayer;

    }
}
