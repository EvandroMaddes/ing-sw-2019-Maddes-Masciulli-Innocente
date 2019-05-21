package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * represent the PlayerBoard Update after a damage, a mark or a death
 */
public class PlayerBoardUpdateEvent extends ModelViewEvent {

    private PlayerBoard playerBoard;

    /**
     * Constructor
     * @param user the Updated resource
     * @param playerBoard the Updated Board
     */
    public PlayerBoardUpdateEvent(String user, PlayerBoard playerBoard){
        super(user);
        this.playerBoard=playerBoard;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        //todo aggiorna la risorsa sul client

        return null;
    }
}
