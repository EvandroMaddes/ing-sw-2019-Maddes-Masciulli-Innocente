package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.utils.custom_exceptions.EffectIllegalArgumentException;

/**
 * Abstract class for the weapons with two alternative effects
 *
 * @author Federico Innocente
 */
public abstract class AlternateFireWeapon extends TwoEffectWeapon {

    /**
     * Constructor
     *
     * @param colour           is the colour of the weapon
     * @param name             is the name of the weapon
     * @param reloadCost       is the reload cost
     * @param secondEffectCost is the cost of the second effect
     */
    public AlternateFireWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     * Some effects are only usable before or after other specific effects.
     * For the alternative effect weapon, by default, if one effect is used all usable are set to false
     *
     * @param effectUsed is the last effect used
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if ((effectUsed == 0 || effectUsed == 1) && getUsableEffect()[effectUsed]) {
            updateUsableEffect(new boolean[]{false, false, false});
        } else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Check if the weapon is usable.
     * To be usable, the weapon must be loaded and at least une of its effects must be usable
     *
     * @return true if the weapon is usable, false otherwise
     */
    @Override
    public boolean isUsable() {
        return isLoaded() && ((isUsableEffectOne() && getUsableEffect()[0]) || (isUsableEffectTwo() && getUsableEffect()[1]));
    }
}