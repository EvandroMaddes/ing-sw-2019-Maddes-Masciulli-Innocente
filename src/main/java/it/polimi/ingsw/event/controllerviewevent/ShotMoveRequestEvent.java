package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the request of a move before of a shot action
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class ShotMoveRequestEvent extends PositionRequestEvent {

    /**
     * Constructor
     *
     * @param user            is the player username
     * @param possibleSquareX are all possible row destination
     * @param possibleSquareY are all possible column destination
     */
    public ShotMoveRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    /**
     * performAction implementation: ask to the player where he want to move
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an ShotMoveChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.shotMoveChoiceEvent(getPossibleSquareX(), getPossibleSquareY());
    }
}
