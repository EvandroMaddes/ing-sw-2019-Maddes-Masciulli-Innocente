package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.DamageToken;

import java.util.ArrayList;

/**
 * @Evandro Maddes
 */
public class KillShotTrack extends GameTrack {

    private static KillShotTrack killShotTrack;
    private ArrayList<DamageToken> tokenTrack;

    private KillShotTrack()
    {

    }

    public static void instance()
    {
        if (killShotTrack == null)
            killShotTrack = new KillShotTrack();
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

}
