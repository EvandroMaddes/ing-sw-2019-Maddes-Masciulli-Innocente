package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;

/**
 * event used to notify the character choice by the player
 */
public class CharacterChoiceEvent extends Event {
    private Character choosenCharacter;

    public CharacterChoiceEvent(String user, Character choosenCharacter) {
        super(user);
        this.choosenCharacter = choosenCharacter;
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().addPlayer(getUser(), choosenCharacter);
    }
}
