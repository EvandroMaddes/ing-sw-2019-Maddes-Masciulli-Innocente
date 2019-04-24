package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;


public abstract class AlternateFireWeapon extends Weapon {

    private AmmoCube[] alternativeEffectCost;


    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost is the alternative effect cost
     */
    public AlternateFireWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost)
    {
        super(colour, name, reloadCost);
        this.alternativeEffectCost = alternativeEffectCost;
    }

    /**
     * overrided for every alternative effect weapon
     */
    public void alterativeEffect()
    {

    }

}
