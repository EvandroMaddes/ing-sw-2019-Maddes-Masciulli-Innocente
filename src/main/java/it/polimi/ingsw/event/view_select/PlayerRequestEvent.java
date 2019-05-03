package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class PlayerRequestEvent extends Event {

    private ArrayList<Player> targetPlayers;

    public PlayerRequestEvent(String user, ArrayList<Player> targetPlayers){
        super(user);
        this.targetPlayers=targetPlayers;
    }
}
