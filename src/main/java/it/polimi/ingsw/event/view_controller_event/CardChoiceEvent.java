package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * represent the card selected by the user after a Request
 */
public class CardChoiceEvent extends Event {

    private String card;
    private String type;

    /**
     * Constructor
     * @param user  the Client user
     * @param card  the selected Card
     * @param type  the selected Card's type
     */
    public CardChoiceEvent(String user, String card, String type){
        super(user);
        this.card=card;
        this.type=type;
    }
}
