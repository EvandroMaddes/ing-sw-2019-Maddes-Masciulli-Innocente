package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class CharacterRequestEvent extends ControllerViewEvent {
    private ArrayList<Character> availableCharacters;

    public CharacterRequestEvent(String user, ArrayList<Character> availableCharacters) {
        super(user);
        this.availableCharacters = availableCharacters;
    }

    public ArrayList<Character> getAvailableCharacter() {
        return availableCharacters;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.characterChoice(getAvailableCharacter());

    }
}
