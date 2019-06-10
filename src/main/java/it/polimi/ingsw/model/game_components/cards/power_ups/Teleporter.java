package it.polimi.ingsw.model.game_components.cards.power_ups;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;


/**
 * @author Federico Innocente
 */
public class Teleporter extends PowerUp {

    public Teleporter(CubeColour colour) {
        super(colour, "Teleporter");
    }

    @Override
    public void performEffect(Object target) {
            move(getOwner(), (Square)target);
    }

    @Override
    public Usability whenToUse() {
        return Usability.AS_ACTION;
    }
}
