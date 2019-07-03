package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;


/**
 * inviato se non si vuole effettuare un Azione (PowerUp o Azioni o Ricarica)
 */
public class SkipActionChoiceEvent extends ViewControllerEvent {

    public SkipActionChoiceEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().nextPhase();
    }
}
