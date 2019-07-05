package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.PlasmaGun;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Plasma gun test
 */
public class PlasmaGunTest {
    private PlasmaGun plasmaGun;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp() {
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        plasmaGun = new PlasmaGun();
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.BANSHEE);
        player3 = new Player("Evandro", Character.DOZER);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.D_STRUCT_OR);
        player1.addWeapon(plasmaGun);
        player1.setPosition(map[1][0]);
        player2.setPosition(map[2][1]);
        player3.setPosition(map[2][1]);
        player4.setPosition(map[1][1]);
        player5.setPosition(map[2][3]);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableTest() {
        Assert.assertTrue(plasmaGun.isUsable());
        Assert.assertTrue(plasmaGun.isUsableEffect(1));
        Assert.assertTrue(plasmaGun.isUsableEffect(2));
        Assert.assertFalse(plasmaGun.isUsableEffect(3));
    }

    /**
     * Test the effect two
     */
    @Test
    public void effectTwoTest() {
        ControllerViewEvent message = plasmaGun.getTargetEffect(2);
        int[] expectedX = new int[]{0, 2, 0, 2, 1};
        int[] expectedY = new int[]{0, 0, 1, 1, 0};
        Assert.assertEquals(4, ((TargetSquareRequestEvent) message).getPossibleTargetsX().length);
        for (int i = 0; i < 4; i++) {
            boolean check = false;
            for (int j = 0; j < 4; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsX()[j] && expectedY[i] == ((TargetSquareRequestEvent) message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(map[2][1]);
        plasmaGun.performEffect(2, target);

        Assert.assertEquals(map[2][1], player1.getPosition());
        Assert.assertFalse(plasmaGun.isUsableEffect(2));
        Assert.assertTrue(plasmaGun.isUsableEffect(1));
        Assert.assertFalse(plasmaGun.isUsableEffect(3));
    }

    /**
     * Test the effect one than the second in that order
     */
    @Test
    public void effectOneThanTwoTest() {
        ControllerViewEvent message = plasmaGun.getTargetEffect(1);
        Assert.assertEquals(2, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        Assert.assertTrue(((TargetPlayerRequestEvent) message).getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(((TargetPlayerRequestEvent) message).getPossibleTargets().contains(player3.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        plasmaGun.performEffect(1, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(plasmaGun.isUsableEffect(1));
        Assert.assertTrue(plasmaGun.isUsableEffect(2));
        Assert.assertTrue(plasmaGun.isUsableEffect(3));

        target.clear();
        target.add(map[2][1]);
        plasmaGun.performEffect(2, target);
        Assert.assertFalse(plasmaGun.isUsableEffect(3));
        Assert.assertFalse(plasmaGun.isUsable());
    }

    /**
     * test the effect one than the third in that order
     */
    @Test
    public void effectOneThanThreeTest() {
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) plasmaGun.getTargetEffect(1);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        plasmaGun.performEffect(1, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(plasmaGun.isUsableEffect(1));
        Assert.assertTrue(plasmaGun.isUsableEffect(2));
        Assert.assertTrue(plasmaGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) plasmaGun.getTargetEffect(3);
        Assert.assertEquals(-1, message.getMaxTarget());

        target.clear();
        plasmaGun.performEffect(3, target);
        Assert.assertEquals(3, player2.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(plasmaGun.isUsableEffect(2));
        Assert.assertFalse(plasmaGun.isUsableEffect(1));
        Assert.assertFalse(plasmaGun.isUsableEffect(3));
    }
}
