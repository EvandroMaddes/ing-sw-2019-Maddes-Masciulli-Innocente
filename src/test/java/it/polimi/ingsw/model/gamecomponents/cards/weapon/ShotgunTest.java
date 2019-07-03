package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Shotgun;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ShotgunTest {
    private Shotgun shotgun;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        Map gameMap = new Map("leftSecond", "rightFirst");
        map = gameMap.getSquareMatrix();
        shotgun = new Shotgun();
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.BANSHEE);
        player3 = new Player("Evandro", Character.DOZER);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.D_STRUCT_OR);
        player1.addWeapon(shotgun);
        player1.setPosition(map[1][2]);
        player2.setPosition(map[2][3]);
        player3.setPosition(map[0][2]);
        player4.setPosition(map[1][2]);
        player5.setPosition(map[1][3]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(shotgun.isUsable());
        Assert.assertTrue(shotgun.isUsableEffect(1));
        Assert.assertTrue(shotgun.isUsableEffect(2));
    }

    @Test
    public void effectOneTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) shotgun.getTargetEffect(1);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(Character.VIOLET, message.getPossibleTargets().get(0));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player4);
        shotgun.performEffect(1, target);
        Assert.assertEquals(3, player4.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(shotgun.isUsableEffect(1));
        Assert.assertTrue(shotgun.isUsable());

        TargetSquareRequestEvent message2 = (TargetSquareRequestEvent) shotgun.getTargetEffect(1);
        int[] expectedX = new int[]{0,1,2};
        int[] expectedY = new int[]{2,3,2};
        Assert.assertEquals(3, message2.getPossibleTargetsX().length);
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == message2.getPossibleTargetsX()[j] && expectedY[i] == message2.getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        target.clear();
        target.add(map[2][2]);
        shotgun.performEffect(1, target);
        Assert.assertEquals(3, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(map[2][2], player4.getPosition());
        Assert.assertEquals(map[1][2], player1.getPosition());
        Assert.assertFalse(shotgun.isUsable());
    }

    @Test
    public void effectTwoTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) shotgun.getTargetEffect(2);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(Character.DOZER);
        expectedTargets.add(Character.D_STRUCT_OR);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player3);
        shotgun.performEffect(2, target);
        Assert.assertEquals(2, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(shotgun.isUsableEffect(1));
        Assert.assertFalse(shotgun.isUsable());
    }
}
