package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class WeaponEffectChioceEvent extends ViewControllerEvent {
    private int effectChoice;

    public WeaponEffectChioceEvent(String user, int effectChoice) {
    super(user);
    this.effectChoice = effectChoice;
}
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().askForEffectPay(effectChoice);
    }


}
