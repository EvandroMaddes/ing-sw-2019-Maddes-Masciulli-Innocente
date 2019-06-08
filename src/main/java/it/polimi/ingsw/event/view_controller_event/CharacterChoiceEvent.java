package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;

/**
 * event used to notify the character choice by the player
 */
public class CharacterChoiceEvent extends ViewControllerEvent {
    private Character chosenCharacter;

    public CharacterChoiceEvent(String user, Character chosenCharacter) {
        super(user);
        this.chosenCharacter = chosenCharacter;
    }

    public Character getChosenCharacter() {
        return chosenCharacter;
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().addPlayer(getUser(), chosenCharacter);
    }
}
