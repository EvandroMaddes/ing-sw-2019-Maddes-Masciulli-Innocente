package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;


/**
 * @author Federico Innocente
 */
public class Teleporter extends PowerUp{

    Square destination;

    public Teleporter(CubeColour colour)
    {
        super(colour, "Teleporter");
    }

    public void setDestination(Square destination)
    {
        this.destination = destination;
    }

    /**
     * teleport the target to the destination
     */
    @Override
    public void useEffect()
    {
        getTarget().setPosition(destination);
        super.useEffect();
    }
}
