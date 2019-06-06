package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class WeaponSquareTargetChoiceEvent extends ViewControllerEvent {
    private int x;
    private int y;

    public WeaponSquareTargetChoiceEvent(String user, int x, int y) {
        super(user);
        this.x = x;
        this.y = y;
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performWeaponEffect(x,y);
    }
}

