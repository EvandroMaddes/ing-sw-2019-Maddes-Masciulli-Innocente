package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Furnace;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * furnace tests
 */
public class FurnaceTest {
    private Furnace furnace;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Player player6;

    @Before
    public void setUp(){
        furnace = new Furnace();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player6 = new Player("Test2", Character.BANSHEE);
        player1.addWeapon(furnace);
        player1.setPosition(map[0][2]);
        player2.setPosition(map[1][2]);
        player3.setPosition(map[0][1]);
        player4.setPosition(map[0][3]);
        player5.setPosition(map[2][3]);
        player6.setPosition(map[0][2]);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableEffectTest(){
        Assert.assertTrue(furnace.isUsable());
        Assert.assertTrue(furnace.isUsableEffect(1));
        Assert.assertTrue(furnace.isUsableEffect(2));
    }

    @Test
    public void effectOneTest(){
        TargetSquareRequestEvent message = (TargetSquareRequestEvent) furnace.getTargetEffect(1);
        Assert.assertEquals(2,message.getPossibleTargetsX().length);
        int[] expectedX = new int[]{1,0};
        int[] expectedY = new int[]{2,3};
        for (int i = 0; i < 2; i++) {
            boolean check = false;
            for (int j = 0; j < 2; j++) {
                if (expectedX[i] == message.getPossibleTargetsX()[j] && expectedY[i] == message.getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(map[1][2]);
        furnace.performEffect(1,target);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player6.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(furnace.isUsable());
    }

    @Test
    public void effectTwoTest(){
        player4.setPosition(map[1][2]);
        TargetSquareRequestEvent message = (TargetSquareRequestEvent) furnace.getTargetEffect(2);
        int[] expectedX = new int[]{0,1};
        int[] expectedY = new int[]{1,2};
        Assert.assertEquals(2, message.getPossibleTargetsX().length);
        for (int i = 0; i < 2; i++) {
            boolean check = false;
            for (int j = 0; j < 2; j++) {
                if (expectedX[i] == message.getPossibleTargetsX()[j] && expectedY[i] == message.getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(map[1][2]);
        furnace.performEffect(2, target);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getMarks().size());
        Assert.assertEquals(1, player4.getPlayerBoard().getMarks().size());
        Assert.assertFalse(furnace.isUsable());
    }
}
