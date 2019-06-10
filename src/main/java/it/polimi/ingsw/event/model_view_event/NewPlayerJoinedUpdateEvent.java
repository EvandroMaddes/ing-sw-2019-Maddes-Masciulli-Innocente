package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * Quando si connette un nuovo giocatore, viene mostrato a tutti i client connessi;
 */
public class NewPlayerJoinedUpdateEvent extends ModelViewEvent {

    String newPlayer;
    Character characterChoice;

    public NewPlayerJoinedUpdateEvent(String username, Character characterChoice){
        super();
        this.newPlayer = username;
        this.characterChoice = characterChoice;
    }


    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.newPlayerJoinedUpdate(newPlayer, characterChoice);
    }
}
