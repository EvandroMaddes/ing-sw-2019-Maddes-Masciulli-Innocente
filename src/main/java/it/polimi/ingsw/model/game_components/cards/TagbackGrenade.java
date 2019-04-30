package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

/**
 * @author Federico Innocetnte
 */
public class TagbackGrenade extends PowerUp {

    /**
     *
     * @param colour is the colour of the powerup
     */
    public TagbackGrenade(CubeColour colour)
    {
        super(colour, "TagbackGranade");
    }



    /**
     * add 1 mark to the target by the owner of the powerUp
     */
    @Override
    public void useEffect()
    {


        getTarget().getPlayerBoard().addMarks(this.getOwner(), 1);
        super.useEffect();
    }
}
