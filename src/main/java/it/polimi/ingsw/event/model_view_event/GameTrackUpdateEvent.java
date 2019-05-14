package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * represent a GameTrack Update, when a player is killed
 *  or  (DominationMod) when a SpawnSquare is damaged
 */
public class GameTrackUpdateEvent extends Event {
    private int damageTokenNumber;

    /**
     * Constructor
     * @param user the Client user
     * @param mapUpdate must be "MAPUPDATE"
     * @param damageTokenNumber the number of token that will be placed
     */
    public GameTrackUpdateEvent(String user, String mapUpdate, int damageTokenNumber){
        super(user,mapUpdate);
        this.damageTokenNumber=damageTokenNumber;
        type= EventType.GameTrackUpdateEvent;
    }
}
