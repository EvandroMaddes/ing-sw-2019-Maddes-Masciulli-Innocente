package it.polimi.ingsw.model.game_components.cards.weapon;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.weapons.Whisper;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class WhisperTest {
    private Whisper whisper;
    private Square[][] map;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @Before
    public void setUp(){
        whisper = new Whisper();
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
        player1 = new Player("Federico", Character.DOZER);
        player2 = new Player("Evandro", Character.SPROG);
        player3 = new Player("Francesco", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("Test", Character.BANSHEE);
        player1.addWeapon(whisper);
        player1.setPosition(map[0][2]);
        player2.setPosition(map[0][2]);
        player3.setPosition(map[1][1]);
        player4.setPosition(map[2][3]);
        player5.setPosition(map[1][3]);
    }

    @Test
    public void isUsableTest(){
        Assert.assertTrue(whisper.isUsable());
        Assert.assertTrue(whisper.isUsableEffect(1));
    }

    @Test
    public void effectTest(){
        ControllerViewEvent message = whisper.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player4.getCharacter());
        expectedTargets.add(player5.getCharacter());

        Assert.assertEquals(2, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player5);
        whisper.performEffect(1, target);
        Assert.assertEquals(3, player5.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player5.getPlayerBoard().getMarks().size());
        Assert.assertFalse(whisper.isUsable());
    }

    @Test
    public void effectTestOnSmallMap(){
        Map gameMap = new Map(Map.SMALL_LEFT, Map.SMALL_RIGHT);
        map = gameMap.getSquareMatrix();
        player1.setPosition(map[1][0]);
        player2.setPosition(map[1][2]);

        ControllerViewEvent message = whisper.getTargetEffect(1);
        ArrayList<Character> expectedTargets = new ArrayList<>();
        expectedTargets.add(player2.getCharacter());

        Assert.assertEquals(1, ((TargetPlayerRequestEvent)message).getPossibleTargets().size());
        for (Character c:expectedTargets) {
            Assert.assertTrue(((TargetPlayerRequestEvent)message).getPossibleTargets().contains(c));
        }

        ArrayList<Object> target = new ArrayList<>();
        target.add(player2);
        whisper.performEffect(1, target);
        Assert.assertEquals(3, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player2.getPlayerBoard().getMarks().size());
        Assert.assertFalse(whisper.isUsable());
    }
}
