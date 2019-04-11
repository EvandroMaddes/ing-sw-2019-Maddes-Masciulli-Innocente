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
     * A cosa serve questo metodo
     * @param damageToken
     * @param number
     */
    public void evalueteDamage( DamageToken damageToken, int number )
    {

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
