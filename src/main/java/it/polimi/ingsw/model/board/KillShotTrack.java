package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.DamageToken;

import java.util.ArrayList;

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

    public void evalueteDamage( DamageToken damageToken, int number )
    {

    }

    private void addDamage( DamageToken damageToken )
    {

    }

}
