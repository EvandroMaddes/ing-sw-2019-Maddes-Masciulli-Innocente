package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PowerUpDeckTest {

    private PowerUpDeck testedDeck;

    @Before
    public void setUp(){
        testedDeck = new PowerUpDeck();
    }

    /**
     * @author Evandro Maddes
     * checks if the fisrt card of the deck is drawed correctly
     */
    @Test
    public void testDraw(){
        PowerUp firstCard = (PowerUp)testedDeck.getDeck().get(0);

        assertTrue(firstCard == testedDeck.draw());
    }

    /**
     * @author Evandro Maddes
     * This method test that, if is called the draw on the last card of a deck,
     * the reshuffle method refill the deck with the cards from discardDeck;
     *is not handled by this class the discarding of a player's card
     */
    @Test
    public void testReshuffle(){
        for (Object powerUp:testedDeck.getDeck()
        ) {
            testedDeck.discardCard((PowerUp) powerUp);
        }
        PowerUp drawedCard = (PowerUp) testedDeck.draw();

        assertFalse(testedDeck.getDeck().contains(drawedCard));
        assertTrue(testedDeck.getDiscardDeck().contains(drawedCard));
        assertEquals(23,testedDeck.getDeck().size());
    }

    /**
     * @author Evandro Maddes
     * checks if one card is drawed, this one is added on discard deck and removed from deck
     */
    @Test
    public void testDiscardDeck() {
        PowerUp discardCard = (PowerUp) testedDeck.getDeck().remove(testedDeck.getDeck().size()-1);
        testedDeck.discardCard(discardCard);
        assertTrue(testedDeck.getDiscardDeck().contains(discardCard));
        assertTrue(!testedDeck.getDeck().contains(discardCard));
        assertEquals(1, testedDeck.getDiscardDeck().size());
        assertEquals(23, testedDeck.getDeck().size());
    }


    /**
     * @author Evandro Maddes
     * checks the correct size of ammotile deck and discard deck
     */
    @Test
    public void testNumberOfCard(){


        assertEquals(24,testedDeck.getDeck().size());

        for (Object powerUp: testedDeck.getDeck()
             ) {
            testedDeck.discardCard((PowerUp)powerUp);

        }
        assertEquals(24,testedDeck.getDiscardDeck().size());

    }




}
