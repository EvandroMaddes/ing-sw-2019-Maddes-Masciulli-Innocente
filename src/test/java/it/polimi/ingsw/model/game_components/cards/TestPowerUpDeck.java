package it.polimi.ingsw.model.game_components.cards;


import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Francesco Masciulli
 * Test Class for the PowerUpDeck methods
 */
public class TestPowerUpDeck {

    private PowerUpDeck testedDeck;

    /**
     * method that set up the Test Class;
     *simulates, with a restricted number of elements, the initializing of the deck.
     */
    @Before
    public void setUp(){
        //il metodo setDeck è della classe astratta DeckManagement;
        //necessita di un parametro di tipo ArrayList<Object>
        //bisognerà fare casting dei parametri in fase di init
        ArrayList<Object> givenDeck = new ArrayList<>();
        Card powerUp1 = new Newton(CubeColour.Red);
        Card powerUp2 = new Teleporter(CubeColour.Blue);
        givenDeck.add(0, powerUp1);
        givenDeck.add(1, powerUp2);
        testedDeck = new PowerUpDeck();
        testedDeck.setDeck(givenDeck);
    }


    /**
     * The first card's name is initialized as "Newton";
     * this test assure that the drawn card was the first in the deck and it doesn't contain the card anymore
     */
    @Test
    public void testDraw(){
        String starterCardName = ((Card)testedDeck.getDeck().get(0)).getName();
        Card drawnCard = (Card)testedDeck.draw();
        if(testedDeck.getDeck().contains(drawnCard)){
            System.out.println("Card is still in the deck");
            fail();
        }
        assertEquals(starterCardName, drawnCard.getName());
        System.out.println("The drawn card was the first of the deck");

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
        PowerUp oldPowerUp = (PowerUp) oldDeck.get(0);
        PowerUp testedPowerUp = (PowerUp)testedDeck.getDeck().get(0);
        if(oldPowerUp.getName()==testedPowerUp.getName()){
            System.out.println("The decks have the same first element");
        }
        else{
            assertNotEquals(oldPowerUp.getName(),testedPowerUp.getName());
            System.out.println("The decks have different first element");
        }

    }

    /**
     * This method test that, if is called the draw on the last card of a deck,
     * the reshuffle method refill the deck with the cards from discardDeck;
     * is not handled by this class the discarding of a player's card
     */
    @Test
    public void testReshuffle(){
        testedDeck.discardCard((PowerUp)testedDeck.getDeck().remove(0));
        testedDeck.discardCard((PowerUp)testedDeck.getDeck().get(0));
        testedDeck.draw();
        assertTrue(!testedDeck.getDeck().isEmpty());
        System.out.println("The empty deck is reshuffled after the last draw");
    }

}
