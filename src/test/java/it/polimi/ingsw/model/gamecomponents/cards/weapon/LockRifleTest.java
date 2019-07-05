package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.LockRifle;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Lock rifle tests
 */
public class LockRifleTest {
    private LockRifle lockRifle;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @Before
    public void setUp() {
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        lockRifle = new LockRifle();
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.BANSHEE);
        player3 = new Player("Evandro", Character.DOZER);
        player4 = new Player("Chiara", Character.VIOLET);
        player1.addWeapon(lockRifle);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableTest() {
        player1.setPosition(map[0][3]);
        player2.setPosition(map[2][2]);
        player3.setPosition(map[0][3]);
        player4.setPosition(map[0][0]);
        Assert.assertTrue(lockRifle.isUsable());
        Assert.assertTrue(lockRifle.isUsableEffect(1));
        Assert.assertFalse(lockRifle.isUsableEffect(2));
    }

    /**
     * Test the effect one and two in that oreder
     */
    @Test
    public void effectOneThanTwoTest() {
        player1.setPosition(map[0][3]);
        player2.setPosition(map[2][2]);
        player3.setPosition(map[0][3]);
        player4.setPosition(map[0][0]);
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) lockRifle.getTargetEffect(1);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        lockRifle.performEffect(1, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getMarks().size());
        Assert.assertFalse(lockRifle.isUsableEffect(1));
        Assert.assertTrue(lockRifle.isUsableEffect(2));

        message = (TargetPlayerRequestEvent) lockRifle.getTargetEffect(2);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        target.clear();
        target.add(player3);
        lockRifle.performEffect(2, target);
        Assert.assertEquals(1, player3.getPlayerBoard().getMarks().size());
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(lockRifle.isUsable());
    }

    /**
     * Test the effect one and the unusability of the effect two
     */
    @Test
    public void effectOneNotUsableEffectTwoTest() {
        player1.setPosition(map[1][0]);
        player2.setPosition(map[1][0]);
        Assert.assertTrue(lockRifle.isUsable());
        Assert.assertTrue(lockRifle.isUsableEffect(1));
        Assert.assertFalse(lockRifle.isUsableEffect(2));
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) lockRifle.getTargetEffect(1);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        lockRifle.performEffect(1, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getMarks().size());
        Assert.assertFalse(lockRifle.isUsableEffect(1));
        Assert.assertFalse(lockRifle.isUsableEffect(2));
        Assert.assertFalse(lockRifle.isUsable());
    }

    /**
     * Test effect one on the small map
     */
    @Test
    public void effectOneOnSmallBoard() {
        Map gameMap = new Map(Map.SMALL_LEFT, Map.SMALL_RIGHT);
        map = gameMap.getSquareMatrix();
        player1.setPosition(map[1][2]);
        player2.setPosition(map[1][2]);
        player3.setPosition(map[0][0]);
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) lockRifle.getTargetEffect(1);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        lockRifle.performEffect(1, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getMarks().size());
        Assert.assertFalse(lockRifle.isUsableEffect(1));
        Assert.assertTrue(lockRifle.isUsableEffect(2));

    }
}
