package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.CharacterRequestEvent;
import it.polimi.ingsw.event.controller_view_event.WinnerEvent;
import it.polimi.ingsw.event.view_controller_event.CharacterChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;


public class GameManagerTest {
    private HashMap<String, VirtualView> hashMap;
    private Controller controller;
    private GameManager gameManager;
    private GameBoard gameBoard;
    private Player player1;
    private Player player2;
    private Square[][] map;
    private GameModel model;

    @Before
    public void setUp(){
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        controller = new Controller(hashMap, 3);
        gameManager = controller.getGameManager();
        gameBoard = gameManager.getModel().getGameboard();
        model = gameManager.getModel();
        map = gameBoard.getMap().getSquareMatrix();
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.DOZER);
        gameManager.getModel().getPlayers().add(player1);
        gameManager.getModel().getPlayers().add(player2);
    }

    @Test
    public void buildGameBoardTest(){
        GameManager gameManager = new GameManager(null, 0);
        Square[][] map = gameManager.getModel().getGameboard().getMap().getSquareMatrix();
        Assert.assertEquals("grey", map[2][0].getSquareColour());
        Assert.assertEquals("green", map[0][3].getSquareColour());

        gameManager = new GameManager(null, 1);
        map = gameManager.getModel().getGameboard().getMap().getSquareMatrix();
        Assert.assertEquals("grey", map[2][0].getSquareColour());
        Assert.assertNull(map[0][3]);

        gameManager = new GameManager(null, 2);
        map = gameManager.getModel().getGameboard().getMap().getSquareMatrix();
        Assert.assertEquals("green", map[0][3].getSquareColour());
        Assert.assertNull(map[2][0]);

        gameManager = new GameManager(null, 3);
        map = gameManager.getModel().getGameboard().getMap().getSquareMatrix();
        Assert.assertNull(map[2][0]);
        Assert.assertNull(map[0][3]);

        try{
            gameManager = new GameManager(null, 5);
            Assert.fail();
        }
        catch (InvalidParameterException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void refillMapTest(){
        AmmoTile ammoTile1 = new AmmoTile(new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow), null, true);
        ((BasicSquare) map[0][0]).setAmmo(ammoTile1);
        gameManager.newRound();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (map[i][j] != null) {
                    if (!gameBoard.getMap().getSpawnSquares().contains(map[i][j]))
                        Assert.assertTrue(((BasicSquare) map[i][j]).checkAmmo());
                    else
                        Assert.assertEquals(3, ((SpawnSquare)map[i][j]).getWeapons().size() );
                }
            }
        }
        Assert.assertEquals(ammoTile1, ((BasicSquare)map[0][0]).getAmmo());
        Assert.assertNotEquals(((BasicSquare)map[0][1]).getAmmo(), ((BasicSquare)map[1][1]).getAmmo());
        ArrayList<Weapon> weapons = new ArrayList<>();
        for (SpawnSquare s:gameBoard.getMap().getSpawnSquares()) {
            weapons.addAll(s.getWeapons());
        }
        for (Weapon w: weapons) {
            for (int i = 0; i < weapons.size(); i++) {
                if (weapons.indexOf(w) != i)
                    Assert.assertNotEquals(w, weapons.get(i));
            }
        }
    }

    @Test
    public void characterChoiceTest(){
        VirtualView virtualView1 = new VirtualView("Federico");
        VirtualView virtualView2 = new VirtualView("Francesco");
        hashMap = new HashMap<>();
        hashMap.put("Federico", virtualView1);
        hashMap.put("Francesco", virtualView2);
        controller = new Controller(hashMap, 3);
        GameManager gameManager = controller.getGameManager();
        GameModel model = controller.getGameManager().getModel();
        ArrayList<Character> expectedCharacter = new ArrayList<>();
        expectedCharacter.add(Character.SPROG);
        expectedCharacter.add(Character.BANSHEE);
        expectedCharacter.add(Character.DOZER);
        expectedCharacter.add(Character.D_STRUCT_OR);
        expectedCharacter.add(Character.VIOLET);

        CharacterRequestEvent messageRequest = (CharacterRequestEvent) hashMap.get("Federico").getToRemoteView();
        Assert.assertEquals(5, messageRequest.getAvailableCharacter().size());
        for (Character c: expectedCharacter ) {
            Assert.assertTrue(messageRequest.getAvailableCharacter().contains(c));
        }

        CharacterChoiceEvent messageChoice = new CharacterChoiceEvent("Federico", Character.SPROG);
        messageChoice.performAction(controller);
        Assert.assertEquals(1, model.getPlayers().size());
        Assert.assertEquals("Federico", model.getPlayers().get(0).getUsername());
        Assert.assertEquals(Character.SPROG, model.getPlayers().get(0).getCharacter());
        Assert.assertTrue(model.getPlayers().get(0).isFirstPlayer());
        Assert.assertNull(gameManager.getCurrentRound());

        messageRequest = (CharacterRequestEvent) hashMap.get("Francesco").getToRemoteView();
        Assert.assertEquals(4, messageRequest.getAvailableCharacter().size());
        expectedCharacter.remove(Character.SPROG);

        for (Character c: expectedCharacter ) {
            Assert.assertTrue(messageRequest.getAvailableCharacter().contains(c));
        }

        messageChoice = new CharacterChoiceEvent("Francesco", Character.DOZER);
        messageChoice.performAction(controller);
        Assert.assertEquals(2, model.getPlayers().size());
        Assert.assertEquals("Francesco", model.getPlayers().get(1).getUsername());
        Assert.assertEquals(Character.DOZER, model.getPlayers().get(1).getCharacter());
        Assert.assertFalse(model.getPlayers().get(1).isFirstPlayer());
        Assert.assertNotNull(gameManager.getCurrentRound());
    }

    @Test
    public void endGameTest(){
        hashMap.put("Evandro", new VirtualView("Evandro"));
        hashMap.put("Test1", new VirtualView("Test1"));
        hashMap.put("Test2", new VirtualView("Test2"));
        Player player3 = new Player("Evandro", Character.D_STRUCT_OR);
        Player player4 = new Player("Test1", Character.BANSHEE);
        Player player5 = new Player("Test2", Character.VIOLET);
        model.addPlayer(player3);
        model.addPlayer(player4);
        model.addPlayer(player5);
        KillShotTrack gameTrack = (KillShotTrack) gameBoard.getGameTrack();
        player1.addPoints(12);
        player2.addPoints(2);
        player4.addPoints(8);

        // p1 = 5, p2 = 6, p3 = 9, p4 = 6
        player3.getPlayerBoard().addSkull();
        player3.getPlayerBoard().addDamages(player1, 4);
        player3.getPlayerBoard().addDamages(player2, 5);
        player5.getPlayerBoard().addDamages(player3, 3);
        player5.getPlayerBoard().addDamages(player4, 4);
        player5.getPlayerBoard().addDamages(player3, 1);

        // p1 = 2(2), p2 = 3(6), p3 = 3(4), p4 = 4(8), p5 = 0(0)
        gameTrack.evaluateDamage(new DamageToken(player1), 1);
        gameTrack.evaluateDamage(new DamageToken(player2), 2);
        gameTrack.evaluateDamage(new DamageToken(player3), 2);
        gameTrack.evaluateDamage(new DamageToken(player2), 1);
        gameTrack.evaluateDamage(new DamageToken(player1), 1);
        gameTrack.evaluateDamage(new DamageToken(player4), 2);
        gameTrack.evaluateDamage(new DamageToken(player3), 1);
        gameTrack.evaluateDamage(new DamageToken(player4), 2);
        Assert.assertTrue(gameTrack.checkEndTrack());
        gameManager.endGame();
        Assert.assertEquals(19, player1.getPoints());
        Assert.assertEquals(14, player2.getPoints());
        Assert.assertEquals(13, player3.getPoints());
        Assert.assertEquals(22, player4.getPoints());
        Assert.assertEquals(0, player5.getPoints());
        Assert.assertNotNull(hashMap.get(player4.getUsername()).getToRemoteView());
        Assert.assertEquals(22, ((WinnerEvent)hashMap.get(player4.getUsername()).getToRemoteView()).getPoint());
    }

}
