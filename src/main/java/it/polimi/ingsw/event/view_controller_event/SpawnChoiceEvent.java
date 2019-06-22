package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class SpawnChoiceEvent extends CardChoiceEvent {
    private CubeColour cardColour;

    public CubeColour getCardColour() {
        return cardColour;
    }

    public SpawnChoiceEvent(String user, String card, CubeColour cardColour) {
        super(user, card);
        this.cardColour = cardColour;
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getDeathManager().spawn(getCard(), getCardColour());
    }
}
