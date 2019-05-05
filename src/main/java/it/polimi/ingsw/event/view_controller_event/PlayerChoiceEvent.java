package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * represent the Players selected by the user
 * the number is setted by the decoding of the PlayerRequestEvent
 */
public class PlayerChoiceEvent extends Event {

    private ArrayList<Character> targetPlayersOrder;

    /**
     * Constructor
     * @param user the Client user
     * @param targetPlayersOrder the chosen Players,
     *                           the integer indicates the player encoding Character -> int
     */
    public PlayerChoiceEvent(String user, ArrayList<Character> targetPlayersOrder){
        super(user);
        this.targetPlayersOrder=targetPlayersOrder;
    }
}
