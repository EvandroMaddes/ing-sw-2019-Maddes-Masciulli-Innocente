package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.DamageToken;

import java.util.ArrayList;

/**
 * @Evandro Maddes
 */
public class KillShotTrack extends GameTrack {

    private ArrayList<DamageToken> tokenTrack;
    private int skullBox;

    public KillShotTrack()
    {

    }


    /**
     * this method add the number (1 or more) of DamageToken to the Track
     * @param damageToken that represent the player
     * @param number of token that replace a skull
     */
    public void evaluateDamage( DamageToken damageToken, int number )
    {   for(int i=0; i<number; i++){
            addDamage(damageToken);
        }
    }

    /**
     *
     * @param damageToken
     */
    private void addDamage( DamageToken damageToken )
    {
        tokenTrack.add(damageToken);
    }

    /**
     * if the number of skulls is zero, it means that game is over
     * @return
     */
    public boolean checkEndTrack()
    {
        return skullBox == 0;
    }

    /**
     * Rimuove i teschi dalla Gametrack ogni volta che muore un giocatore
     */
    public void removeSkull()
    {
        skullBox--;
    }

}
