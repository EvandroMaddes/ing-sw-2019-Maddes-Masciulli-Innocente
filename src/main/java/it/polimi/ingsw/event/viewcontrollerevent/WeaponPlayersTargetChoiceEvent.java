package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

public class WeaponPlayersTargetChoiceEvent extends ViewControllerEvent {
    private ArrayList<Character> targetPlayer;

    public WeaponPlayersTargetChoiceEvent(String user, ArrayList<Character> targetPlayer) {
        super(user);
        this.targetPlayer = targetPlayer;
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performWeaponEffect(targetPlayer);
    }
}
