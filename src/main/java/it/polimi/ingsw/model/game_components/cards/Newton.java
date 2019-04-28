package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class Newton extends PowerUp {

   public Newton(CubeColour colour) {
        super(colour , "Newton");
    }

    //Move an enemy player
    @Override
    public void useEffect() {

    }
}
