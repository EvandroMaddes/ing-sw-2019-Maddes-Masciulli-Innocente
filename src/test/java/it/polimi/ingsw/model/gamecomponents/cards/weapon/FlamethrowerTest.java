package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Flamethrower;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * flamethrower tests
 */
public class FlamethrowerTest {
    private Flamethrower flamethrower;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp() {
        flamethrower = new Flamethrower();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addAmmo(new AmmoCube(CubeColour.Yellow));
        player1.addWeapon(flamethrower);
        player1.setPosition(map[2][3]);
        player2.setPosition(map[2][2]);
        player3.setPosition(map[2][1]);
        player4.setPosition(map[1][2]);
        player5.setPosition(map[0][3]);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableTest() {
        Assert.assertTrue(flamethrower.isUsable());
        Assert.assertTrue(flamethrower.isUsableEffect(1));
        Assert.assertTrue(flamethrower.isUsableEffect(2));
    }

    /**
     * Test the first effetc
     */
    @Test
    public void effectOneTest() {
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) flamethrower.getTargetEffect(1);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(player2.getCharacter(), message.getPossibleTargets().get(0));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        flamethrower.performEffect(1, target);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(flamethrower.isUsableEffect(1));
        Assert.assertFalse(flamethrower.isUsableEffect(2));
        Assert.assertTrue(flamethrower.isUsable());

        message = (TargetPlayerRequestEvent) flamethrower.getTargetEffect(1);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(player3.getCharacter(), message.getPossibleTargets().get(0));

        target.clear();
        target.add(player3);
        flamethrower.performEffect(1, target);
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(flamethrower.isUsableEffect(1));
        Assert.assertFalse(flamethrower.isUsable());
    }

    /**
     * TEst the second effect
     */
    @Test
    public void effectTwoTest() {
        player5.setPosition(map[2][2]);
        ControllerViewEvent message = flamethrower.getTargetEffect(2);
        Assert.assertEquals(1, ((TargetSquareRequestEvent) message).getPossibleTargetsX().length);
        Assert.assertEquals(2, ((TargetSquareRequestEvent) message).getPossibleTargetsX()[0]);
        Assert.assertEquals(2, ((TargetSquareRequestEvent) message).getPossibleTargetsY()[0]);

        ArrayList<Object> target = new ArrayList<>();
        target.add(map[2][2]);
        flamethrower.performEffect(2, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(flamethrower.isUsableEffect(1));
        Assert.assertFalse(flamethrower.isUsableEffect(2));
        Assert.assertFalse(flamethrower.isUsable());
    }
}
