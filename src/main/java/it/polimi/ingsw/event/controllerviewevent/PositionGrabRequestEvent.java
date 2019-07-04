package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the request of a grab position
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class PositionGrabRequestEvent extends PositionRequestEvent {

    /**
     * Constructor
     *
     * @param user            player username
     * @param possibleSquareX all possible row
     * @param possibleSquareY all possible column
     */
    public PositionGrabRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    /**
     * performAction implementation: ask to the player where he want to grab
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an PositionGrabChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.positionGrabChoice(getPossibleSquareX(), getPossibleSquareY());
    }
}
