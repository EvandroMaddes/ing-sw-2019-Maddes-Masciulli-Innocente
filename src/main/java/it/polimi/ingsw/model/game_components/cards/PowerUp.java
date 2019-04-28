package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;

public abstract class  PowerUp extends Card {


    public PowerUp(CubeColour colour, String name){
        super(colour, name);
    }

    public void useEffect(){

    }

}
