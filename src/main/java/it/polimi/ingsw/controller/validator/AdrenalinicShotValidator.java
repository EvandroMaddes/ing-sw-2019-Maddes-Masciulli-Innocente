package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;

public class AdrenalinicShotValidator extends AdrenalinicGrabValidator {

    @Override
    public boolean[] getUsableActions(Controller controller) {
        return new boolean[]{true, true, true};
    }
}
