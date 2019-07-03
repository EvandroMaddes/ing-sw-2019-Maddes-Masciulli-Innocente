package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.DamageToken;

import java.util.Observable;

/**
 * Is the GameTrack abstract class, in the eventuality of other mod implementations
 * @author Evandro Maddes
 */
public abstract class GameTrack extends Observable {

    /**
     * Is the number of remaining skulls on the GameTrack
     */
    private int skullBox;
    /**
     * Is the set of reachable point, gained at the end of the game following the game rules
     */
    public final static int[] POINTS = new int[]{ 8,6,4,2,1,1 };
    /**
     * Is the number of repeted token from the same player on each GameTrack box
     */
    public int[] tokenSequence;

    /**
     * Constructor:
     * set to 8 the number of skull, following the game rules, and clean the tokenSequence
     */
    public GameTrack() {
        this.skullBox = 8;
        this.tokenSequence = new int[]{0,0,0,0,0,0,0,0};
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
     * @return true if the game is over, false if there is any skull in the skullbox
     */
    public boolean checkEndTrack()
    {
        return skullBox == 0;
    }

    /**
     * Getter method:
     * @return the number of skulls in the skullbox
     */
    public int getSkullBox() {
        return skullBox;
    }

    /**
     * Getter method:
     * @return the actual tokenSequence
     */
    public int[] getTokenSequence() {
        return tokenSequence;
    }

    /**
     * Will be implemented depending on mod
     * @param damageToken is the Player damageToken
     * @param number is the number of token that must be set
     */
    public abstract void evaluateDamage(DamageToken damageToken, int number );

    /**
     * Evaluate the end-game points awarding
     */
    public abstract void collectGameTrackPoints();
}
