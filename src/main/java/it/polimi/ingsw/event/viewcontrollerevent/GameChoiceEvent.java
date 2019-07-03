package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Represent the User preferences about map before a new game initialization
 * Map coding is:
 * 0: Big left, Big right (bigger)
 * 1: Big left, Small right (medium1)
 * 2: Small left, Big right (medium2)
 * 3: Small left, Small right (smaller)
 *
 * @author Federico Innocente
 * @author Francesco Masciulli
 */
public class GameChoiceEvent extends ViewControllerEvent {
    /**
     * Is the codification of the chosen map
     */
    private int map;

    /**
     * Constructor
     *
     * @param user the "Master" Client user
     * @param map  the chosen map
     */
    public GameChoiceEvent(String user, int map) {
        super(user);
        this.map = map;
    }

    /**
     * Getter method
     *
     * @return the chosen map codification
     */
    public int getMap() {
        return map;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.createGameManager(map);
    }
}
