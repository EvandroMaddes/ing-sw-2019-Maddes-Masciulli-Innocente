package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public abstract class AlternateFireWeapon extends TwoEffectWeapon {

    public AlternateFireWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    /**
     * for the alternative effect weapon, by default if one effect is used all usable are setted to false
     * @param effectUsed
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if ( (effectUsed == 0 || effectUsed == 1) && getUsableEffect()[effectUsed] ){
            updateUsableEffect(new boolean[]{false, false, false});
        }
        else
            throw new IllegalArgumentException("EffectControlFlow error");
    }

    @Override
    public boolean isUsable() {
        return isLoaded() && ( (isUsableEffectOne() && getUsableEffect()[0] ) || ( isUsableEffectTwo() && getUsableEffect()[1] ) );
    }
}