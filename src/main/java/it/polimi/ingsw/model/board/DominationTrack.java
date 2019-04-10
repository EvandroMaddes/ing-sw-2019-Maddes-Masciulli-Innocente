package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.DamageToken;

import java.util.ArrayList;

public class DominationTrack extends GameTrack {

    private static DominationTrack dominationTrack;
    private ArrayList<DamageToken> redTokenTrack;
    private ArrayList<DamageToken> blueTokenTrack;
    private ArrayList<DamageToken> yellowTokenTrack;

    private DominationTrack ()
    {

    }

    public void instance()
    {

    }

    /**
     *
     * @param track
     * @param damageToken
     */
    public void addDamage( ArrayList<DamageToken> track, DamageToken damageToken )
    {

    }
}
