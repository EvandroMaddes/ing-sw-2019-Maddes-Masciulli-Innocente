package it.polimi.ingsw.model.game_components.cards.weapon;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.weapons.TractorBeam;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TractorBeamTest {
    private TractorBeam tractorBeam;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        tractorBeam = new TractorBeam();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addWeapon(tractorBeam);
        player1.setPosition(map[1][0]);
        player2.setPosition(map[0][1]);
        player3.setPosition(map[1][2]);
        player4.setPosition(map[0][3]);
        player5.setPosition(map[1][0]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(tractorBeam.isUsable());
        Assert.assertFalse(tractorBeam.isUsableEffect(1));
        Assert.assertTrue(tractorBeam.isUsableEffect(2));
    }

    @Test
    public void effectTwoOnSameSquarePlayerTest(){
        ControllerViewEvent message = tractorBeam.getTargetEffect(2);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        Assert.assertEquals(2, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(player5.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player5);
        tractorBeam.performEffect(2, target);
        Assert.assertEquals(map[1][0], player1.getPosition());
        Assert.assertEquals(player1.getPosition(), player5.getPosition());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(3, player5.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(tractorBeam.isUsable());
    }

    @Test
    public void effectTwoOnDifferentSquarePlayerTest(){
        ControllerViewEvent message = tractorBeam.getTargetEffect(2);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        Assert.assertEquals(2, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(player5.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        tractorBeam.performEffect(2, target);
        Assert.assertEquals(map[1][0], player1.getPosition());
        Assert.assertEquals(player1.getPosition(), player2.getPosition());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(3, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(tractorBeam.isUsable());
    }

    @Test
    public void effectOneOnVisibleTargetTest(){
        player2.setPosition(map[0][0]);
        ControllerViewEvent message = tractorBeam.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(3, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        tractorBeam.performEffect(1, target);
        Assert.assertTrue(tractorBeam.isUsableEffect(1));

        message = tractorBeam.getTargetEffect(1);
        int[] expectedX = new int[]{0,1,2};
        int[] expectedY = new int[]{0,0,0};
        Assert.assertEquals(3, ((TargetSquareRequestEvent)message).getPossibleTargetsX().length );
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsX()[j] && expectedY[i] == ((TargetSquareRequestEvent)message).getPossibleTargetsY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        target.clear();
        target.add(map[2][0]);
        tractorBeam.performEffect(1, target);
        Assert.assertEquals(map[2][0], player2.getPosition());
        Assert.assertEquals(1, player2.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(tractorBeam.isUsable());
    }

    @Test
    public void effectOneOnUnseenTargetTest(){
        ControllerViewEvent message = tractorBeam.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());
        expectedTargets.add(player3.getCharacter());
        expectedTargets.add(player5.getCharacter());
        Assert.assertEquals(3, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player3);
        tractorBeam.performEffect(1, target);
        Assert.assertTrue(tractorBeam.isUsableEffect(1));

        message = tractorBeam.getTargetEffect(1);
        Assert.assertEquals(1, ((TargetSquareRequestEvent)message).getPossibleTargetsX().length );
        Assert.assertEquals(2, ((TargetSquareRequestEvent)message).getPossibleTargetsX()[0]);
        Assert.assertEquals(1, ((TargetSquareRequestEvent)message).getPossibleTargetsY()[0]);

        target.clear();
        target.add(map[2][1]);
        tractorBeam.performEffect(1, target);
        Assert.assertEquals(map[2][1], player3.getPosition());
        Assert.assertEquals(1, player3.getPlayerBoard().getDamageAmount());
        Assert.assertFalse(tractorBeam.isUsable());
    }
}
