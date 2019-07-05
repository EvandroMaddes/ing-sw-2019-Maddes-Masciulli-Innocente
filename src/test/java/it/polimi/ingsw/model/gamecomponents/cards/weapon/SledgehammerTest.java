package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Sledgehammer;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Sledgehammer tests
 */
public class SledgehammerTest {
    private Sledgehammer sledgehammer;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;

    @Before
    public void setUp() {
        sledgehammer = new Sledgehammer();
        Map gameMap = new Map(Map.SMALL_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.BANSHEE);
        player3 = new Player("Evandro", Character.DOZER);
        player1.addWeapon(sledgehammer);
        player1.setPosition(map[1][0]);
        player2.setPosition(map[1][0]);
        player3.setPosition(map[0][0]);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableTest() {
        Assert.assertTrue(sledgehammer.isUsable());
        Assert.assertTrue(sledgehammer.isUsableEffect(1));
        Assert.assertTrue(sledgehammer.isUsableEffect(2));
    }

    /**
     * Test the effect one
     */
    @Test
    public void effectOneTest() {
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) sledgehammer.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        Assert.assertEquals(expectedTargets, message.getPossibleTargets());
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(1, message.getMaxTarget());

        ArrayList<Object> targets = new ArrayList<>();
        targets.add(player2);
        sledgehammer.performEffect(1, targets);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(player1, player2.getPlayerBoard().getDamageReceived()[0].getPlayer());

        Assert.assertFalse(sledgehammer.isUsable());
    }

    /**
     * Test the effect two
     */
    @Test
    public void effectTwoTest() {
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) sledgehammer.getTargetEffect(2);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        Assert.assertEquals(expectedTargets, message.getPossibleTargets());
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(1, message.getMaxTarget());

        ArrayList<Object> targets = new ArrayList<>();
        targets.add(player2);
        sledgehammer.performEffect(2, targets);
        Assert.assertEquals(3, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(player1, player2.getPlayerBoard().getDamageReceived()[0].getPlayer());
        Assert.assertTrue(sledgehammer.isUsable());
        Assert.assertFalse(sledgehammer.isUsableEffect(1));

        TargetSquareRequestEvent message2 = (TargetSquareRequestEvent) sledgehammer.getTargetEffect(2);
        int[] expextedX = new int[]{0, 1, 1};
        int[] expectedY = new int[]{0, 0, 1};

        Assert.assertEquals(3, message2.getPossibleTargetsX().length);
        Assert.assertEquals(3, message2.getPossibleTargetsY().length);
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expextedX[i] == message2.getPossibleTargetsX()[j] && expectedY[i] == message2.getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        targets = new ArrayList<>();
        targets.add(map[1][1]);
        sledgehammer.performEffect(2, targets);
        Assert.assertEquals(3, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(map[1][1], player2.getPosition());
        Assert.assertEquals(map[1][0], player1.getPosition());
        Assert.assertEquals(map[0][0], player3.getPosition());
        Assert.assertFalse(sledgehammer.isUsable());
    }


}
