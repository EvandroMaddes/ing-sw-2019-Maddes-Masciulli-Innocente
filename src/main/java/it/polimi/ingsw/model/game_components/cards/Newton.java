package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Direction;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

/**
 * @author Federico Innocente
 */
public class Newton extends PowerUp {

    private Direction direction;
    private int times;

    /**
     *
     * @param colour card colour
     */
    public Newton(CubeColour colour)
    {
        super(colour , "Newton");
    }


    @Override
    public void useEffect()
    {
        for (int i = 0; i < times; i++)
        {
            getTarget().setPosition(direction.getNextSquare());

        }
    }
}
