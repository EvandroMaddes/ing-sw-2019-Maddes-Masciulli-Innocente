package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

/**
 * @author Francesco Masciulli
 * represent the card selected by the user after a Request
 */
public abstract class CardChoiceEvent extends ViewControllerEvent {

    private String card;


    /**
     * Constructor
     *
     * @param user     the Client user
     * @param card     the selected Card
     */
    public CardChoiceEvent(String user, String card) {
        super(user);
    }

    public String getCard() {
        return card;
    }


}
