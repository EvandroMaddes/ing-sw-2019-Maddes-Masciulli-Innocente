package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.utils.Decoder;

import java.util.ArrayList;

public class GenericPayChoiceEvent extends ViewControllerEvent {
    private boolean[] ammoChoice;
    private String powerUpType;
    private CubeColour powerUpColour;

    public GenericPayChoiceEvent(String user, boolean[] ammoChoice, String powerUpType, CubeColour powerUpColour) {
        super(user);
        this.ammoChoice = ammoChoice;
        this.powerUpType = powerUpType;
        this.powerUpColour = powerUpColour;
    }

    @Override
    public void performAction(Controller controller) {
        if (ammoChoice[0])
            controller.getGameManager().getCurrentRound().getActionManager().payCost(new int[]{1,0,0}, new ArrayList<>());
        else if (ammoChoice[1])
            controller.getGameManager().getCurrentRound().getActionManager().payCost(new int[]{0,1,0}, new ArrayList<>());
        else if (ammoChoice[2])
            controller.getGameManager().getCurrentRound().getActionManager().payCost(new int[]{0,0,1}, new ArrayList<>());
        else {
            PowerUp choice = Decoder.decodePowerUp(controller.getGameManager().getCurrentRound().getCurrentPlayer(), powerUpType, powerUpColour);
            ArrayList<PowerUp> choiceAsList = new ArrayList<>();
            choiceAsList.add(choice);
            controller.getGameManager().getCurrentRound().getActionManager().payCost(new int[]{0, 0, 0}, choiceAsList);
        }
        controller.getGameManager().getCurrentRound().getActionManager().askTargetTargetingScope();
    }
}
