package it.polimi.ingsw.model.gamecomponents.cards.weapon;

import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.MachineGun;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MachineGunTest {
    private MachineGun machineGun;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        machineGun = new MachineGun();
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.BANSHEE);
        player3 = new Player("Evandro", Character.DOZER);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.D_STRUCT_OR);
        player1.addWeapon(machineGun);
        player1.setPosition(map[2][2]);
        player2.setPosition(map[2][2]);
        player3.setPosition(map[2][0]);
        player4.setPosition(map[1][2]);
        player5.setPosition(map[0][0]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(machineGun.isUsable());
        Assert.assertTrue(machineGun.isUsableEffect(1));
        Assert.assertFalse(machineGun.isUsableEffect(2));
        Assert.assertFalse(machineGun.isUsableEffect(3));
    }

    @Test
    /*
     * test effect one, than two, than three +1 damage, than three +1 target
     */
    public void effectOneWithTwoTargetsThanTwoThanThreeTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player4.getCharacter());
        Assert.assertEquals(3, message.getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }
        Assert.assertEquals(2, message.getMaxTarget());

        ArrayList<Object> targets = new ArrayList<>();
        targets.add(player2);
        targets.add(player3);
        machineGun.performEffect(1, targets);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(1));
        Assert.assertTrue(machineGun.isUsableEffect(2));
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(2);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        targets.clear();
        targets.add(player3);
        machineGun.performEffect(2, targets);
        Assert.assertEquals(2, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(2));
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(3);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));

        targets.clear();
        targets.add(player2);
        machineGun.performEffect(3, targets);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(3);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));

        targets.clear();
        targets.add(player4);
        machineGun.performEffect(3, targets);
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsable());
    }

    @Test
    public void effectOneWithTwoTargetsThanTwoThanThreeInvertedTest(){
        /*
         * test effect one, than two, than three +1 target, than three +1 damage
         */
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player4.getCharacter());
        Assert.assertEquals(3, message.getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }
        Assert.assertEquals(2, message.getMaxTarget());

        ArrayList<Object> targets = new ArrayList<>();
        targets.add(player2);
        targets.add(player3);
        machineGun.performEffect(1, targets);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(1));
        Assert.assertTrue(machineGun.isUsableEffect(2));
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(2);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        targets.clear();
        targets.add(player3);
        machineGun.performEffect(2, targets);
        Assert.assertEquals(2, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(2));
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(3);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));

        targets.clear();
        targets.add(player4);
        machineGun.performEffect(3, targets);
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(3));
        Assert.assertFalse(machineGun.isUsable());
    }

    @Test
    /*
     * test effect one on one target, than two, than three +1 target (three +1 damage not allowed)
     */
    public void effectOneWithOneTargetThanTwoThanThreeTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player4.getCharacter());
        Assert.assertEquals(3, message.getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }
        Assert.assertEquals(2, message.getMaxTarget());

        ArrayList<Object> targets = new ArrayList<>();
        targets.add(player2);
        machineGun.performEffect(1, targets);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(1));
        Assert.assertTrue(machineGun.isUsableEffect(2));
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(2);
        Assert.assertEquals(1, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));

        targets.clear();
        targets.add(player2);
        machineGun.performEffect(2, targets);
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(2));
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(3);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));

        targets.clear();
        targets.add(player3);
        machineGun.performEffect(3, targets);
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(3));
        Assert.assertFalse(machineGun.isUsable());
    }

    @Test
    /*
     * test effect one on one target, than three +1 target
     */
    public void effectOneWithOneTargetThanThreeTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player4.getCharacter());
        Assert.assertEquals(3, message.getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }
        Assert.assertEquals(2, message.getMaxTarget());

        ArrayList<Object> targets = new ArrayList<>();
        targets.add(player2);
        machineGun.performEffect(1, targets);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(1));
        Assert.assertTrue(machineGun.isUsableEffect(2));
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(3);
        Assert.assertEquals(3, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));

        targets.clear();
        targets.add(player3);
        machineGun.performEffect(3, targets);
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(3));
        Assert.assertFalse(machineGun.isUsable());
    }

    @Test
    /*
     * test effect one, than three +1 target, than three +1 damage, than 2
     */
    public void effectOneWithTwoTargetsThanThreeThanTwoTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player4.getCharacter());
        Assert.assertEquals(3, message.getPossibleTargets().size());
        for (Character c: expectedTargets) {
            Assert.assertTrue(message.getPossibleTargets().contains(c));
        }
        Assert.assertEquals(2, message.getMaxTarget());

        ArrayList<Object> targets = new ArrayList<>();
        targets.add(player2);
        targets.add(player3);
        machineGun.performEffect(1, targets);
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(1));
        Assert.assertTrue(machineGun.isUsableEffect(2));
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(3);
        Assert.assertEquals(3, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        targets.clear();
        targets.add(player4);
        machineGun.performEffect(3, targets);
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(machineGun.isUsableEffect(3));

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(3);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));
        Assert.assertFalse(machineGun.isUsable());

        targets.clear();
        targets.add(player2);
        machineGun.performEffect(3, targets);
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsable());

        message = (TargetPlayerRequestEvent) machineGun.getTargetEffect(2);
        Assert.assertEquals(-1, message.getMaxTarget());

        targets.clear();
        targets.add(player3);
        machineGun.performEffect(2, targets);
        Assert.assertEquals(2, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(machineGun.isUsableEffect(2));
        Assert.assertFalse(machineGun.isUsableEffect(3));
        Assert.assertFalse(machineGun.isUsable());
    }

}
