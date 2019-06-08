package it.polimi.ingsw.model.board;

import java.util.Observable;

/**
 * @author Evandro Maddes
 */
public abstract class GameTrack extends Observable {

    private int skullBox = 8;
    public final static int[] POINTS = new int[]{ 8,6,4,2,1,1 };

    /**
     * Remove a skull for every Player death
     */
    public void removeSkull()
    {
        skullBox--;
    }

    /**
     * if the number of skulls is zero, it means that game is over
     * @return
     */
    public boolean checkEndTrack()
    {
        return skullBox == 0;
    }

    public int getSkullBox() {
        return skullBox;
    }
}
