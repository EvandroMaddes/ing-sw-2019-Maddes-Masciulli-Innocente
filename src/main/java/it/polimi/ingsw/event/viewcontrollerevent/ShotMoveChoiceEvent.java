package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

public class ShotMoveChoiceEvent extends PositionChoiceEvent {

    public ShotMoveChoiceEvent(String user, int positionX, int positionY) {
        super(user, positionX, positionY);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().managePreEffectShot(getPositionX(), getPositionY());
    }
}
