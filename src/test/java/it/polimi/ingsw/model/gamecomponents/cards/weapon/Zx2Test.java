package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Zx2;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * ZX-2 tests
 */
public class Zx2Test {
    private Zx2 zx2;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @Before
    public void setUp() {
        Map gameMap = new Map("leftSecond", "rightFirst");
        map = gameMap.getSquareMatrix();
        zx2 = new Zx2();
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.BANSHEE);
        player3 = new Player("Evandro", Character.DOZER);
        player4 = new Player("Chiara", Character.VIOLET);
        player1.addWeapon(zx2);
        player1.setPosition(map[1][0]);
        player2.setPosition(map[1][0]);
        player3.setPosition(map[0][0]);
        player4.setPosition(map[0][2]);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableTest() {
        Assert.assertTrue(zx2.isUsable());
        Assert.assertTrue(zx2.isUsableEffect(1));
        Assert.assertTrue(zx2.isUsableEffect(2));
    }

    /**
     * Test the effect one
     */
    @Test
    public void effectOneTest() {
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) zx2.getTargetEffect(1);
        ArrayList<Character> expected = new ArrayList<>();
        expected.add(Character.BANSHEE);
        expected.add(Character.DOZER);
        expected.add(Character.VIOLET);
        Assert.assertEquals(1, message.getMaxTarget());
        Assert.assertEquals(3, message.getPossibleTargets().size());
        for (Character c : expected) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        zx2.performEffect(1, target);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player2.getPlayerBoard().getMarks().size());
        Assert.assertEquals(player1, player2.getPlayerBoard().getMarks().get(0).getPlayer());
        Assert.assertEquals(player1, player2.getPlayerBoard().getDamageReceived()[0].getPlayer());
        Assert.assertFalse(zx2.isUsable());
    }

    /**
     * Test the effect two
     */
    @Test
    public void effectTwoTest() {
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) zx2.getTargetEffect(2);
        ArrayList<Character> expected = new ArrayList<>();
        expected.add(Character.BANSHEE);
        expected.add(Character.DOZER);
        expected.add(Character.VIOLET);
        Assert.assertEquals(3, message.getMaxTarget());
        Assert.assertEquals(3, message.getPossibleTargets().size());
        for (Character c : expected) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        target.add(player3);
        target.add(player4);
        zx2.performEffect(2, target);
        Assert.assertEquals(0, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getMarks().size());
        Assert.assertEquals(1, player3.getPlayerBoard().getMarks().size());
        Assert.assertEquals(1, player4.getPlayerBoard().getMarks().size());
        Assert.assertEquals(player1, player2.getPlayerBoard().getMarks().get(0).getPlayer());
        Assert.assertFalse(zx2.isUsable());
    }
}
