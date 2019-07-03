package it.polimi.ingsw.model.gamecomponents.cards.power_ups;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;


/**
 * Implements the Teleporter PowerUp
 *
 * @author Federico Innocente
 */
public class Teleporter extends PowerUp {

    /**
     * Constructor: call the PowerUp Constructor and set the right usability
     *
     * @param colour is the PowerUp colour
     */
    public Teleporter(CubeColour colour) {
        super(colour, "Teleporter", Usability.AS_ACTION);
    }

    /**
     * Move the owner player to the chosen square
     *
     * @param target is the target of the effect (a Square)
     */
    @Override
    public void performEffect(Object target) {
        move(getOwner(), (Square) target);
    }
}
