package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class VortexCannon extends OneOptionalEffectWeapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param firstOptionalEffectCost
     */
    public VortexCannon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost);
    }

    @Override
    protected void fireBaseEffect(ArrayList<Player> targets, Square destination) {

    }

    @Override
    public void fireFirstOptionalEffect(ArrayList<Player> targets, Square destination) {

    }

    @Override
    public boolean canActivateAnEffect() {
        return false;
    }

    @Override
    public ArrayList<Player> getTargetsBaseEffect() {
        return null;
    }

    @Override
    public ArrayList<Player> getTargetsFirstOptionalEffect() {
        return null;
    }


}
