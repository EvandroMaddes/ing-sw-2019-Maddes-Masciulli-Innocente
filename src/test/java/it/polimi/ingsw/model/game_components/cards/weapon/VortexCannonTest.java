package it.polimi.ingsw.model.game_components.cards.weapon;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.weapons.VortexCannon;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class VortexCannonTest {
    private VortexCannon vortexCannon;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        vortexCannon = new VortexCannon();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addWeapon(vortexCannon);
        player1.setPosition(map[1][1]);
        player2.setPosition(map[0][3]);
        player3.setPosition(map[1][2]);
        player4.setPosition(map[0][2]);
        player5.setPosition(map[1][1]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(vortexCannon.isUsable());
        Assert.assertTrue(vortexCannon.isUsableEffect(1));
        Assert.assertFalse(vortexCannon.isUsableEffect(2));
    }

    @Test
    public void effectOneThanTwoTest(){
        ControllerViewEvent message = vortexCannon.getTargetEffect(1);
        int[] expectedX = new int[]{0,0,2};
        int[] expectedY = new int[]{1,2,1};
        Assert.assertEquals(3, ((TargetSquareRequestEvent)message).getPossibleTargetsX().length );
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsX()[j] && expectedY[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(map[0][2]);
        vortexCannon.performEffect(1, target);
        Assert.assertTrue(vortexCannon.isUsableEffect(1));
        Assert.assertFalse(vortexCannon.isUsableEffect(2));

        message = vortexCannon.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player4.getCharacter());
        expectedTargets.add(player3.getCharacter());
        Assert.assertEquals(3, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        target.clear();
        target.add(player2);
        vortexCannon.performEffect(1, target);
        Assert.assertEquals(map[0][2], player2.getPosition());
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(vortexCannon.isUsableEffect(1));
        Assert.assertTrue(vortexCannon.isUsableEffect(2));

        message = vortexCannon.getTargetEffect(2);
        expectedTargets.clear();
        expectedTargets.add(player4.getCharacter());
        expectedTargets.add(player3.getCharacter());
        Assert.assertEquals(2, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        target.clear();
        target.add(player3);
        target.add(player4);
        vortexCannon.performEffect(2, target);
        Assert.assertEquals(map[0][2], player2.getPosition());
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(vortexCannon.isUsableEffect(2));
        Assert.assertFalse(vortexCannon.isUsable());
    }
}
