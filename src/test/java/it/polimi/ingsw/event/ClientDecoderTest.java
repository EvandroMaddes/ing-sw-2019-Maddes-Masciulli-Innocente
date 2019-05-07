package it.polimi.ingsw.event;

import it.polimi.ingsw.event.coder.ClientDecoder;
import it.polimi.ingsw.event.view_select.PlayerRequestEvent;
import it.polimi.ingsw.model.player.Character;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;

public class ClientDecoderTest {

    private ClientDecoder testedClientDecoder;
    private String user;
    private Iterator iterator;

    @Before
    public void setUp(){
        user="TestUser";
    }

  /*  @Test
    public void testDecodePlayerRequestEvent(){
        ArrayList<Character> expectedCharacters = new ArrayList<>();
        expectedCharacters.add(Character.VIOLET);
        expectedCharacters.add(Character.BANSHEE);
        boolean [] testedTargets = {false, true, false, true, false};
        PlayerRequestEvent testedMessage = new PlayerRequestEvent(user, testedTargets, 1);
        ArrayList<Character> testedCharacters = testedClientDecoder.decodePlayerRequestEvent(testedMessage, characterIntegerMap);
        for (Character expectedCharacter : expectedCharacters) {
            Assert.assertTrue(testedCharacters.contains(expectedCharacter));
        }
        System.out.println("Tested PlayerRequest decoding");

    }

   */
}
