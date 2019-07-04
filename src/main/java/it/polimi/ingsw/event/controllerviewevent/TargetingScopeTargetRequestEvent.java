package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message for the request of a target for the targeting scope
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class TargetingScopeTargetRequestEvent extends ControllerViewEvent {
    /**
     * Is a list of possible targets
     */
    private ArrayList<Character> possibleTargets;

    /**
     * Constructor
     *
     * @param user            is the player username
     * @param possibleTargets is a list of possible targets
     */
    public TargetingScopeTargetRequestEvent(String user, ArrayList<Character> possibleTargets) {
        super(user);
        this.possibleTargets = possibleTargets;
    }

    /**
     * performAction implementation: ask to the player to choose a target
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an TargetingScopeTargetChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.targetingScopeTargetChoice(possibleTargets);
    }

    /**
     * Getter method
     *
     * @return all possible target
     */
    public ArrayList<Character> getPossibleTargets() {
        return possibleTargets;
    }
}
