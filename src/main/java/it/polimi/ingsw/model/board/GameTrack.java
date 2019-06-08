package it.polimi.ingsw.model.board;

import java.util.Observable;

/**
 * @author Evandro Maddes
 */
public abstract class GameTrack extends Observable {

    private int skullBox = 8;
    final static int points[] = new int[]{ 8,6,4,2,1,1 };

    /**
     *
     * @param skullBox
     */

    public void setSkullBox(int skullBox) {
        this.skullBox = skullBox;
    }

    /**
     *
     * @return
     */
    public int getSkullBox() {
        return skullBox;
    }

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
        boolean i;

        if(skullBox==0)
            i=true;
        else i=false;

        return i;
    }

}
