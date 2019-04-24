package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;


public abstract class TwoOptionalEffectWeapon extends OneOptionalEffectWeapon {

    private AmmoCube[] secondOptionalEffectCost;


    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param firstOptionalEffectCost
     * @param secondOptionalEffectCost is the cost of the secon optional effect
     */
    public TwoOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost)
    {
        super(colour, name, reloadCost, firstOptionalEffectCost);
        this.secondOptionalEffectCost = secondOptionalEffectCost;
    }

    /**
     * overloaded for every weapon that extends from this class
     */
    public void SecondOptionalEffect()
    {

    }
}
