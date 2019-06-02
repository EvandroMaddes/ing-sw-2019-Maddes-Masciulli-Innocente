package it.polimi.ingsw.model.game_components.cards;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WeaponDeckTest {

    private  WeaponDeck testedDeck ;

    @Before
    public void setup(){
        testedDeck = new WeaponDeck();
    }
/*

    /**
     * @author Evandro Maddes
     * checks the correct size of ammotile deck
     */
/*    @Test
    public void testNumberOfCard(){

        assertEquals(21,testedDeck.getDeck().size());


    }

    @Test
    /**@author Evandro Maddes
     * checks if weapon is different from others
     */
/*    public void testDifferentWeapon(){
        WeaponDeck deck = new WeaponDeck();
        for (int i=0; i< deck.getDeck().size(); i++) {
            for (int j = 0; j < deck.getDeck().size() && i!=j; j++) {
                assertFalse((((Weapon) deck.getDeck().get(i)).getName().equals(((Weapon) deck.getDeck().get(j)).getName())));
                assertFalse(((Weapon) deck.getDeck().get(i)) == ((Weapon) deck.getDeck().get(j)));
            }

        }
    }

    /**
     * @author Evandro Maddes
     * checks if the fisrt card of the deck is drawed correctly
     */
/*    @Test
    public void testDraw(){
        Weapon firstCard = (Weapon) testedDeck.getDeck().get(0);
        System.out.println(testedDeck.getDeck().size());
        assertTrue(firstCard == testedDeck.draw());
    }

    /**
     * This method test that, after is called a draw on an empty deck, the returned object is an Exception
     *
     */
/*    @Test
    public void testDrawEmptyDeck() {
        int size = testedDeck.getDeck().size();
        for (int i=0; i<size;i++) {
          testedDeck.draw();
        }
        Object returned =  testedDeck.draw();
        assertEquals(((IndexOutOfBoundsException)returned).getMessage(), new IndexOutOfBoundsException("Index: 0, Size: 0").getMessage());


    } */
}
