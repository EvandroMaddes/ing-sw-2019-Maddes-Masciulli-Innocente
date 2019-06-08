package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.DamageToken;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 */
public class DominationTrack extends GameTrack {

    private static DominationTrack dominationTrack;
    private ArrayList<DamageToken> redTokenTrack;
    private ArrayList<DamageToken> blueTokenTrack;
    private ArrayList<DamageToken> yellowTokenTrack;

    public void instance()
    {

    }

    /**
     *set damage token on domination track
     * @param track track selected by a player
     * @param damageToken to keep which player made damege
     */
    public void addDamage( ArrayList<DamageToken> track, DamageToken damageToken )
    {
        if(track==redTokenTrack)
            redTokenTrack.add(damageToken);
        else if(track==blueTokenTrack)
                blueTokenTrack.add(damageToken);
        else if(track==yellowTokenTrack)
                yellowTokenTrack.add(damageToken);
    }
}
