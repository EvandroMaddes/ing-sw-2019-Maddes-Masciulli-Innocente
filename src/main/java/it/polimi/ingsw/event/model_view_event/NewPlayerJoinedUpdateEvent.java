package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.RemoteView;

/**
 * Quando si connette un nuovo giocatore, viene mostrato a tutti i client connessi;
 */
public class NewPlayerJoinedUpdateEvent extends ModelViewEvent {


    public NewPlayerJoinedUpdateEvent(String user){
        super(user);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        //todo aggiorna la risorsa sul client

        return null;
    }
}