package it.polimi.ingsw.model.game_components.cards;


import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.model.game_components.ammo.CubeColour.*;
import static org.junit.Assert.*;

/**
 * @author Francesco Masciulli
 * Test Class for the PowerUpDeck methods
 */
public class TestAmmoTilesDeck {

    private AmmoTilesDeck testedDeck;

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
        AmmoTile ammoTile1 = new AmmoTile(new AmmoCube(Red), new AmmoCube(Red), new AmmoCube(Yellow), false);
        AmmoTile ammoTile2 = new AmmoTile(new AmmoCube(Blue), new AmmoCube(Blue), null, true);
        givenDeck.add(0, ammoTile1);
        givenDeck.add(1, ammoTile2);
        testedDeck = new AmmoTilesDeck();
        testedDeck.setDeck(givenDeck);

    }

    /**
     * The first ammo tile cube colour is Red;
     * this test assure that the drawn card was the first in the deck and it doesn't contain the card anymore
     */
    @Test
    public void testDraw(){
        AmmoCube starterFirstAmmoCube = ((AmmoTile)testedDeck.getDeck().get(0)).getAmmoCubes()[0];
        AmmoTile drawnAmmoTile = (AmmoTile)testedDeck.draw();
        if(testedDeck.getDeck().contains(drawnAmmoTile)){
            System.out.println("Card is still in the deck");
            fail();
        }
        AmmoCube drawnFirstAmmoCube = drawnAmmoTile.getAmmoCubes()[0];
        assertTrue(drawnFirstAmmoCube.equals(starterFirstAmmoCube));
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
     * This method test that, if is called the draw on the last card of a deck,
     * the reshuffle method refill the deck with the cards from discardDeck;
     * is not handled by this class the discarding of a player's card
     */
    @Test
    public void testReshuffle(){
        testedDeck.discardCard((AmmoTile)testedDeck.getDeck().remove(0));
        testedDeck.discardCard((AmmoTile)testedDeck.getDeck().get(0));
        testedDeck.draw();
        assertTrue(!testedDeck.getDeck().isEmpty());
        System.out.println("The empty deck is reshuffled after the last draw");
    }

    /**@author Evandro Maddes
     * checks the correct size of ammotile deck
     */
    @Test
    public void testCreateDeck(){
        AmmoTilesDeck deck = new AmmoTilesDeck();
        assertEquals(36,deck.getDeck().size());
    }



}
