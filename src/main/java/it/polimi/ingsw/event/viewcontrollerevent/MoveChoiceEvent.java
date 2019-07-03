package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

public class MoveChoiceEvent extends PositionChoiceEvent {

    public MoveChoiceEvent(String user, int positionX, int positionY){
        super(user, positionX, positionY);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performMove(getPositionX(), getPositionY());
        controller.getGameManager().getCurrentRound().nextPhase();
    }
}
