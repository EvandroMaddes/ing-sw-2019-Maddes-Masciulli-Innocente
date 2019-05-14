package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * represent the card selected by the user after a Request
 */
public class CardChoiceEvent extends Event {

    private String card;
    private String cardType;
    private String cardColour;

    /**
     * Constructor
     *
     * @param user     the Client user
     * @param card     the selected Card
     * @param cardType the selected Card's type
     */
    public CardChoiceEvent(String user, String card, String cardType, String cardColour) {
        super(user);
        this.card = card;
        this.cardType = cardType;
        this.cardColour = cardColour;

    }

    public String getCard() {
        return card;
    }

    public String getCardColour() {
        return cardColour;
    }
}
