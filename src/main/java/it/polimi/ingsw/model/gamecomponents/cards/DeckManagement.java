package it.polimi.ingsw.model.gamecomponents.cards;

import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * Abstract class for the Deck Management;
 * implements the basic methods of drawing and shuffling further the Constructor and getter methods;
 * @author Francesco Masciulli
 *
 */
public abstract class DeckManagement {

    /**
     * Is the ArrayList of Object that contains the deck elements
     */
    private ArrayList<Object> deck ;

    /**
     * Setter method:
     * @param deck is the standard ArrayList given in the initializing phase of the game
     *
     */
    void setDeck(ArrayList<Object> deck) {
        this.deck = deck;
    }

    /**
     * Getter method:
     * @return the deck
     */
    public ArrayList<Object> getDeck() {
        return deck;
    }

    /**
     * Using the java.util.Collection, the method randomly shuffle the deck elements
     */
    void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * perform a draw from the deck
     * @return the first Deck element, if is not empty, or the following exception, null if the deck is empty
     */
    public Object draw() {
        Object drawnCard;
        if (deck.isEmpty())
            return null;
        else {
            drawnCard = deck.get(0);
            deck.remove(0);
            return drawnCard;
        }
    }


}
