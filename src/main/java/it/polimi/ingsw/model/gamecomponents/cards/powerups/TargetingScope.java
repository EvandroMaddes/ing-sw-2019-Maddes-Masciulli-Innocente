package it.polimi.ingsw.model.gamecomponents.cards.powerups;

import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;


/**
 * Class that implements the TargetingScope powerUp
 *
 * @author Federico Innocente
 */
public class TargetingScope extends PowerUp {

    /**
     * Constructor: call the PowerUp Constructor and set the right usability
     *
     * @param colour is the PowerUp colour
     */
    public TargetingScope(CubeColour colour) {
        super(colour, "TargetingScope", Usability.WHILE_ACTION);
    }


    /**
     * Implements PowerUp method: damage a player with a DamageToken
     *
     * @param target is the target of the effect (a Player)
     */
    @Override
    public void performEffect(Object target) {
        damage((Player) target, 1);
    }
}
