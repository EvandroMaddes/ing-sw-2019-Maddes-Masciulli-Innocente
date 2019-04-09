package it.polimi.ingsw.model.game_components.ammo;

/**
 * @author Federico Innocente
 */

public class AmmoCube {

    private CubeColour colour;

    public AmmoCube (CubeColour colour)
    {
        this.colour = colour;
    }


    public CubeColour getColour()
    {
        return colour;
    }
}
