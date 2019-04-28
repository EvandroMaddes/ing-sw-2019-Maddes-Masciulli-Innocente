package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class TargetingScope extends PowerUp {


    public TargetingScope(CubeColour colour){
        super(colour, "TargetingScope");
    }

    //when the player shot, he could pay a cube to make an additional damage
    @Override
    public void useEffect() {

    }

    public void payCube(AmmoCube cube)
    {

    }
}
