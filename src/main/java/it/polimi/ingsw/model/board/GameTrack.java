package it.polimi.ingsw.model.board;

/**
 * @author Evandro Maddes
 */
public abstract class GameTrack {

    private int skullBox;
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
     * Rimuove i teschi dalla Gametrack ogni volta che muore un giocatore
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
