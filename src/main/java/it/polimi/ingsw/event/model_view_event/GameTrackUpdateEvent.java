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
public class GameTrackUpdateEvent extends ModelViewEvent {
    public GameTrackUpdateEvent(String user) {
        super(user);
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return null;
    }
}
