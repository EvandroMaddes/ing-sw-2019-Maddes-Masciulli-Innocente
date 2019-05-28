package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * represent the User preferences about map and mod before a new game initialization
 *
 * map coding is:
 *  0: FirstLeft,FirstRight (bigger)
 *  1: FirstLeft, SecondRight (medium1)
 *  2: SecondLeft, FirstRight (medium2)
 *  3: SecondLeft, SeconfRIght (smaller)
 * mod coding is:
 *  0: KillShot mod
 *  1: Domination mod
 *
 */
public class GameChoiceEvent extends ViewControllerEvent {

    private int map;
    private int mod;

    /**
     * Constructor
     * @param user the "Master" Client user
     * @param map  the chosen map
     * @param mod  the chosen mod
     */
    public GameChoiceEvent(String user, int map, int mod){
        super(user);
        this.map=map;
        this.mod=mod;
    }

    public int getMap() {
        return map;
    }

    public int getMod() {
        return mod;
    }

    @Override
    public void performAction(Controller controller) {
        controller.createGameManager(map);
    }
}
