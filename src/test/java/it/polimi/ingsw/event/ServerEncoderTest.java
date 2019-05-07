package it.polimi.ingsw.event;

import it.polimi.ingsw.event.coder.ServerEncoder;
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

public class ServerEncoderTest {
    private ServerEncoder testedServerEncoder;
    private String user;

    @Before
    public void setUp(){
        testedServerEncoder = new ServerEncoder();
        user = "TestUser";

    }


    @Test
    public void testEncodeCardRequestEvent(){
        ArrayList<Card> testedCards = new ArrayList<>();
        testedCards.add(new Weapon(CubeColour.Yellow,"TestWeapon1",null));
        testedCards.add(new Weapon(CubeColour.Blue,"TestWeapon2",null));
        CardRequestEvent event = testedServerEncoder.encodeCardRequestEvent(user,testedCards);
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
        testedSquare.add(new BasicSquare(3,0));
        testedSquare.add(new SpawnSquare(0,2));

        PositionRequestEvent event = testedServerEncoder.encodePositionRequestEvent(user, testedSquare);
        Assert.assertEquals( 3,event.getPossiblePositionsY().get(0).intValue());
        Assert.assertEquals( 0,event.getPossiblePositionsX().get(0).intValue());
        Assert.assertEquals( 0,event.getPossiblePositionsY().get(1).intValue());
        Assert.assertEquals( 2,event.getPossiblePositionsX().get(1).intValue());
        System.out.println("Tested PositionRequest encoding");
    }
}