package it.polimi.ingsw.model.gamecomponents.cards;


import it.polimi.ingsw.model.gamecomponents.ammo.AmmoTile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Francesco Masciulli
 * Test Class for the PowerUpDeck methods
 */
public class AmmoTilesDeckTest {

    private AmmoTilesDeck testedDeck;

    /**
     * method that set up the Test Class;
     *simulates, with a restricted number of elements, the initializing of the deck.
     */
   @Before
    public void setUp(){
        testedDeck = new AmmoTilesDeck();

    }

    /**
     * The first ammo tile cube colour is Red;
     * this test assure that the drawn card was the first in the deck and it doesn't contain the card anymore
     */
    @Test
    public void testDraw(){
        AmmoTile firstCard = (AmmoTile) testedDeck.getDeck().get(0);
        assertTrue(testedDeck.draw()==firstCard);
        System.out.println("The drawn AmmoTile's first cube was the first of the deck's AmmoTile");

    }

    /**
     * It tests the shuffling of the initialized deck;
     * in this test class there are just two cards so there will be the same order with 1 case in 2;
     * is also indirectly tested the casting and methods calls from the DeckManagement to the specified card class.
     *
     */
    @Test
    public void testShuffle(){
        ArrayList<Object> oldDeck = (ArrayList<Object>) testedDeck.getDeck().clone();
        try{
            testedDeck.shuffle();
        } catch(UnsupportedOperationException notValidList){
            fail();
        }
        // in questo test sono istanziate 2 carte nel deck, quindi la probabilità è 0.5
        AmmoTile oldAmmoTile = (AmmoTile)oldDeck.get(0);
        AmmoTile testedAmmoTile = (AmmoTile)testedDeck.getDeck().get(0);
        if(oldAmmoTile.equals(testedAmmoTile)){
            System.out.println("The decks have the same first element");
        }
        else{
            assertNotEquals(oldAmmoTile,testedAmmoTile);
            System.out.println("The decks have different first element");
        }

    }

    /**
     * @author Evandro Maddes
     * This method test that, if is called the draw on the last card of a deck,
     * the reshuffle method refill the deck with the cards from discardDeck;
     * is not handled by this class the discarding of a player's card
     */
    @Test
    public void testReshuffle(){
        for (Object ammoTile:testedDeck.getDeck()
             ) {
            testedDeck.discardCard((AmmoTile)ammoTile);
        }
        AmmoTile drawedCard = (AmmoTile) testedDeck.draw();

        assertFalse(testedDeck.getDeck().contains(drawedCard));
        assertTrue(testedDeck.getDiscardDeck().contains(drawedCard));
        assertEquals(35,testedDeck.getDeck().size());
    }

    /**
     * @author Evandro Maddes
     * checks if one card is drawed, this one is added on discard deck and removed from deck
     */
    @Test
    public void testDiscardDeck() {
        AmmoTile discardCard = (AmmoTile) testedDeck.getDeck().remove(testedDeck.getDeck().size()-1);
        testedDeck.discardCard(discardCard);
        assertTrue(testedDeck.getDiscardDeck().contains(discardCard));
        assertTrue(!testedDeck.getDeck().contains(discardCard));
        assertEquals(1, testedDeck.getDiscardDeck().size());
        assertEquals(35, testedDeck.getDeck().size());
    }

        /**
         * @author Evandro Maddes
         * checks the correct size of ammotile deck
         */
    @Test
    public void testNumberOfCard(){

        assertEquals(36,testedDeck.getDeck().size());


    }



}
