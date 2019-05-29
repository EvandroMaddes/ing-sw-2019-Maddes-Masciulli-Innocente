package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * @author Federico Innocente
 */
public class Newton extends PowerUp {

    private int direction;
    private int times;

    /**
     *
     * @param colour card colour
     */
    public Newton(CubeColour colour)
    {
        super(colour , "Newton");
    }

    /**
     *
     * @param times that the target moves
     */
    public void setTimes(int times)
    {
        this.times = times;
    }

    /**
     *
     * @param direction in which target moves
     */
    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    /**
     * move the target
     */
    @Override
    public void useEffect()
    {
        for (int i = 0; i < times; i++)
        {
            //move(getTarget(), direction);
        }
        super.useEffect();
    }

}
