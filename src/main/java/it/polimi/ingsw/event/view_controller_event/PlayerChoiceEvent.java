package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.EventType;
import it.polimi.ingsw.model.player.Character;


import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * represent the Players selected by the user
 * the number is setted by the decoding of the PlayerRequestEvent
 */
public class PlayerChoiceEvent extends Event {

    /**
     * Context: set the choosing context:
     *      1) "Character choice"
     *      2) "Targets choice"
     */
    private ArrayList<Character> targetPlayersOrder;
    private String context;


    /**
     * Constructor
     * @param user the Client user
     * @param targetPlayersOrder the chosen Players,
     */
    public PlayerChoiceEvent(String user, String context, ArrayList<Character> targetPlayersOrder){
        super(user);
        this.context=context;
        this.targetPlayersOrder=targetPlayersOrder;
        type= EventType.PlayerChoiceEvent;
    }

    public ArrayList<Character> getTargetPlayersOrder() {
        return targetPlayersOrder;
    }

    public String getContext() {
        return context;
    }
}
