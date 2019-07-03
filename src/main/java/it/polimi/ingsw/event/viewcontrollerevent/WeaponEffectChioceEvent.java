package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Message to notify the effect choice
 *
 * @author Federico Innocente
 */
public class WeaponEffectChioceEvent extends ViewControllerEvent {
    /**
     * Is the number of the chosen effect (1/2/3)
     */
    private int effectChoice;

    /**
     * Constructor
     *
     * @param user         is the player username
     * @param effectChoice is the effect chosen
     */
    public WeaponEffectChioceEvent(String user, int effectChoice) {
        super(user);
        this.effectChoice = effectChoice;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().askForEffectPay(effectChoice);
    }


}
