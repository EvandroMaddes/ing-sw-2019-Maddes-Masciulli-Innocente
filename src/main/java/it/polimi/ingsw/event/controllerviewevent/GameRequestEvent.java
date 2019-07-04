package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message to print the map to the player
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class GameRequestEvent extends ControllerViewEvent {

    public GameRequestEvent(String user) {
        super(user);
    }

    /**
     * performAction implementation: print the maps
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an GameChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        Event message = remoteView.gameChoice();
        remoteView.printScreen();
        return message;

    }
}
