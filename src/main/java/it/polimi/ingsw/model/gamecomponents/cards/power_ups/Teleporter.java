package it.polimi.ingsw.model.gamecomponents.cards.power_ups;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;


/**
 * @author Federico Innocente
 */
public class Teleporter extends PowerUp {

    public Teleporter(CubeColour colour) {
        super(colour, "Teleporter", Usability.AS_ACTION);
    }

    @Override
    public void performEffect(Object target) {
            move(getOwner(), (Square)target);
    }
}
