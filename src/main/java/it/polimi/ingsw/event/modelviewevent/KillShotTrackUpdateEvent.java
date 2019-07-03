package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * represent a GameTrack Update, when a player is killed
 *
 * @author Francesco Masciulli, Federico Innocente, Evandro Maddes
 */
public class KillShotTrackUpdateEvent extends ModelViewBroadcastEvent {
    /**
     * Is an array that contains the Character of the players that put token on the track
     */
    private Character[] damageTokens;
    /**
     * Identify the sequence of token on the track
     */
    private int[] sequence;

    /**
     * Constructor: call the super-class constructor an set the attributes values
     *
     * @param damageTokens is the set Character array;
     * @param sequence     is the set int array
     */
    public KillShotTrackUpdateEvent(Character[] damageTokens, int[] sequence) {
        super();
        this.damageTokens = damageTokens;
        this.sequence = sequence;
    }

    /**
     * performAction implementation: handle the gameTrack update
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.gameTrackSkullUpdate(damageTokens, sequence);
    }
}
