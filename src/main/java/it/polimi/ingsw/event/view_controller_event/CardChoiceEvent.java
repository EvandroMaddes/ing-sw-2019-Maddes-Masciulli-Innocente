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

    /**
     * Constructor
     * @param user  the Client user
     * @param card  the selected Card
     * @param cardType  the selected Card's type
     */
    public CardChoiceEvent(String user, String card, String cardType){
        super(user);
        this.card=card;
        this.cardType=cardType;
        type= EventType.CardChoiceEvent;

    }
}
