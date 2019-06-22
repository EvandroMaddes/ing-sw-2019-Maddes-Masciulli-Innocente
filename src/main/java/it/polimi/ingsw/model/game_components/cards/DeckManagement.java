package it.polimi.ingsw.model.game_components.cards;

import java.util.ArrayList;
import java.util.Collections;
/**
 * @author Francesco Masciulli
 *
 * Abstract class for the Deck Management;
 * implements the basic methods of drawing and shuffling further the Constructor and getter;
 *
 */
public abstract class DeckManagement {

    private ArrayList<Object> deck ;

    /**
     * Setter method
     * @param deck is the standard ArrayList given in the initializing phase of the game
     *
     */
    public void setDeck(ArrayList<Object> deck) {
        this.deck = deck;
    }

    /**
     * Getter method
     * @return the deck
     */
    public ArrayList<Object> getDeck() {
        return deck;
    }

    /**
     * Using the java.util.Collection, the method randomly shuffle the deck elements
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     *
     * @return the first Deck element, if is not empty, or the following exception
     * @throws IndexOutOfBoundsException if the deck is an empty ArrayList it must be handled by the calling method
     */

    //Da controllare il caso in cui verrà passata l'eccezione e le sottoclassi la casteranno
    public Object draw() throws IndexOutOfBoundsException{
        Object drawnCard = new Object();
        try {
            drawnCard = deck.get(0);
            deck.remove(0);
            return drawnCard;
        }
        catch(IndexOutOfBoundsException emptyDeckException){
            return  emptyDeckException;
        }

    }


}
