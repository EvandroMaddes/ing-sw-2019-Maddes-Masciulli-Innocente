package it.polimi.ingsw.event;

import it.polimi.ingsw.event.coder.ClientDecoder;
import it.polimi.ingsw.event.view_select.CardRequestEvent;
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
    public void testDecodeCardRequestEvent(){
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
}
