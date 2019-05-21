package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * represent a GameTrack Update, when a player is killed
 *  or  (DominationMod) when a SpawnSquare is damaged
 */
public class GameTrackUpdateEvent extends ModelViewEvent {
    private int damageTokenNumber;

    /**
     * Constructor
     * @param user the Client user
     * @param mapUpdate must be "MAPUPDATE"
     * @param damageTokenNumber the number of token that will be placed
     */
    public GameTrackUpdateEvent(String user, String mapUpdate, int damageTokenNumber){
        super(user);
        this.damageTokenNumber=damageTokenNumber;
       // type= EventType.GameTrackUpdateEvent;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        //todo aggiorna la risorsa sul client

        return null;
    }
}
