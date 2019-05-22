package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class WeaponTargetRequestEvent extends CharacterRequestEvent {
    public WeaponTargetRequestEvent(String user, ArrayList<Character> availableCharacters) {
        super(user, availableCharacters);
    }

    //todo occhio va reimplementato?
    @Override
    public Event performAction(RemoteView remoteView) {
        return super.performAction(remoteView);
    }
}
