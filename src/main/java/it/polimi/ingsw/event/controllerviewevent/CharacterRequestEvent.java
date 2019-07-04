package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message for the character request
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class CharacterRequestEvent extends ControllerViewEvent {
    /**
     * List of available character
     */
    private ArrayList<Character> availableCharacters;

    /**
     * Constructor
     *
     * @param user                is the player username
     * @param availableCharacters list of available character
     */
    public CharacterRequestEvent(String user, ArrayList<Character> availableCharacters) {
        super(user);
        this.availableCharacters = availableCharacters;
    }

    /**
     * Getter method
     *
     * @return the list of available character
     */
    public ArrayList<Character> getAvailableCharacter() {
        return availableCharacters;
    }

    /**
     * performAction implementation: ask to the player which character he want to play
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an CharacterChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.characterChoice(getAvailableCharacter());

    }
}
