package it.polimi.ingsw.event;

import it.polimi.ingsw.event.view_controller_event.PositionChoiceEvent;
import it.polimi.ingsw.event.view_select.CardRequestEvent;
import it.polimi.ingsw.event.view_select.PlayerRequestEvent;
import it.polimi.ingsw.event.view_select.PositionRequestEvent;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Character;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class EncoderTest {
    private Encoder testedEncoder;
    private String user;

    @Before
    public void setUp(){
        testedEncoder= new Encoder();
        user = "TestUser";

    }

    @Test
    public void testEnumMap(){
        Assert.assertTrue(testedEncoder.getCharacterIntegerMap().get(Character.D_STRUCT_OR)==0);
        Assert.assertTrue( testedEncoder.getCharacterIntegerMap().get(Character.SPROG)==4);
        System.out.println("Tested EnumMap Initialization");

    }

    @Test
    public void testEncodePlayerRequestEvent(){
        //represent: D_STRUCT_OR, DOZER, SPROG;
        boolean[] expectedCharacters = {true,false,true,false,true};

        ArrayList <Character> testedCharacters = new ArrayList<>();
        testedCharacters.add(Character.SPROG);
        testedCharacters.add(Character.D_STRUCT_OR);
        testedCharacters.add(Character.DOZER);
        PlayerRequestEvent event = testedEncoder.encodePlayerRequestEvent(user,testedCharacters,1);
        boolean[] resultCharacters = event.getTargetPlayers();
        for(int i = 0; i<5; i++){
            Assert.assertTrue( expectedCharacters[i]==resultCharacters[i]);
        }
        System.out.println("Tested PlayerRequestEvent encoding");
    }

    @Test
    public void testEncodeCardRequestEvent(){
        ArrayList<Card> testedCards = new ArrayList<>();
        testedCards.add(new Weapon(CubeColour.Yellow,"TestWeapon1",null));
        testedCards.add(new Weapon(CubeColour.Blue,"TestWeapon2",null));
        CardRequestEvent event = testedEncoder.encodeCardRequestEvent(user,testedCards);
        Assert.assertTrue( event.getType().equals("Weapon"));
        Assert.assertTrue(event.getCards().contains("TestWeapon1"));
        int index = event.getCards().indexOf("TestWeapon1");
        if(index!=1 && index != 0){
            Assert.fail();
        }
        Assert.assertTrue(event.getColour().get(index).equals(CubeColour.Yellow));
        Assert.assertTrue( event.getCards().contains("TestWeapon2"));
        index = event.getCards().indexOf("TestWeapon2");
        if(index!=1 && index != 0){
            Assert.fail();
        }
        Assert.assertTrue( event.getColour().get(index).equals(CubeColour.Blue));
        System.out.println("Tested CardRequestEvent encoding");
    }

    @Test
    public void testEncodePositionRequestEvent(){
        ArrayList<Square> testedSquare = new ArrayList<>();
        testedSquare.add(new BasicSquare(3,0,null,false,null, false,
                null,false,null,false,"Red"));
        testedSquare.add(new SpawnSquare(0,2,null,false,null, false,
                null,false,null,false,"Blue"));

        PositionRequestEvent event = testedEncoder.encodePositionRequestEvent(user, testedSquare);
        Assert.assertEquals( 3,event.getPossiblePositionsY().get(0).intValue());
        Assert.assertEquals( 0,event.getPossiblePositionsX().get(0).intValue());
        Assert.assertEquals( 0,event.getPossiblePositionsY().get(1).intValue());
        Assert.assertEquals( 2,event.getPossiblePositionsX().get(1).intValue());
        System.out.println("Tested PositionRequest encoding");
    }
}
