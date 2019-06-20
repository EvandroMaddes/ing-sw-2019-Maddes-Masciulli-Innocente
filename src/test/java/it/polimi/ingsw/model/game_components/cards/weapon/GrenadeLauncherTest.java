package it.polimi.ingsw.model.game_components.cards.weapon;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.weapons.GrenadaLauncher;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GrenadeLauncherTest {
    private GrenadaLauncher grenadeLauncher;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        grenadeLauncher = new GrenadaLauncher();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addAmmo(new AmmoCube(CubeColour.Yellow));
        player1.addWeapon(grenadeLauncher);
        player1.setPosition(map[2][3]);
        player2.setPosition(map[2][3]);
        player3.setPosition(map[2][2]);
        player4.setPosition(map[1][2]);
        player5.setPosition(map[0][3]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(grenadeLauncher.isUsable());
        Assert.assertTrue(grenadeLauncher.isUsableEffect(1));
        Assert.assertTrue(grenadeLauncher.isUsableEffect(2));
    }

    @Test
    public void effectOneWithoutMoveThanEffectTwoTest(){
        ControllerViewEvent message = grenadeLauncher.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player4.getCharacter());
        Assert.assertEquals(3, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        grenadeLauncher.performEffect(1, target);

        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(grenadeLauncher.isUsable());
        Assert.assertTrue(grenadeLauncher.isUsableEffect(2));
        Assert.assertTrue(grenadeLauncher.isUsableEffect(1));

        message = grenadeLauncher.getTargetEffect(2);
        int[] expectedX = new int[]{2,2,1};
        int[] exepctedY = new int[]{3,2,2};
        Assert.assertEquals(3, ((TargetSquareRequestEvent)message).getPossibleTargetsX().length);
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsX()[j] && exepctedY[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        target.clear();
        target.add(map[2][3]);
        grenadeLauncher.performEffect(2, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(grenadeLauncher.isUsableEffect(1));
        Assert.assertFalse(grenadeLauncher.isUsable());
    }

    @Test
    public void effectTwoThanEffectOneWithMove(){
        player5.setPosition(map[1][2]);
        ControllerViewEvent message = grenadeLauncher.getTargetEffect(2);
        int[] expectedX = new int[]{2,2,1};
        int[] exepctedY = new int[]{3,2,2};
        Assert.assertEquals(3, ((TargetSquareRequestEvent)message).getPossibleTargetsX().length);
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsX()[j] && exepctedY[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(map[1][2]);
        grenadeLauncher.performEffect(2, target);
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(grenadeLauncher.isUsableEffect(1));
        Assert.assertFalse(grenadeLauncher.isUsableEffect(2));

        message = grenadeLauncher.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player4.getCharacter());
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(4, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        target.clear();
        target = new ArrayList<>();
        target.add(player4);
        grenadeLauncher.performEffect(1, target);

        Assert.assertEquals(2, player4.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(grenadeLauncher.isUsable());
        Assert.assertFalse(grenadeLauncher.isUsableEffect(2));
        Assert.assertTrue(grenadeLauncher.isUsableEffect(1));

        message = grenadeLauncher.getTargetEffect(1);
        expectedX = new int[]{0,2,1};
        exepctedY = new int[]{2,2,3};
        Assert.assertEquals(3, ((TargetSquareRequestEvent)message).getPossibleTargetsX().length);
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsX()[j] && exepctedY[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        target.clear();
        target.add(map[0][2]);
        grenadeLauncher.performEffect(1, target);
        Assert.assertEquals(2, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(map[0][2], player4.getPosition());
        Assert.assertFalse(grenadeLauncher.isUsableEffect(1));
        Assert.assertFalse(grenadeLauncher.isUsable());
    }
}
