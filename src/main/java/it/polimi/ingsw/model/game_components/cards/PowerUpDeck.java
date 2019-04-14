package it.polimi.ingsw.model.game_components.cards;

import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * Class implementing the PowerUp Deck
 */
public class PowerUpDeck extends DeckManagement {

    private ArrayList<PowerUp> discardDeck;

    /**
     *
     * todo implement the method that add a discarded element in the discard Deck;
     */
    public void discardCard(PowerUp discardedPowerUp) {

    }

    /**
     *
     * @return The first Weapon Card element from the Deck, if the last one is not empty;
     * the super method couldn't throw an exception, because the reshuffle method calling prevent it
     */
    @Override
    public Object draw() {
        PowerUp drawnCard = (PowerUp)super.draw();
        if(getDeck().isEmpty()) {
            reshuffle();
        }
        return drawnCard;
    }

    /**
     * When the Deck's empty, it is filled with the discardDeck Elements end shuffled;
     */
    public void reshuffle()
    {
        setDeck((ArrayList<Object>)discardDeck.clone());
        shuffle();
        discardDeck.clear();
    }
}
