package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.RespawnRequestEvent;
import it.polimi.ingsw.event.view_controller_event.SkipActionChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.SpawnChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;
import it.polimi.ingsw.model.board.KillShotTrack;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.power_ups.TagbackGrenade;
import it.polimi.ingsw.model.game_components.cards.power_ups.Teleporter;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class DeathManagerTest {
    private HashMap<String, VirtualView> hashMap;
    private Controller controller;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Square[][] map;
    private RoundManager roundManager;

    @Before
    public void setUp(){
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        hashMap.put("Evandro", new VirtualView("Evandro"));
        hashMap.put("Chiara", new VirtualView("Chiara"));
        controller = new Controller(hashMap, 0);
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.DOZER);
        player3 = new Player("Evandro", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        controller.getGameManager().getModel().getPlayers().add(player1);
        controller.getGameManager().getModel().getPlayers().add(player2);
        controller.getGameManager().getModel().getPlayers().add(player3);
        controller.getGameManager().getModel().getPlayers().add(player4);
        map = controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix();
        SetUpObserverObservable.connect(controller.getGameManager().getModel().getPlayers(), controller.getUsersVirtualView(), controller.getGameManager().getModel());
    }

    @Test
    public void endRoundRespawnTest(){
        controller.getGameManager().setPlayerTurn(3);
        player1.setPosition(map[0][0]);
        player2.setPosition(map[0][0]);
        player3.setPosition(map[0][0]);
        player4.setPosition(map[0][0]);
        PowerUp tagbackGrenade = new TagbackGrenade(CubeColour.Blue);
        PowerUp teleporter = new Teleporter(CubeColour.Yellow);
        player2.addPowerUp(tagbackGrenade);
        player3.addPowerUp(new Teleporter(CubeColour.Blue));
        player3.addPowerUp(teleporter);
        player3.addPowerUp(new TagbackGrenade(CubeColour.Yellow));
        player2.getPlayerBoard().addDamages(player1, 6);
        player2.getPlayerBoard().addDamages(player3, 4);
        player3.getPlayerBoard().addDamages(player1, 6);
        player3.getPlayerBoard().addDamages(player2, 6);
        controller.getGameManager().newRound();
        Assert.assertEquals(player1, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        ViewControllerEvent choiceMessage = new SkipActionChoiceEvent(player1.getUsername());
        choiceMessage.performAction(controller);
        Assert.assertEquals(4, controller.getGameManager().getCurrentRound().getPhase());
        choiceMessage.performAction(controller);
        //first respawn(player2)
        Assert.assertEquals(player1, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        Assert.assertEquals(8, controller.getGameManager().getCurrentRound().getPhase());
        Assert.assertEquals(9, player1.getPoints());
        Assert.assertEquals(6, player3.getPoints());
        Assert.assertEquals(1, player2.getPlayerBoard().getSkullsNumber());
        Assert.assertEquals(0, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(7, controller.getGameManager().getModel().getGameboard().getGameTrack().getSkullBox());
        Assert.assertEquals(player3, ((KillShotTrack)controller.getGameManager().getModel().getGameboard().getGameTrack()).getTokenTrack().get(0).getPlayer());
        Assert.assertEquals(1, ((KillShotTrack)controller.getGameManager().getModel().getGameboard().getGameTrack()).getTokenTrack().size());
        Assert.assertEquals(1, controller.getGameManager().getModel().getGameboard().getGameTrack().getTokenSequence()[0]);
        Assert.assertEquals(0, controller.getGameManager().getModel().getGameboard().getGameTrack().getTokenSequence()[2]);
        Assert.assertEquals(0, controller.getGameManager().getModel().getGameboard().getGameTrack().getTokenSequence()[1]);
        Event requestEvent = hashMap.get(player2.getUsername()).getToRemoteView();
        Assert.assertEquals(2 ,((RespawnRequestEvent)requestEvent).getPowerUpNames().length);
        Assert.assertEquals("TagbackGrenade", ((RespawnRequestEvent)requestEvent).getPowerUpNames()[0]);
        Assert.assertEquals(CubeColour.Blue, ((RespawnRequestEvent)requestEvent).getPowerUpColours()[0]);
        choiceMessage = new SpawnChoiceEvent(player2.getUsername(), "TagbackGrenade", CubeColour.Blue);
        choiceMessage.performAction(controller);
        Assert.assertEquals(1, player2.getPowerUps().size());
        Assert.assertFalse(player2.getPowerUps().contains(tagbackGrenade));
        Assert.assertEquals(map[0][2], player2.getPosition());
        Assert.assertEquals(8, controller.getGameManager().getCurrentRound().getPhase());
        Assert.assertEquals(player1, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        //second respawn (player3)
        Assert.assertEquals(18, player1.getPoints());
        Assert.assertEquals(6, player2.getPoints());
        Assert.assertEquals(6, player3.getPoints());
        Assert.assertEquals(1, player2.getPlayerBoard().getSkullsNumber());
        Assert.assertEquals(0, player2.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(1, player3.getPlayerBoard().getSkullsNumber());
        Assert.assertEquals(0, player3.getPlayerBoard().getDamageAmount());
        Assert.assertEquals(6, controller.getGameManager().getModel().getGameboard().getGameTrack().getSkullBox());
        Assert.assertEquals(3, ((KillShotTrack)controller.getGameManager().getModel().getGameboard().getGameTrack()).getTokenTrack().size());
        Assert.assertEquals(player3, ((KillShotTrack)controller.getGameManager().getModel().getGameboard().getGameTrack()).getTokenTrack().get(0).getPlayer());
        Assert.assertEquals(player2, ((KillShotTrack)controller.getGameManager().getModel().getGameboard().getGameTrack()).getTokenTrack().get(1).getPlayer());
        Assert.assertEquals(player2, ((KillShotTrack)controller.getGameManager().getModel().getGameboard().getGameTrack()).getTokenTrack().get(2).getPlayer());
        Assert.assertEquals(1, controller.getGameManager().getModel().getGameboard().getGameTrack().getTokenSequence()[0]);
        Assert.assertEquals(2, controller.getGameManager().getModel().getGameboard().getGameTrack().getTokenSequence()[1]);
        Assert.assertEquals(0, controller.getGameManager().getModel().getGameboard().getGameTrack().getTokenSequence()[2]);
        requestEvent = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(4 ,((RespawnRequestEvent)requestEvent).getPowerUpNames().length);
        Assert.assertEquals(teleporter.getName(), ((RespawnRequestEvent)requestEvent).getPowerUpNames()[0]);
        Assert.assertEquals(teleporter.getName(), ((RespawnRequestEvent)requestEvent).getPowerUpNames()[1]);
        Assert.assertEquals("TagbackGrenade", ((RespawnRequestEvent)requestEvent).getPowerUpNames()[2]);
        Assert.assertEquals(CubeColour.Blue, ((RespawnRequestEvent)requestEvent).getPowerUpColours()[0]);
        Assert.assertEquals(CubeColour.Yellow, ((RespawnRequestEvent)requestEvent).getPowerUpColours()[1]);
        Assert.assertEquals(CubeColour.Yellow, ((RespawnRequestEvent)requestEvent).getPowerUpColours()[2]);
        choiceMessage = new SpawnChoiceEvent(player2.getUsername(), "Teleporter", CubeColour.Yellow);
        choiceMessage.performAction(controller);
        Assert.assertEquals(3, player3.getPowerUps().size());
        Assert.assertFalse(player2.getPowerUps().contains(teleporter));
        Assert.assertEquals(map[2][3], player3.getPosition());
    }
}
