package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

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

    /**
     * Constructor
     * @param user the "Master" Client user
     * @param map  the chosen map
     */
    public GameChoiceEvent(String user, int map){
        super(user);
        this.map=map;
    }

    public int getMap() {
        return map;
    }

    @Override
    public void performAction(Controller controller) {
        controller.createGameManager(map);
    }
}
