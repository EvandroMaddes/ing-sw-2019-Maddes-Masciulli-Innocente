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
    //by now, is given by the ServerEncoder, already tested;
    private EnumMap<Character, Integer> characterIntegerMap = new EnumMap<>(Character.class);

    @Before
    public void setUp(){
        testedClientDecoder =new ClientDecoder();
        user="TestUser";
        characterIntegerMap.put(Character.D_STRUCT_OR,0);
        characterIntegerMap.put(Character.BANSHEE,1);
        characterIntegerMap.put(Character.DOZER,2);
        characterIntegerMap.put(Character.VIOLET,3);
        characterIntegerMap.put(Character.SPROG,4);

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
