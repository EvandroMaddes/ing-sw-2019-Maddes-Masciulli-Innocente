package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;

/**
 * Event used to notify the character choice by the player
 *
 * @author Federico Innocente
 */
public class CharacterChoiceEvent extends ViewControllerEvent {
    /**
     * Is the chosen character
     */
    private Character chosenCharacter;

    /**
     * Constructor
     *
     * @param user            is the player username
     * @param chosenCharacter is the chosen character
     */
    public CharacterChoiceEvent(String user, Character chosenCharacter) {
        super(user);
        this.chosenCharacter = chosenCharacter;
    }

    /**
     * Getter method
     *
     * @return the chosen character
     */
    public Character getChosenCharacter() {
        return chosenCharacter;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().addPlayer(getUser(), chosenCharacter);
    }
}
