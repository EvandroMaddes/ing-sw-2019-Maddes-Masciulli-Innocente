package it.polimi.ingsw.event.view_controller_event;


import it.polimi.ingsw.controller.Controller;

/**
 * @author Francesco Masciulli
 * represent the Players selected by the user
 * the number is setted by the decoding of the PlayerRequestEvent
 */
public class PlayerChoiceEvent extends ViewControllerEvent {
    public PlayerChoiceEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(Controller controller) {

    }
}
