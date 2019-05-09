package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class TractorBeam extends AlternateFireWeapon {
    @Override
    public ArrayList<Player> getTargetsAlternativeEffect() {
        return null;
    }

    @Override
    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination) {

    }

    @Override
    protected void fireBaseEffect(ArrayList<Player> targets, Square destination) {

    }

    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */
    public TractorBeam(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect(){

    return null;
    }
}
