package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the request of a target for teh teleporter
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class TeleporterTargetRequestEvent extends PositionRequestEvent {

    /**
     * Constructor
     *
     * @param user            is the player username
     * @param possibleSquareX are all the possible row
     * @param possibleSquareY are all the possible column
     */
    public TeleporterTargetRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    /**
     * performAction implementation: ask to the player the destination of a teleporter
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an TeleporterTargetChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.newtonTeleporterTargetSquareChoice(getPossibleSquareX(), getPossibleSquareY());
    }
}
