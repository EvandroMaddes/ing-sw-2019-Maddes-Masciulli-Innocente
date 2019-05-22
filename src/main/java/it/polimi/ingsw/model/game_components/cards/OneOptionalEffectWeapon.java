package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


public abstract class OneOptionalEffectWeapon extends TwoEffectWeapon {

    ArrayList<Player> firstEffectTarget;

    public OneOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
        firstEffectTarget = new ArrayList<>();
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        firstEffectTarget.clear();
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed != 2 && getUsableEffect()[effectUsed])
            getUsableEffect()[effectUsed] = false;
        else
            throw new IllegalArgumentException();
    }

    public ArrayList<Player> getFirstEffectTarget() {
        return firstEffectTarget;
    }

}
