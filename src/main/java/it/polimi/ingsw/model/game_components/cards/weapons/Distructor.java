package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.OneOptionalEffectWeapon;

public class Distructor extends OneOptionalEffectWeapon {
    public Distructor(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost){
        super(colour,name,reloadCost,firstOptionalEffectCost);

    }



}
