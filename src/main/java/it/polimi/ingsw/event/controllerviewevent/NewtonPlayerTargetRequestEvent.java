package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message for the choice of a target for the newton
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class NewtonPlayerTargetRequestEvent extends TargetPlayerRequestEvent {

    /**
     * Constructor
     * @param user is the player username
     * @param possibleTargets are the possible character targets
     * @param maxTarget is the max number of tatgets the player can chose
     */
    public NewtonPlayerTargetRequestEvent(String user, ArrayList<Character> possibleTargets, int maxTarget) {
        super(user, possibleTargets, maxTarget);
    }

    /**
     * performAction implementation: ask to the player the target of a newton powerUp
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an NewtonPlayerTargetChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.newtonTargetChoice(getPossibleTargets(),getMaxTarget());
    }
}
