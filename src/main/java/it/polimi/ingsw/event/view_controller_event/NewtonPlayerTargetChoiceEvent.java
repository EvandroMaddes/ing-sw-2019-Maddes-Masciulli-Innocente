package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.utils.Decoder;

public class NewtonPlayerTargetChoiceEvent extends ViewControllerEvent {
    private Character chosenTarget;


    public NewtonPlayerTargetChoiceEvent(String user, Character chosenTarget) {
        super(user);
        this.chosenTarget = chosenTarget;
    }

    @Override
    public void performAction(Controller controller) {
        Object target = Decoder.decodePlayerFromCharacter(chosenTarget, controller.getGameManager().getModel().getPlayers());
        controller.getGameManager().getCurrentRound().getActionManager().performPowerUp(target);
        controller.getGameManager().getCurrentRound().getActionManager().askForSquareTargetsNewton();
    }
}
