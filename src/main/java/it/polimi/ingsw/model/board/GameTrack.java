package it.polimi.ingsw.model.board;

public abstract class GameTrack {

    private int skullBox;
    final static int points[] = new int[]{ 8,6,4,2,1,1 };

    /**
     * Rimuove i teschi dalla Gametrack ogni volta che muore un giocatore
     */
    public void removeSkull()
    {

    }

    /**
     * if the number of skulls is zero, it means that game is over
     * @return
     */
    public boolean checkEndTrack()
    {
        boolean i=true;
        return i;
    }

}
