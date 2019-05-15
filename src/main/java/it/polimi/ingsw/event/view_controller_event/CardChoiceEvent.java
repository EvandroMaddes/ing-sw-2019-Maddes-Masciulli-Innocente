package it.polimi.ingsw.event.view_controller_event;

/**
 * @author Francesco Masciulli
 * represent the card selected by the user after a Request
 */
public abstract class CardChoiceEvent extends ViewControllerEvent {

    private String card;
    private String cardColour;

    /**
     * Constructor
     *
     * @param user     the Client user
     * @param card     the selected Card
     */
    public CardChoiceEvent(String user, String card, String cardColour) {
        super(user);
        this.card = card;
        this.cardColour = cardColour;
    }

    public String getCard() {
        return card;
    }

    public String getCardColour() {
        return cardColour;
    }
}
