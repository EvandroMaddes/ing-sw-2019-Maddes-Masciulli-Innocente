package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;


public abstract class OneOptionalEffectWeapon extends Weapon {

    private AmmoCube[] firstOptionalEffectCost;


    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param firstOptionalEffectCost cost of the firsts (and maybe only) optional effect
     */
    public OneOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost)
    {
        super(colour, name, reloadCost);
        this.firstOptionalEffectCost = firstOptionalEffectCost;
    }

    /**
     * overrided for every weapon that implements from this class
     */
    public void firstOptionalEffec()
    {

    }
}
