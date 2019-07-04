package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Event to ask to the player which action they want to use
 *
 * @author Federico Inncente
 * @author Evandro Maddes
 */
public class ActionRequestEvent extends ControllerViewEvent {
    /**
     * Are the usable action with the following codification:
     * 0 - Move
     * 1 - Grab
     * 2 - Shot
     */
    private boolean[] usableActions;

    /**
     * Constructor
     *
     * @param user          the username to who send the message
     * @param usableActions are the possible usable actions
     */
    public ActionRequestEvent(String user, boolean[] usableActions) {
        super(user);
        this.usableActions = usableActions;
    }

    /**
     * Getter method
     *
     * @return the usability of a shot action
     */
    private boolean isFireEnable() {
        return usableActions[2];
    }

    /**
     * Getter method
     *
     * @return the usable actions
     */
    public boolean[] getUsableActions() {
        return usableActions;
    }

    /**
     * performAction implementation: Ask the player which action want to perform
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an ActionChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.actionChoice(isFireEnable());
    }
}
