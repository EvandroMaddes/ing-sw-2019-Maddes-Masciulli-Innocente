package it.polimi.ingsw.model.board;

import it.polimi.ingsw.event.modelviewevent.KillShotTrackUpdateEvent;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;

/**
 * This class implements the basic mod (KillShot mod) GameTrack of the game
 *
 * @Evandro Maddes
 */
public class KillShotTrack extends GameTrack {

    /**
     * is a container for the token on the GameTrack
     */
    private ArrayList<DamageToken> tokenTrack = new ArrayList<>();

    /**
     * Getter Method:
     *
     * @return the tokenTrack
     */
    public ArrayList<DamageToken> getTokenTrack() {
        return tokenTrack;
    }

    /**
     * GameTrack method implementation:
     * this method add the number (1 or 2 following game rules) of DamageToken to the Track
     *
     * @param damageToken that represent the player
     * @param number      of token that replace a skull
     */
    @Override
    public void evaluateDamage(DamageToken damageToken, int number) {
        addDamage(damageToken, number);
        if (!checkEndTrack()) {
            removeSkull();
        }
    }

    /**
     * GameTrack method implementation:
     * remove a skull from the gametrack and notify the VirtualViews
     */
    @Override
    public void removeSkull() {
        super.removeSkull();
        notifyView();
    }

    /**
     * Notify the VirtualViews with an ad-hoc created UpdateEvent
     */
    private void notifyView() {
        KillShotTrackUpdateEvent message = new KillShotTrackUpdateEvent(Encoder.encodeDamageTokenList(tokenTrack), getTokenSequence());
        notifyObservers(message);
    }


    /**
     * Add the given number of a player's token on the KillShotTrack and notify the VirtualViews
     *
     * @param damageToken is the player's DamageToken
     * @param number      is the number of token that must be set;
     */
    private void addDamage(DamageToken damageToken, int number) {
        for (int i = 0; i < number; i++) {
            tokenTrack.add(new DamageToken(damageToken.getPlayer()));
        }
        if (!checkEndTrack()) {
            int i = 0;
            while (getTokenSequence()[i] != 0)
                i++;
            getTokenSequence()[i] = number;
        }
        notifyView();
    }

    /**
     * Evaluates the DamageTokens on the track and award the players with the right amounts of points, following the game rules
     */
    @Override
    public void collectGameTrackPoints() {
        Player[] damageDealer = new Player[5];
        int[] damageDealed = new int[]{0, 0, 0, 0, 0};

        for (DamageToken d : tokenTrack) {
            int i = 0;
            while (damageDealed[i] != 0 && damageDealer[i] != d.getPlayer())
                i++;
            damageDealer[i] = d.getPlayer();
            damageDealed[i]++;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5 - i - 1; j++) {
                if (damageDealed[j + 1] > damageDealed[j]) {
                    int intTemp;
                    Player playerTemp;
                    intTemp = damageDealed[j + 1];
                    damageDealed[j + 1] = damageDealed[j];
                    damageDealed[j] = intTemp;
                    playerTemp = damageDealer[j + 1];
                    damageDealer[j + 1] = damageDealer[j];
                    damageDealer[j] = playerTemp;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (damageDealer[i] != null)
                damageDealer[i].addPoints(GameTrack.POINTS[i]);
        }
    }
}
