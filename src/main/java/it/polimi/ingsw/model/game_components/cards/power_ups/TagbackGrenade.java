package it.polimi.ingsw.model.game_components.cards.power_ups;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

/**
 * @author Federico Innocetnte
 */
public class TagbackGrenade extends PowerUp {

    public TagbackGrenade(CubeColour colour) {
        super(colour, "TagbackGrenade", Usability.END_TURN);
    }

    @Override
    public void performEffect(Object target) {
        mark((Player) target, 1);
    }
}
