package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message for the player target request for an effect of a weapon
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class TargetPlayerRequestEvent extends ControllerViewEvent {
    /**
     * Are the possible targets
     */
    private ArrayList<Character> possibleTargets;
    /**
     * Max number to choice for the player
     * maxTarget = -1 mean no target needed, the effect will be manage by itself if he can be used
     */
    private int maxTarget;


    /**
     * Constructor
     * @param user is the player username
     * @param possibleTargets are all the possible targets
     * @param maxTarget max number to choice for the player
     */
    public TargetPlayerRequestEvent(String user, ArrayList<Character> possibleTargets, int maxTarget) {
        super(user);
        this.possibleTargets = possibleTargets;
        this.maxTarget = maxTarget;
    }

    /**
     * Getter method
     * @return all the possible targets
     */
    public ArrayList<Character> getPossibleTargets() {
        return possibleTargets;
    }

    /**
     * Getter method
     * @return the max number of chosable targets
     */
    public int getMaxTarget() {
        return maxTarget;
    }

    /**
     * performAction implementation: ask to the player the player targets for a weapon effect
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an TargetPlayerChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponTargetChoice(getPossibleTargets(),getMaxTarget());
    }
}
