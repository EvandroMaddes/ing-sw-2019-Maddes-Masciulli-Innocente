package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class TagbackGrenade extends PowerUp {

    public TagbackGrenade(CubeColour colour){
        super(colour, "TagbackGranade");
    }

    //If an enemy player damages the player, he could mark that enemy player
    @Override
    public void useEffect() {

    }
}
