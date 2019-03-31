package it.polimi.ingsw.model;

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

    public void replaceSkull( DamageToken damageToken )
    {

    }

    public void addDamage( DamageToken damageToken )
    {

    }

}
