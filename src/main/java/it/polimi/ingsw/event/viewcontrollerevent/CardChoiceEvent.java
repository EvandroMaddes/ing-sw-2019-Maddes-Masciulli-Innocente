package it.polimi.ingsw.event.viewcontrollerevent;


/**
 * Message to notify the controller about a generic card choice
 *
 * @author Federico Innocente
 * @author Franesco Masciulli
 */
public abstract class CardChoiceEvent extends ViewControllerEvent {

    /**
     * Is the name of the chosen card
     */
    private String card;


    /**
     * Constructor
     *
     * @param user the Client user
     * @param card the selected Card name
     */
    CardChoiceEvent(String user, String card) {
        super(user);
        this.card = card;
    }

    /**
     * Getter method
     *
     * @return the chosen card
     */
    public String getCard() {
        return card;
    }


}
