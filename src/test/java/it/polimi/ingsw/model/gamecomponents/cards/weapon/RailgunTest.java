package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Railgun;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RailgunTest {
    private Railgun railgun;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        railgun = new Railgun();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addAmmo(new AmmoCube(CubeColour.Yellow));
        player1.addWeapon(railgun);
        player1.setPosition(map[1][3]);
        player2.setPosition(map[1][0]);
        player3.setPosition(map[0][3]);
        player4.setPosition(map[2][2]);
        player5.setPosition(map[1][3]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(railgun.isUsable());
        Assert.assertTrue(railgun.isUsableEffect(1));
        Assert.assertTrue(railgun.isUsableEffect(2));
    }

    @Test
    public void effectOneTest(){
        ControllerViewEvent message = railgun.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(3, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player5);
        railgun.performEffect(1, target);
        Assert.assertEquals(3, player5.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(railgun.isUsable());
    }

    @Test
    public void effectTwoFirstTargetInSameSquareTest(){
        ControllerViewEvent message = railgun.getTargetEffect(2);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(3, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player5);
        railgun.performEffect(2, target);
        Assert.assertEquals(2, player5.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(railgun.isUsable());

        message = railgun.getTargetEffect(2);
        expectedTargets.clear();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        Assert.assertEquals(2, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        target.clear();
        target.add(player2);
        railgun.performEffect(2, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(railgun.isUsable());
    }

    @Test
    public void effectTwoFirstTargetInDifferentSquareSquareTest(){
        ControllerViewEvent message = railgun.getTargetEffect(2);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(3, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        railgun.performEffect(2, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(railgun.isUsable());

        message = railgun.getTargetEffect(2);
        expectedTargets.clear();
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(1, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        target.clear();
        target.add(player5);
        railgun.performEffect(2, target);
        Assert.assertEquals(2, player5.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(railgun.isUsable());
    }
}
