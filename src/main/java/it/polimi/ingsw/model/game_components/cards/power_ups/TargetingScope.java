package it.polimi.ingsw.model.game_components.cards.power_ups;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;


/**
 * @author Federico Innocente
 */
public class TargetingScope extends PowerUp {

    public TargetingScope(CubeColour colour) {
        super(colour, "TargetingScope", Usability.WHILE_ACTION);
    }

    @Override
    public void performEffect(Object target) {
        damage((Player)target, 1);
    }
}
