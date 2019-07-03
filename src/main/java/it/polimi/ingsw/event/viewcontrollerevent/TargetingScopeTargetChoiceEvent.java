package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;

public class TargetingScopeTargetChoiceEvent extends ViewControllerEvent{
    private Character target;

    public TargetingScopeTargetChoiceEvent(String user, Character target) {
        super(user);
        this.target = target;
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performTargetingScopeEffect(target);
    }
}
