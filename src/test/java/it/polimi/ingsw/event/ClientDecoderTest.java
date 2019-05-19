package it.polimi.ingsw.event;

import it.polimi.ingsw.event.coder.ClientDecoder;
import it.polimi.ingsw.event.view_select.CardRequestEvent;
import it.polimi.ingsw.event.view_select.PositionRequestEvent;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class ClientDecoderTest {

    private ClientDecoder testedClientDecoder;
    private String testUser;
    private Iterator iterator;

    @Before
    public void setUp(){
        testedClientDecoder = new ClientDecoder();
        testUser="TestUser";
    }

    @Test
    public void decodePowerUpRequestTest(){
        ArrayList<String> testPowerUpStrings = new ArrayList<>();
        ArrayList<CubeColour> testColours = new ArrayList<>();
        testColours.add(CubeColour.Blue);
        testColours.add(CubeColour.Yellow);
        testPowerUpStrings.add("Teleporter");
        testPowerUpStrings.add("Newton");
        Event testMessage = new CardRequestEvent(testUser, testPowerUpStrings, "PowerUp", testColours);
        ArrayList<Card> decodedCards = testedClientDecoder.decodeCardRequestEvent((CardRequestEvent)testMessage);
        Assert.assertEquals(CubeColour.Blue, decodedCards.get(0).getColour());
        Assert.assertEquals("Newton", decodedCards.get(1).getName());
        System.out.println("Tested CardRequestEvent decoding");
    }

    @Test
    public void decodeWeaponRequestTest(){
        ArrayList<String> weaponString = new ArrayList<>();
        weaponString.add("FURNACE");
        weaponString.add("HELLION");
        ArrayList<CubeColour> testColours = new ArrayList<>();
        testColours.add(CubeColour.Blue);
        testColours.add(CubeColour.Yellow);
        Event messageTest = new CardRequestEvent("Evandro",weaponString,"Weapon",testColours);

        ArrayList<Card> decodedCards = testedClientDecoder.decodeCardRequestEvent((CardRequestEvent) messageTest);

        Assert.assertEquals(weaponString.get(0),decodedCards.get(0).getName());
        Assert.assertEquals(weaponString.get(1),decodedCards.get(1).getName());

    }

    @Test
    public void decodePositionRequestTest(){
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> columns = new ArrayList<>();
        rows.add(1);// basicSquare
        columns.add(3);
        rows.add(2); //spawnSquare
        columns.add(2);

        Event messageTest = new PositionRequestEvent("Evandro",rows,columns);

        ArrayList<Square> possibleSquare = testedClientDecoder.decodePositionRequestEvent((PositionRequestEvent) messageTest);

        Assert.assertEquals(1,possibleSquare.get(0).getRow());
        Assert.assertEquals(3,possibleSquare.get(0).getColumn());

        Assert.assertEquals(2,possibleSquare.get(1).getRow());
        Assert.assertEquals(2,possibleSquare.get(1).getColumn());
        Assert.assertTrue(possibleSquare.get(1) instanceof SpawnSquare);

    }
}
