package it.polimi.ingsw.model.game_components.cards.weapon;

import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.weapons.ShockWave;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ShockWaveTest {
    private ShockWave shockWave;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        shockWave = new ShockWave();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addWeapon(shockWave);
        player1.setPosition(map[1][2]);
        player2.setPosition(map[0][2]);
        player3.setPosition(map[1][1]);
        player4.setPosition(map[2][2]);
        player5.setPosition(map[2][2]);
    }

    @Test
    public void isUsableEffectTest(){
        Assert.assertTrue(shockWave.isUsable());
        Assert.assertTrue(shockWave.isUsableEffect(1));
        Assert.assertTrue(shockWave.isUsableEffect(2));
    }

    @Test
    public void effectOneTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) shockWave.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();

        expectedTargets.add(Character.SPROG);
        expectedTargets.add(Character.BANSHEE);
        expectedTargets.add(Character.VIOLET);
        Assert.assertEquals(3, message.getPossibleTargets().size());
        Assert.assertEquals(1, message.getMaxTarget());
        for (Character c: expectedTargets) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        shockWave.performEffect(1, target);
        Assert.assertTrue(shockWave.isUsable());
        Assert.assertTrue(shockWave.isUsableEffect(1));
        Assert.assertFalse(shockWave.isUsableEffect(2));
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());

        message = (TargetPlayerRequestEvent) shockWave.getTargetEffect(1);
        expectedTargets.clear();
        expectedTargets.add(Character.BANSHEE);
        expectedTargets.add(Character.VIOLET);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertEquals(1, message.getMaxTarget());
        for (Character c: expectedTargets) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }

        target.clear();
        target.add(player5);
        shockWave.performEffect(1, target);
        Assert.assertEquals(1, player5.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(shockWave.isUsable());
        Assert.assertFalse(shockWave.isUsableEffect(1));
        Assert.assertFalse(shockWave.isUsableEffect(2));
    }

    @Test
    public void secondEffectTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) shockWave.getTargetEffect(2);
        Assert.assertEquals(-1, message.getMaxTarget());

        shockWave.performEffect(2, null);
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player5.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(shockWave.isUsable());
    }

}
