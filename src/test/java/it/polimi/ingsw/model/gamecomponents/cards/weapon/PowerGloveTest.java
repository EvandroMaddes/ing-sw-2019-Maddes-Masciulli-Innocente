package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.PowerGlove;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PowerGloveTest {
    private PowerGlove powerGlove;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        powerGlove = new PowerGlove();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addWeapon(powerGlove);
        player1.setPosition(map[2][3]);
        player2.setPosition(map[2][2]);
        player3.setPosition(map[2][1]);
        player4.setPosition(map[2][3]);
        player5.setPosition(map[1][2]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(powerGlove.isUsable());
        Assert.assertTrue(powerGlove.isUsableEffect(1));
        Assert.assertTrue(powerGlove.isUsableEffect(2));
    }

    @Test
    public void effectOneTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) powerGlove.getTargetEffect(1);

        Assert.assertEquals(1, message.getMaxTarget());
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(player2.getCharacter(), message.getPossibleTargets().get(0));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        powerGlove.performEffect(1, target);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player2.getPlayerBoard().getMarks().size());
        Assert.assertEquals(map[2][2], player1.getPosition());
        Assert.assertEquals(map[2][2], player2.getPosition());
        Assert.assertFalse(powerGlove.isUsableEffect(1));
        Assert.assertFalse(powerGlove.isUsableEffect(2));
        Assert.assertFalse(powerGlove.isUsable());
    }

    @Test
    public void effectTwoTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) powerGlove.getTargetEffect(2);

        Assert.assertEquals(1, message.getMaxTarget());
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(player2.getCharacter(), message.getPossibleTargets().get(0));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        powerGlove.performEffect(2, target);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(map[2][2], player1.getPosition());
        Assert.assertEquals(map[2][2], player2.getPosition());
        Assert.assertFalse(powerGlove.isUsableEffect(1));
        Assert.assertFalse(powerGlove.isUsableEffect(1));
        Assert.assertTrue(powerGlove.isUsableEffect(2));
        Assert.assertTrue(powerGlove.isUsable());

        message = (TargetPlayerRequestEvent) powerGlove.getTargetEffect(2);
        Assert.assertEquals(1, message.getMaxTarget());
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertEquals(player3.getCharacter(), message.getPossibleTargets().get(0));

        target.clear();
        target.add(player3);
        powerGlove.performEffect(2, target);
        Assert.assertEquals(2, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player2.getPlayerBoard().getMarks().size());
        Assert.assertEquals(map[2][1], player1.getPosition());
        Assert.assertEquals(map[2][1], player3.getPosition());
        Assert.assertEquals(map[2][2], player2.getPosition());
        Assert.assertFalse(powerGlove.isUsableEffect(1));
        Assert.assertFalse(powerGlove.isUsableEffect(2));
        Assert.assertFalse(powerGlove.isUsable());
    }
}
