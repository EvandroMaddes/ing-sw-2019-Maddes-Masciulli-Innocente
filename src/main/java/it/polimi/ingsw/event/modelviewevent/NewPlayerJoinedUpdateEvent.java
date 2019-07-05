package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * When a new client enter the match and chose the character, is shown to all the other connected clients
 */
public class NewPlayerJoinedUpdateEvent extends ModelViewBroadcastEvent {

    /**
     * Is the new player username
     */
    private String newPlayer;
    /**
     * is the Character chosen by the new player
     */
    private Character characterChoice;

    /**
     * Constructor: call super-class constructor and set newPlayer and characterChoice values
     *
     * @param username        is the new player username
     * @param characterChoice is the new player's chosen character
     */
    public NewPlayerJoinedUpdateEvent(String username, Character characterChoice) {
        super();
        this.newPlayer = username;
        this.characterChoice = characterChoice;
    }

    /**
     * Implements the performAction: notify the character choice
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.newPlayerJoinedUpdate(newPlayer, characterChoice);
    }
}
