package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;


import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * represent the Players selected by the user
 * the number is setted by the decoding of the PlayerRequestEvent
 */
public class PlayerChoiceEvent extends ViewControllerEvent {

    /**
     * Context: set the choosing context:
     *      1) "Character choice"
     *      2) "Targets choice"
     */
    private ArrayList<Character> targetPlayersOrder;


    /**
     * Constructor
     * @param user the Client user
     * @param targetPlayersOrder the chosen Players,
     */
    public PlayerChoiceEvent(String user, ArrayList<Character> targetPlayersOrder){
        super(user);
        this.targetPlayersOrder=targetPlayersOrder;
    }
    @Override
    public void performAction(Controller controller) {

    }

    public ArrayList<Character> getTargetPlayersOrder() {
        return targetPlayersOrder;
    }


}
