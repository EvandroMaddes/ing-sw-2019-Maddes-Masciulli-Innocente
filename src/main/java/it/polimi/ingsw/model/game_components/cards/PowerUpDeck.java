package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.power_ups.Newton;
import it.polimi.ingsw.model.game_components.cards.power_ups.TagbackGrenade;
import it.polimi.ingsw.model.game_components.cards.power_ups.TargetingScope;
import it.polimi.ingsw.model.game_components.cards.power_ups.Teleporter;

import java.util.ArrayList;
import java.util.Collections;

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

    /**
     * @author Evandro Maddes
     * It creates the powerUp deck and it shuffles it
     */
    public PowerUpDeck(){


        ArrayList<Object> deck = new ArrayList<Object>();

        addPowerupColour(deck,CubeColour.Blue);
        addPowerupColour(deck,CubeColour.Yellow);
        addPowerupColour(deck,CubeColour.Red);


         Collections.shuffle(deck);
        this.setDeck(deck);

    }

    /**
     * @author Evandro Maddes
     * While power up deck is partially created, this method addss powers Up by colour
     * @param currentDeck Power Up deck partially created
     * @param colour colour of power up
     * @return power up deck within power up of spicified colour
     */
    private ArrayList<Object> addPowerupColour(ArrayList<Object> currentDeck, CubeColour colour){

        for(int i=0; i<2; i++){
            PowerUp currentTagbackGranade = new TagbackGrenade(colour, "TagbackGranade");
            currentDeck.add(currentTagbackGranade);
            PowerUp currentTargetingScope = new TargetingScope(colour, "TargetingScope");
            currentDeck.add(currentTargetingScope);
            PowerUp currentTeleporter = new Teleporter(colour, "Teleporter");
            currentDeck.add(currentTeleporter);
            PowerUp currentNewton = new Newton(colour, "Newton");
            currentDeck.add(currentNewton);
        }
        return currentDeck;
    }

    public ArrayList<PowerUp> getDiscardDeck() {
        return discardDeck;
    }
}
