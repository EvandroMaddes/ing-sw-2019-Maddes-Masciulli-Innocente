package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class Teleporter extends PowerUp{

    public Teleporter(CubeColour colour){
        super(colour, "Teleporter");
    }


    //Move the player in a choosen position in the map; it could be used before and after any of the user action
    @Override
    public void useEffect() {

    }
}
