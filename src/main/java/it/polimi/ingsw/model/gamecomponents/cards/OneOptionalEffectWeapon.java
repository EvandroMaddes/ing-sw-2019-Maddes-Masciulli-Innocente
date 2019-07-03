package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.utils.custom_exceptions.EffectIllegalArgumentException;

/**
 * Abstract class for the weapons with two alternative effects
 *
 * @author Federico Inncente
 */
public abstract class OneOptionalEffectWeapon extends TwoEffectWeapon {

    /**
     * Constructor
     *
     * @param colour           is the colour of the weapon
     * @param name             is the name of the weapon
     * @param reloadCost       is the reload cost
     * @param secondEffectCost is the cost of the second effect
     */
    public OneOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     * Some effects are only usable before or after other specific effects.
     *
     * @param effectUsed is the last effect used
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed != 2 && getUsableEffect()[effectUsed])
            getUsableEffect()[effectUsed] = false;
        else
            throw new EffectIllegalArgumentException();
    }
}
