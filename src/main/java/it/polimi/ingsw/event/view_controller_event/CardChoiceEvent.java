package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.EventType;

/**
 * @author Francesco Masciulli
 * represent the card selected by the user after a Request
 */
public class CardChoiceEvent extends Event {

    private String card;
    private String cardType;
    private String cardColour;
    private String context;

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
        type = EventType.CardChoiceEvent;

    }

    public String getCard() {
        return card;
    }

    public String getContext() {
        return context;
    }

    public String getCardColour() {
        return cardColour;
    }
}
