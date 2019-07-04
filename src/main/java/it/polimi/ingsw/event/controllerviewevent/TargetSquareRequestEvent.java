package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the square target request for an effect of a weapon
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class TargetSquareRequestEvent extends ControllerViewEvent {
    /**
     * Are all the possible row
     */
    private int[] possibleTargetsX;
    /**
     * Are all teh possible column
     */
    private int[] possibleTargetsY;

    /**
     * Constructor
     *
     * @param user             is the player username
     * @param possibleTargetsX Are all the possible row
     * @param possibleTargetsY Are all teh possible column
     */
    public TargetSquareRequestEvent(String user, int[] possibleTargetsX, int[] possibleTargetsY) {
        super(user);
        this.possibleTargetsX = possibleTargetsX;
        this.possibleTargetsY = possibleTargetsY;
    }

    /**
     * Getter method
     *
     * @return all the possible row
     */
    public int[] getPossibleTargetsX() {
        return possibleTargetsX;
    }

    /**
     * Getter method
     *
     * @return all the possible column
     */
    public int[] getPossibleTargetsY() {
        return possibleTargetsY;
    }

    /**
     * performAction implementation: ask to the player the square target for an effect of a weapon
     *
     * @param remoteView is the Client RemoteView implementation
     * @return a TargetSquareChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponEffectSquareChoice(getPossibleTargetsX(), getPossibleTargetsY());
    }
}
