package it.polimi.ingsw.model.game_components.cards.weapon;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.weapons.Hellion;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class HellionTest {
    private Hellion hellion;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        hellion = new Hellion();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addWeapon(hellion);
        player1.setPosition(map[1][1]);
        player2.setPosition(map[1][1]);
        player3.setPosition(map[0][2]);
        player4.setPosition(map[0][2]);
        player5.setPosition(map[2][2]);
    }

    @Test
    public void isUsableTest() {
        Assert.assertTrue(hellion.isUsable());
        Assert.assertTrue(hellion.isUsableEffect(1));
        Assert.assertTrue(hellion.isUsableEffect(2));
    }

    @Test
    public void effectOneTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) hellion.getTargetEffect(1);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player4);
        hellion.performEffect(1, target);
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player4.getPlayerBoard().getMarks().size());
        Assert.assertEquals(1, player3.getPlayerBoard().getMarks().size());
        Assert.assertEquals(0, player1.getPlayerBoard().getMarks().size());
        Assert.assertEquals(0, player2.getPlayerBoard().getMarks().size());
        Assert.assertEquals(0, player5.getPlayerBoard().getMarks().size());
        Assert.assertFalse(hellion.isUsable());
    }

    @Test
    public void effectTwoTest(){
        TargetPlayerRequestEvent message = (TargetPlayerRequestEvent) hellion.getTargetEffect(2);
        Assert.assertEquals(2, message.getPossibleTargets().size());
        Assert.assertTrue(message.getPossibleTargets().contains(player4.getCharacter()));
        Assert.assertTrue(message.getPossibleTargets().contains(player3.getCharacter()));

        ArrayList<Object> target = new ArrayList<>();
        target.add(player4);
        hellion.performEffect(2, target);
        Assert.assertEquals(1, player4.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player1.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(0, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(2, player4.getPlayerBoard().getMarks().size());
        Assert.assertEquals(2, player3.getPlayerBoard().getMarks().size());
        Assert.assertEquals(0, player1.getPlayerBoard().getMarks().size());
        Assert.assertEquals(0, player2.getPlayerBoard().getMarks().size());
        Assert.assertEquals(0, player5.getPlayerBoard().getMarks().size());
        Assert.assertFalse(hellion.isUsable());
    }
    
}
