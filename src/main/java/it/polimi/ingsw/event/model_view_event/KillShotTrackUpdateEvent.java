package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * represent a GameTrack Update, when a player is killed
 *  or  (DominationMod) when a SpawnSquare is damaged
 */
public class KillShotTrackUpdateEvent extends ModelViewEvent {
    private Character[] damageTokens;
    private int skullsNumber;

    public KillShotTrackUpdateEvent(String user, Character[] damageTokens, int skullsNumber) {
        super(user);
        this.damageTokens = damageTokens;
        this.skullsNumber = skullsNumber;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        // TODO: 2019-06-08
        //return remoteView.gameTrackSkullUpdate(getCharacter(), getSkullNumber(), killerCharacter);
        return null;
    }
}
