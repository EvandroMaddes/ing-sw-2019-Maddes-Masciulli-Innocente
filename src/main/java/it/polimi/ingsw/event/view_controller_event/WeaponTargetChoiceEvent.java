package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

public class WeaponTargetChoiceEvent extends ViewControllerEvent {
    private ArrayList<Character> targetPlayer;

    public WeaponTargetChoiceEvent(String user, ArrayList<Character> targetPlayer) {
        super(user);
        this.targetPlayer = targetPlayer;
    }

    public ArrayList<Character> getTargetPlayer() {
        return targetPlayer;
    }

    @Override
    public void performAction(Controller controller) {
        //TODO
    }
}
