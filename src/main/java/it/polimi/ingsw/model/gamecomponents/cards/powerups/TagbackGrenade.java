package it.polimi.ingsw.model.gamecomponents.cards.powerups;

import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

/**
 * Implements the TagbackGrenade powerUp
 *
 * @author Federico Innocetnte
 */
public class TagbackGrenade extends PowerUp {

    /**
     * Constructor: call the PowerUpConstructor, setting the right Usability
     *
     * @param colour is the PowerUp colour
     */
    public TagbackGrenade(CubeColour colour) {
        super(colour, "TagbackGrenade", Usability.END_TURN);
    }

    /**
     * Implement PowerUp method: mark a player with a token
     *
     * @param target is the target of the effect (a Player)
     */
    @Override
    public void performEffect(Object target) {
        mark((Player) target, 1);
    }
}
