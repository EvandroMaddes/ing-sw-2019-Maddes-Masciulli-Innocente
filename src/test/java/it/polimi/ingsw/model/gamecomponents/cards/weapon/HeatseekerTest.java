package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Heatseeker;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * heatseeker tests
 */
public class HeatseekerTest {
    private Heatseeker heatseeker;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @Before
    public void setUp() {
        heatseeker = new Heatseeker();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player1.addAmmo(new AmmoCube(CubeColour.Yellow));
        player1.addWeapon(heatseeker);
    }

    /**
     * Test the usability of the weapon
     */
    @Test
    public void isUsableTest() {
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1.setPosition(map[0][3]);
        player2.setPosition(map[0][3]);
        player3.setPosition(map[2][3]);
        player4.setPosition(map[2][0]);

        Assert.assertTrue(heatseeker.isUsable());
        Assert.assertTrue(heatseeker.isUsableEffect(1));
    }

    /**
     * TEst the effect one on the large map
     */
    @Test
    public void effectOnLargeMapTest() {
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1.setPosition(map[0][3]);
        player2.setPosition(map[0][3]);
        player3.setPosition(map[2][3]);
        player4.setPosition(map[2][0]);

        ControllerViewEvent message = heatseeker.getTargetEffect(1);
        Assert.assertEquals(1, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        Assert.assertEquals(Character.VIOLET, ((TargetPlayerRequestEvent) message).getPossibleTargets().get(0));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player4);
        heatseeker.performEffect(1, target);
        Assert.assertEquals(3, player4.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(heatseeker.isUsable());
    }

    /**
     * Test the efefct on a small map
     */
    @Test
    public void effectOnSmallMap() {
        Map gameMap = new Map(Map.SMALL_LEFT, Map.SMALL_RIGHT);
        map = gameMap.getSquareMatrix();
        player1.setPosition(map[2][3]);
        player2.setPosition(map[2][3]);
        player3.setPosition(map[2][3]);
        player4.setPosition(map[0][0]);

        ControllerViewEvent message = heatseeker.getTargetEffect(1);
        Assert.assertEquals(1, ((TargetPlayerRequestEvent) message).getPossibleTargets().size());
        Assert.assertEquals(Character.VIOLET, ((TargetPlayerRequestEvent) message).getPossibleTargets().get(0));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player4);
        heatseeker.performEffect(1, target);
        Assert.assertEquals(3, player4.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(heatseeker.isUsable());
    }


}
