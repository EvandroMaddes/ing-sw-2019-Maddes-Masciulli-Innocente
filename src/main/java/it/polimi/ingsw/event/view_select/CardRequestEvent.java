package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

import java.util.ArrayList;

/**This class represent a Card Request from the Server to a Client
 * it could be used to present a list of Weapons or PowerUps
 * the user must select one of this Card
 *
 * @author Francesco Masciulli
 */
public class CardRequestEvent extends Event {

    private ArrayList<String> cards;
    private String cardType;
    private ArrayList<CubeColour> colour;

    /**
     * Constructor
     * @param user the Client user
     * @param cards the cardList from which the user must chose
     * @param cardType the type of cards that are sent
     * @param colour the colour of each card
     */
    public CardRequestEvent(String user, ArrayList<String> cards, String cardType, ArrayList<CubeColour> colour){
        super(user);
        this.cards=cards;
        this.cardType=cardType;
        this.colour=colour;
        type= EventType.CardRequestEvent;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public String getCardType() {
        return cardType;
    }

    public ArrayList<CubeColour> getColour() {
        return colour;
    }
}
