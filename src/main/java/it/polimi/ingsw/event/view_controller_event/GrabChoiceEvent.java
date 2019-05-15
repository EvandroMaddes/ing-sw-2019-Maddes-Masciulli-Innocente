package it.polimi.ingsw.event.view_controller_event;


import it.polimi.ingsw.controller.Controller;

public class GrabChoiceEvent extends PositionChoiceEvent {

    public GrabChoiceEvent(String user, int positionX, int positionY) {
        super(user, positionX, positionY);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performGrab(getPositionX(), getPositionY());
    }
}
