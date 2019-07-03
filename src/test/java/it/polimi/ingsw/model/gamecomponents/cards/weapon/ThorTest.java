package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Thor;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ThorTest {
    private Thor thor;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        thor = new Thor();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addAmmo(new AmmoCube(CubeColour.Yellow));
        player1.addWeapon(thor);
        player1.setPosition(map[2][1]);
        player2.setPosition(map[1][2]);
        player3.setPosition(map[0][2]);
        player4.setPosition(map[0][3]);
        player5.setPosition(map[0][1]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(thor.isUsable());
        Assert.assertTrue(thor.isUsableEffect(1));
        Assert.assertFalse(thor.isUsableEffect(2));
        Assert.assertFalse(thor.isUsableEffect(3));
    }

    @Test
    public void effectOneTwoThreeTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) thor.getTargetEffect(1);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(player2.getCharacter(), message.getPossibleTargets().get(0));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        thor.performEffect(1, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(thor.isUsableEffect(1));
        Assert.assertFalse(thor.isUsableEffect(3));
        Assert.assertTrue(thor.isUsableEffect(2));

        message = (TargetPlayerRequestEvent) thor.getTargetEffect(2);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player5.getCharacter()));

        target.clear();
        target.add(player3);
        thor.performEffect(2, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(thor.isUsableEffect(1));
        Assert.assertFalse(thor.isUsableEffect(2));
        Assert.assertTrue(thor.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) thor.getTargetEffect(3);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player5.getCharacter()));

        target.clear();
        target.add(player4);
        thor.performEffect(3, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player4.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(thor.isUsableEffect(1));
        Assert.assertFalse(thor.isUsableEffect(2));
        Assert.assertFalse(thor.isUsableEffect(3));
        Assert.assertFalse(thor.isUsable());
    }

    @Test
    public void effectOneOnSameSquareThanTwoTest(){
        Map gameMap = new Map(Map.SMALL_LEFT, Map.SMALL_RIGHT);
        map = gameMap.getSquareMatrix();
        player1.setPosition(map[1][1]);
        player2.setPosition(map[1][1]);
        player3.setPosition(map[1][2]);
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) thor.getTargetEffect(1);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        thor.performEffect(1, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(thor.isUsableEffect(1));
        Assert.assertTrue(thor.isUsableEffect(2));
        Assert.assertFalse(thor.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) thor.getTargetEffect(2);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        target.clear();
        target.add(player3);
        thor.performEffect(2, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(thor.isUsableEffect(1));
        Assert.assertFalse(thor.isUsableEffect(2));
        Assert.assertFalse(thor.isUsableEffect(3));
        Assert.assertFalse(thor.isUsable());
    }

}
