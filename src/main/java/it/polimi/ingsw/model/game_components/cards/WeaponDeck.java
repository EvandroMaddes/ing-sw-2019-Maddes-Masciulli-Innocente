package it.polimi.ingsw.model.game_components.cards;
/**
 * @author Francesco Masciulli
 * Class implementing the Weapon Deck
 */



public class WeaponDeck extends DeckManagement {

    /**
     *
     * @return The first Weapon Card element from the Deck, if the last one is not empty;
     * the super method could throw an exception that will be casted!
     */
    @Override
    public Object draw() {
        Weapon drawnCard = (Weapon)draw();
        return drawnCard;
    }


}
