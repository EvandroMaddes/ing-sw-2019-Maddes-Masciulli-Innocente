package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.CyberBlade;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * cyberblade tests
 */
public class CyberBladeTest {
    private CyberBlade cyberBlade;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp() {
        cyberBlade = new CyberBlade();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addWeapon(cyberBlade);
        player1.setPosition(map[2][3]);
        player2.setPosition(map[2][2]);
        player3.setPosition(map[2][1]);
        player4.setPosition(map[1][3]);
        player5.setPosition(map[0][3]);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableTest() {
        Assert.assertTrue(cyberBlade.isUsable());
        Assert.assertFalse(cyberBlade.isUsableEffect(1));
        Assert.assertTrue(cyberBlade.isUsableEffect(2));
        Assert.assertFalse(cyberBlade.isUsableEffect(3));
        player2.setPosition(map[2][3]);
        Assert.assertTrue(cyberBlade.isUsableEffect(1));
    }

    /**
     * Test the effect two
     */
    @Test
    public void effectTwoTest() {
        ControllerViewEvent message = cyberBlade.getTargetEffect(2);
        int[] expectedX = new int[]{2, 1, 2};
        int[] expectedY = new int[]{2, 3, 3};
        Assert.assertEquals(3, ((TargetSquareRequestEvent) message).getPossibleTargetsX().length);
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsX()[j] && expectedY[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(map[2][2]);
        cyberBlade.performEffect(2, target);

        Assert.assertEquals(map[2][2], player1.getPosition());
        Assert.assertFalse(cyberBlade.isUsableEffect(2));
        Assert.assertTrue(cyberBlade.isUsableEffect(1));
        Assert.assertFalse(cyberBlade.isUsableEffect(3));
    }

    /**
     * test the three effect in that order : 1 - 2 - 3
     */
    @Test
    public void effectOneThanTwoThanThreeTest() {
        player5.setPosition(map[2][3]);
        Assert.assertTrue(cyberBlade.isUsableEffect(1));

        ControllerViewEvent message = cyberBlade.getTargetEffect(1);
        Assert.assertEquals(1, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        Assert.assertEquals(player5.getCharacter(), ((TargetPlayerRequestEvent) message).getPossibleTargets().get(0));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player5);
        cyberBlade.performEffect(1, target);
        Assert.assertEquals(2, player5.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(cyberBlade.isUsableEffect(1));
        Assert.assertTrue(cyberBlade.isUsableEffect(2));
        Assert.assertFalse(cyberBlade.isUsableEffect(3));

        message = cyberBlade.getTargetEffect(2);
        int[] expectedX = new int[]{2, 1, 2};
        int[] expectedY = new int[]{2, 3, 3};
        Assert.assertEquals(3, ((TargetSquareRequestEvent) message).getPossibleTargetsX().length);
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsX()[j] && expectedY[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        target.clear();
        target.add(map[1][3]);
        cyberBlade.performEffect(2, target);

        Assert.assertEquals(map[1][3], player1.getPosition());
        Assert.assertFalse(cyberBlade.isUsableEffect(2));
        Assert.assertFalse(cyberBlade.isUsableEffect(1));
        Assert.assertTrue(cyberBlade.isUsableEffect(3));

        message = cyberBlade.getTargetEffect(3);
        Assert.assertEquals(1, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        Assert.assertEquals(player4.getCharacter(), ((TargetPlayerRequestEvent) message).getPossibleTargets().get(0));

        target.clear();
        target.add(player4);
        cyberBlade.performEffect(3, target);
        Assert.assertEquals(2, player4.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(cyberBlade.isUsableEffect(1));
        Assert.assertFalse(cyberBlade.isUsableEffect(2));
        Assert.assertFalse(cyberBlade.isUsableEffect(3));
        Assert.assertFalse(cyberBlade.isUsable());
    }

    /**
     * Test the three effect in that order: 1 - 3 - 2
     */
    @Test
    public void effectOneThanThreeThanTwoTest() {
        player5.setPosition(map[2][3]);
        player3.setPosition(map[2][3]);
        Assert.assertTrue(cyberBlade.isUsableEffect(1));

        ControllerViewEvent message = cyberBlade.getTargetEffect(1);
        Assert.assertEquals(2, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        Assert.assertTrue(((TargetPlayerRequestEvent) message).getPossibleTargets().contains(player5.getCharacter()));
        Assert.assertTrue(((TargetPlayerRequestEvent) message).getPossibleTargets().contains(player3.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player5);
        cyberBlade.performEffect(1, target);
        Assert.assertEquals(2, player5.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(cyberBlade.isUsableEffect(1));
        Assert.assertTrue(cyberBlade.isUsableEffect(2));
        Assert.assertTrue(cyberBlade.isUsableEffect(3));

        message = cyberBlade.getTargetEffect(3);
        Assert.assertEquals(1, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        Assert.assertEquals(player3.getCharacter(), ((TargetPlayerRequestEvent) message).getPossibleTargets().get(0));

        target.clear();
        target.add(player3);
        cyberBlade.performEffect(3, target);
        Assert.assertEquals(2, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(cyberBlade.isUsableEffect(1));
        Assert.assertTrue(cyberBlade.isUsableEffect(2));
        Assert.assertFalse(cyberBlade.isUsableEffect(3));
        Assert.assertTrue(cyberBlade.isUsable());

        message = cyberBlade.getTargetEffect(2);
        int[] expectedX = new int[]{2, 1, 2};
        int[] expectedY = new int[]{2, 3, 3};
        Assert.assertEquals(3, ((TargetSquareRequestEvent) message).getPossibleTargetsX().length);
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsX()[j] && expectedY[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        target.clear();
        target.add(map[1][3]);
        cyberBlade.performEffect(2, target);

        Assert.assertEquals(map[1][3], player1.getPosition());
        Assert.assertFalse(cyberBlade.isUsableEffect(2));
        Assert.assertFalse(cyberBlade.isUsableEffect(1));
        Assert.assertFalse(cyberBlade.isUsableEffect(3));
    }
}
