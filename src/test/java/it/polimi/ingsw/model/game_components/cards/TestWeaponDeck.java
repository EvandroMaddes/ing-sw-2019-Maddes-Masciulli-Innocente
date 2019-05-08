package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Francesco Masciulli
 * Test Class for the WeaponDeck methods
 */


public class TestWeaponDeck {

    private class TestWeapon extends Weapon{
        TestWeapon(CubeColour colour, String name, AmmoCube[] reloadCost){
            super(colour, name, reloadCost);
        }

        @Override
        public void fire(ArrayList<Player> targets, Square destination, int selectedEffect) {

        }
    }
    private WeaponDeck testedDeck;


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
        Card weapon1 = new TestWeapon(CubeColour.Red, "FirstWeapon", null);
        Card weapon2 = new TestWeapon(CubeColour.Blue, "SeconWeapon", null);
        givenDeck.add(0, weapon1);
        givenDeck.add(1, weapon2);
        testedDeck = new WeaponDeck();
        testedDeck.setDeck(givenDeck);
    }

    /**
     * The first card's name is initialized as "FirstWeapon";
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
     * This method test that, after is called a draw on an empty deck, the returned object is an Exception
     *
     */
    @Test
    public void testDrawEmptyDeck() {
        ArrayList<Object> emptyDeck = new ArrayList<>();
        testedDeck.setDeck(emptyDeck);
        Object returned = testedDeck.draw();
        assertEquals(((IndexOutOfBoundsException)returned).getMessage(), new IndexOutOfBoundsException("Index: 0, Size: 0").getMessage());
        System.out.println("Catched Exception");


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
        // la probabilità di aver lo stesso ordine per tutti gli elementi è del è 1/(5,84 * 10^27)
        // sarebbero le combinazioni possibili di 21 carte arma diverse
        // in questo test sono istanziate 2 carte nel deck, quindi la probabilità è 0.5
        Weapon oldWeapon = (Weapon)oldDeck.get(0);
        Weapon testedWeapon = (Weapon)testedDeck.getDeck().get(0);
        if(oldWeapon.getName()==testedWeapon.getName()){
            System.out.println("The decks have the same first element");
        }
        else{
            assertNotEquals(oldWeapon.getName(),testedWeapon.getName());
            System.out.println("The decks have different first element");
        }

    }




}
