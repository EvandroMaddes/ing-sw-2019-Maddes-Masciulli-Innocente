package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

/**
 * @author Francesco Masciulli
 * represent the card selected by the user after a Request
 */
public abstract class CardChoiceEvent extends ViewControllerEvent {

    private String card;
    private CubeColour cardColour;

    /**
     * Constructor
     *
     * @param user     the Client user
     * @param card     the selected Card
     */
    public CardChoiceEvent(String user, String card, CubeColour cardColour) {
        super(user);
        this.card = card;
        this.cardColour = cardColour;
    }

    public String getCard() {
        return card;
    }

    public CubeColour getCardColour() {
        return cardColour;
    }
}
