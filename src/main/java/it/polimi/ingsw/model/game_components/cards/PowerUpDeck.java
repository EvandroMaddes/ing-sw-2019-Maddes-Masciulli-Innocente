package it.polimi.ingsw.model.game_components.cards;

import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * Class implementing the PowerUp Deck
 */
public class PowerUpDeck extends DeckManagement {

    private ArrayList<PowerUp> discardDeck = new ArrayList<>();

    /**
     * this method add a discarded PowerUp Card in the discardDeck, used by reshuffle method
     * @param discardedPowerUp is the card that a player discard (if he grab the 4th one)
     */
    public void discardCard(PowerUp discardedPowerUp) {
        discardDeck.add(discardedPowerUp);
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
