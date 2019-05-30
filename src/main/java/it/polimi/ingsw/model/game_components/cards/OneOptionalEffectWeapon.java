package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public abstract class OneOptionalEffectWeapon extends TwoEffectWeapon {

    public OneOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed != 2 && getUsableEffect()[effectUsed])
            getUsableEffect()[effectUsed] = false;
        else
            throw new IllegalArgumentException();
    }
}
