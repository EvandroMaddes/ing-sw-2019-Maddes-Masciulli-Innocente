package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.RocketLauncher;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Rocket launcher tests
 */
public class RocketLauncherTest {
    private RocketLauncher rocketLauncher;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp() {
        rocketLauncher = new RocketLauncher();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addAmmo(new AmmoCube(CubeColour.Yellow));
        player1.addWeapon(rocketLauncher);
        player1.setPosition(map[2][2]);
        player2.setPosition(map[2][2]);
        player3.setPosition(map[2][2]);
        player4.setPosition(map[2][1]);
        player5.setPosition(map[2][1]);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableTest() {
        Assert.assertTrue(rocketLauncher.isUsable());
        Assert.assertTrue(rocketLauncher.isUsableEffect(1));
        Assert.assertTrue(rocketLauncher.isUsableEffect(2));
        Assert.assertFalse(rocketLauncher.isUsableEffect(3));
    }

    /**
     * test the efect two
     */
    @Test
    public void effectTwoTest() {
        ControllerViewEvent message = rocketLauncher.getTargetEffect(2);
        int[] expectedX = new int[]{2, 2, 2, 1, 1, 0, 2, 1};
        int[] expectedY = new int[]{0, 1, 2, 1, 2, 2, 3, 3};
        Assert.assertEquals(8, ((TargetSquareRequestEvent) message).getPossibleTargetsX().length);
        for (int i = 0; i < 8; i++) {
            boolean check = false;
            for (int j = 0; j < 8; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsX()[j] && expectedY[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
        ArrayList<Object> target = new ArrayList<>();
        target.add(map[2][1]);
        rocketLauncher.performEffect(2, target);
        Assert.assertFalse(rocketLauncher.isUsableEffect(2));
        Assert.assertEquals(map[2][1], player1.getPosition());
        Assert.assertTrue(rocketLauncher.isUsableEffect(1));
        Assert.assertFalse(rocketLauncher.isUsableEffect(3));
    }

    /**
     * Test the effect one without moving the target, than test the effect three
     */
    @Test
    public void effectOneWithoutMoveThanTreeTest() {
        ControllerViewEvent message = rocketLauncher.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player4.getCharacter());
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(2, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        for (Character c : expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent) message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player5);
        rocketLauncher.performEffect(1, target);
        Assert.assertEquals(2, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(map[2][1], player5.getPosition());
        Assert.assertTrue(rocketLauncher.isUsableEffect(1));
        Assert.assertTrue(rocketLauncher.isUsableEffect(2));
        Assert.assertTrue(rocketLauncher.isUsableEffect(3));

        message = rocketLauncher.getTargetEffect(3);
        Assert.assertEquals(-1, ((TargetPlayerRequestEvent) message).getMaxTarget());

        target.clear();
        rocketLauncher.performEffect(3, target);
        Assert.assertEquals(3, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(rocketLauncher.isUsableEffect(1));
        Assert.assertFalse(rocketLauncher.isUsableEffect(2));
        Assert.assertFalse(rocketLauncher.isUsableEffect(3));
    }

    /**
     * Test the effect one moving the target, than test the effect three
     */
    @Test
    public void effectOneWithMoveThanTreeTest() {
        ControllerViewEvent message = rocketLauncher.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player4.getCharacter());
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(2, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        for (Character c : expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent) message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player5);
        rocketLauncher.performEffect(1, target);
        Assert.assertEquals(2, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(map[2][1], player5.getPosition());
        Assert.assertTrue(rocketLauncher.isUsableEffect(1));
        Assert.assertTrue(rocketLauncher.isUsableEffect(2));
        Assert.assertTrue(rocketLauncher.isUsableEffect(3));

        message = rocketLauncher.getTargetEffect(1);
        int[] expectedX = new int[]{2, 1, 2};
        int[] expectedY = new int[]{2, 1, 0};
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
        target.add(map[2][2]);
        rocketLauncher.performEffect(1, target);
        Assert.assertEquals(map[2][2], player5.getPosition());
        Assert.assertEquals(2, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(map[2][1], player4.getPosition());

        message = rocketLauncher.getTargetEffect(3);
        Assert.assertEquals(-1, ((TargetPlayerRequestEvent) message).getMaxTarget());

        target.clear();
        rocketLauncher.performEffect(3, target);
        Assert.assertEquals(3, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(rocketLauncher.isUsableEffect(1));
        Assert.assertFalse(rocketLauncher.isUsableEffect(2));
        Assert.assertFalse(rocketLauncher.isUsableEffect(3));
    }

}
