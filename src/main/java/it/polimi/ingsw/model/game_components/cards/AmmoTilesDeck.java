package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoTile;

import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * Class implementing the Ammo Tiles Deck
 */

public class AmmoTilesDeck extends DeckManagement {

    private ArrayList<AmmoTile> discardDeck = new ArrayList<>();

    /**
     * this method add a discarded AmmoTile in the discardDeck, used by reshuffle method
     * @param discardedAmmoTile is the card that a player discard (if he grab the 4th one)
     */
    public void discardCard(AmmoTile discardedAmmoTile) {
        discardDeck.add(discardedAmmoTile);
    }

    /**
     *
     @return the first Weapon Card element from the Deck, if the last one is not empty;
      * the super method couldn't throw an exception, because the reshuffle method calling prevent it
     */
    @Override
    public Object draw() {
        AmmoTile drawnCard = (AmmoTile)super.draw();
        if(getDeck().isEmpty()) {
            reshuffle();
        }
        return drawnCard;
    }

    /**
     * When the Deck's empty, it is filled with the discardDeck Elements end shuffled;
     */
    public void reshuffle() {
        setDeck((ArrayList<Object>)discardDeck.clone());
        shuffle();
        discardDeck.clear();
    }

}
