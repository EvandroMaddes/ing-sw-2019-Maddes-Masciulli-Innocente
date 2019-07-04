package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the choice of a square target for the newton
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class NewtonTargetSquareRequestEvent extends PositionRequestEvent {

    /**
     * Constructor
     *
     * @param user            teh player username
     * @param possibleSquareX all possible row coordinate
     * @param possibleSquareY all possible column coordinate
     */
    public NewtonTargetSquareRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    /**
     * performAction implementation: ask to the player where he want ot move a player with the newton
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an NewtonTargetSquareChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.newtonTeleporterTargetSquareChoice(getPossibleSquareX(), getPossibleSquareY());
    }
}
