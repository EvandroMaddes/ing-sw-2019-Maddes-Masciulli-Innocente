package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Electroscythe;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * electroscythe tests
 */
public class ElectroscytheTest {
    private Electroscythe electroscythe;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp() {
        electroscythe = new Electroscythe();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addWeapon(electroscythe);
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
        Assert.assertFalse(electroscythe.isUsable());
        Assert.assertFalse(electroscythe.isUsableEffect(1));
        Assert.assertFalse(electroscythe.isUsableEffect(2));
        player2.setPosition(map[2][3]);
        Assert.assertTrue(electroscythe.isUsableEffect(1));
        Assert.assertTrue(electroscythe.isUsableEffect(2));
    }

    /**
     * Test the first effect
     */
    @Test
    public void effectOneTest() {
        player2.setPosition(map[2][3]);
        player3.setPosition(map[2][3]);
        ControllerViewEvent message = electroscythe.getTargetEffect(1);
        Assert.assertEquals(-1, ((TargetPlayerRequestEvent) message).getMaxTarget());

        ArrayList<Object> target = new ArrayList<>();
        electroscythe.performEffect(1, target);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());

        Assert.assertFalse(electroscythe.isUsableEffect(1));
        Assert.assertFalse(electroscythe.isUsableEffect(2));
        Assert.assertFalse(electroscythe.isUsable());
    }

    /**
     * Test the second effect
     */
    @Test
    public void effectTwoTest() {
        player2.setPosition(map[2][3]);
        player3.setPosition(map[2][3]);
        ControllerViewEvent message = electroscythe.getTargetEffect(2);
        Assert.assertEquals(-1, ((TargetPlayerRequestEvent) message).getMaxTarget());

        ArrayList<Object> target = new ArrayList<>();
        electroscythe.performEffect(2, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());

        Assert.assertFalse(electroscythe.isUsableEffect(1));
        Assert.assertFalse(electroscythe.isUsableEffect(2));
        Assert.assertFalse(electroscythe.isUsable());
    }


}
