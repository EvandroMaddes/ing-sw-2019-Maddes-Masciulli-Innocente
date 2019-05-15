package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * represent the selected Action after a request
 */
public class ActionChoiceEvent extends ViewControllerEvent {

    private int action;

    /**
     * action:
     * 1 = move
     * 2 = grab
     * 3 = shot
     *
     * Constructor
     * @param user the Client user
     * @param action the chosen action encoding
     *
     */
    public ActionChoiceEvent(String user, int action){
        super(user);
        this.action = action;
    }

    @Override
    public void performAction(Controller controller) {
        switch (action){
            case 1:{
                controller.getGameManager().getCurrentRound().getActionManager().sendPossibleMoves();
                break;
            }
            case 2:{
                controller.getGameManager().getCurrentRound().getActionManager().sendPossibleGrabs();
                break;
            }
            case 3:{
                controller.getGameManager().getCurrentRound().getActionManager().sendPossibleWeapons();
                break;
            }
            default:{
                //todo se da un opzione non valida passa il turno, sarebbe buono richiedere
                controller.getGameManager().getCurrentRound().nextPhase();
            }
        }
    }
}
