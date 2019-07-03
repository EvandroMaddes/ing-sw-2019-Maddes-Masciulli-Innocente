package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Message to notify the player target choice for weapons effect
 *
 * @author Federico Inncente
 */
public class WeaponPlayersTargetChoiceEvent extends ViewControllerEvent {
    /**
     * Are the chosen target for the effect
     */
    private ArrayList<Character> targetPlayer;

    /**
     * Constructor
     *
     * @param user         is the player username
     * @param targetPlayer are the target character
     */
    public WeaponPlayersTargetChoiceEvent(String user, List<Character> targetPlayer) {
        super(user);
        this.targetPlayer = (ArrayList<Character>) targetPlayer;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performWeaponEffect(targetPlayer);
    }
}
