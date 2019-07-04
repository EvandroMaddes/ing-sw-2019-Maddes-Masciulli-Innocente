package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controllerviewevent.CharacterRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.RespawnRequestEvent;
import it.polimi.ingsw.event.viewcontrollerevent.*;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.powerups.Teleporter;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class DisconnectionManagerTest {
    private HashMap<String, VirtualView> hashMap;
    private Controller controller;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Square[][] map;

    @Before
    public void setUp(){
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        hashMap.put("Evandro", new VirtualView("Evandro"));
        hashMap.put("Chiara", new VirtualView("Chiara"));
        hashMap.put("TestPlayer", new VirtualView("TestPlayer"));
        controller = new Controller(hashMap, 3);
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.DOZER);
        player3 = new Player("Evandro", Character.D_STRUCT_OR);
        player4 = new Player("Chiara", Character.VIOLET);
        player5 = new Player("TestPlayer", Character.BANSHEE);
        controller.getGameManager().getModel().getPlayers().add(player1);
        controller.getGameManager().getModel().getPlayers().add(player2);
        controller.getGameManager().getModel().getPlayers().add(player3);
        controller.getGameManager().getModel().getPlayers().add(player4);
        controller.getGameManager().getModel().getPlayers().add(player5);
        map = controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix();
        controller.getGameManager().setPlayerTurn(0);
        player1.setPosition(map[0][0]);
        player2.setPosition(map[0][1]);
        player3.setPosition(map[0][2]);
        player4.setPosition(map[1][0]);
        player5.setPosition(map[0][0]);
        SetUpObserverObservable.connect(controller.getGameManager().getModel().getPlayers(), controller.getUsersVirtualView(), controller.getGameManager().getModel());
    }

    @Test
    public void disconnectionOnAnotherRoundTest(){
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals(map[0][2], player3.getPosition());
        controller.getGameManager().newRound();
        ViewControllerEvent disconnectionEvent = new DisconnectedEvent(player3.getUsername());
        disconnectionEvent.performAction(controller);
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertTrue(controller.getGameManager().getModel().getPlayers().contains(player3));
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertTrue(controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().contains(player3));
        Assert.assertEquals(map[0][2], player3.getPosition());
        Assert.assertTrue(map[0][2].getSquarePlayers().contains(player3));
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
    }

    @Test
    public void disconnectedOnHisRoundTest(){
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals(map[0][1], player2.getPosition());
        controller.getGameManager().newRound();
        ViewControllerEvent choiceEvent = new ActionChoiceEvent(player2.getUsername(), 1);
        choiceEvent.performAction(controller);
        Assert.assertEquals(player2, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        ViewControllerEvent disconnectionEvent = new DisconnectedEvent(player2.getUsername());
        disconnectionEvent.performAction(controller);
        Assert.assertEquals(4, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertFalse(controller.getGameManager().getModel().getPlayers().contains(player2));
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertTrue(controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().contains(player2));
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertEquals(map[0][1], player2.getPosition());
        Assert.assertFalse(map[0][2].getSquarePlayers().contains(player2));
        Assert.assertEquals(player3, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
    }

    @Test
    public void disconnectedOnAnotherRoundThanWaitHisRoundTest(){
        controller.getGameManager().newRound();
        ViewControllerEvent choiceEvent = new SkipActionChoiceEvent(player2.getUsername());
        choiceEvent.performAction(controller);
        ViewControllerEvent disconnectionEvent = new DisconnectedEvent(player4.getUsername());
        disconnectionEvent.performAction(controller);
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertTrue(controller.getGameManager().getModel().getPlayers().contains(player4));
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertTrue(controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().contains(player4));
        choiceEvent.performAction(controller);
        Assert.assertEquals(player3, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertTrue(controller.getGameManager().getModel().getPlayers().contains(player4));
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertTrue(controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().contains(player4));
        choiceEvent.performAction(controller);
        choiceEvent.performAction(controller);
        Assert.assertEquals(player5, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        Assert.assertEquals(4, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertFalse(controller.getGameManager().getModel().getPlayers().contains(player4));
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertTrue(controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().contains(player4));
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertFalse(controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().contains(player4));
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
    }

    @Test
    public void disconnectionBeforeToChoseTheCharacterAndReconnectedInFirstRoundPhaseTest(){
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        hashMap.put("Evandro", new VirtualView("Evandro"));
        hashMap.put("Chiara", new VirtualView("Chiara"));
        controller = new Controller(hashMap, 3);
        Event requestMessage = hashMap.get("Federico").getToRemoteView();
        Assert.assertEquals(5, ((CharacterRequestEvent)requestMessage).getAvailableCharacter().size());
        ViewControllerEvent choiceEvent = new CharacterChoiceEvent("Federico", Character.DOZER);
        choiceEvent.performAction(controller);
        Assert.assertEquals(1, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals("Federico", controller.getGameManager().getModel().getPlayers().get(0).getUsername());
        requestMessage = hashMap.get("Chiara").getToRemoteView();
        Assert.assertEquals(4, ((CharacterRequestEvent)requestMessage).getAvailableCharacter().size());
        DisconnectedEvent disconnectedEvent = new DisconnectedEvent("Francesco");
        disconnectedEvent.performAction(controller);
        choiceEvent = new CharacterChoiceEvent("Chiara", Character.SPROG);
        choiceEvent.performAction(controller);
        Assert.assertEquals(3, controller.getGameManager().getModel().getPlayers().size());
        choiceEvent = new CharacterChoiceEvent("Evandro", Character.BANSHEE);
        choiceEvent.performAction(controller);
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertEquals(4, controller.getGameManager().getModel().getPlayers().size());
        requestMessage = hashMap.get("Federico").getToRemoteView();
        Assert.assertEquals(2, ((RespawnRequestEvent) requestMessage).getPowerUpNames().length);
        choiceEvent = new SpawnChoiceEvent("Federico", "Teleporter", CubeColour.Blue);
        controller.getGameManager().getModel().getPlayers().get(0).getPowerUps().clear();
        controller.getGameManager().getModel().getPlayers().get(0).addPowerUp(new Teleporter(CubeColour.Blue));
        choiceEvent.performAction(controller);
        Assert.assertEquals("Federico", controller.getGameManager().getCurrentRound().getCurrentPlayer().getUsername());
        choiceEvent = new SkipActionChoiceEvent("Federico");
        choiceEvent.performAction(controller);
        choiceEvent.performAction(controller);
        Assert.assertEquals(3, controller.getGameManager().getModel().getPlayers().size());
        for (Player p: controller.getGameManager().getModel().getPlayers()) {
            Assert.assertNotEquals("Francesco", p.getUsername());
        }
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertEquals("Francesco", controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().get(0).getUsername());
        Assert.assertEquals("Federico",  controller.getGameManager().getModel().getPlayers().get(0).getUsername());
        Assert.assertEquals("Chiara",  controller.getGameManager().getModel().getPlayers().get(1).getUsername());
        Assert.assertEquals("Evandro",  controller.getGameManager().getModel().getPlayers().get(2).getUsername());
        choiceEvent = new SpawnChoiceEvent("Chiara", "Teleporter", CubeColour.Blue);
        controller.getGameManager().getModel().getPlayers().get(1).getPowerUps().clear();
        controller.getGameManager().getModel().getPlayers().get(1).addPowerUp(new Teleporter(CubeColour.Blue));
        choiceEvent.performAction(controller);
        Assert.assertEquals("Chiara",  controller.getGameManager().getModel().getPlayers().get(1).getUsername());
        controller.getGameManager().getModel().getPlayers().get(1).getPowerUps().clear();
        choiceEvent = new SkipActionChoiceEvent("Chiara");
        choiceEvent.performAction(controller);
        choiceEvent.performAction(controller);
        Assert.assertEquals("Evandro", controller.getGameManager().getCurrentRound().getCurrentPlayer().getUsername());
        choiceEvent = new SpawnChoiceEvent("Evandro", "Teleporter", CubeColour.Blue);
        controller.getGameManager().getModel().getPlayers().get(2).getPowerUps().clear();
        controller.getGameManager().getModel().getPlayers().get(2).addPowerUp(new Teleporter(CubeColour.Blue));
        choiceEvent.performAction(controller);
        choiceEvent = new SkipActionChoiceEvent("Evandro");
        choiceEvent.performAction(controller);
        Assert.assertTrue(controller.getGameManager().isFirstRoundPhase());
        ReconnectedEvent reconnectedEvent = new ReconnectedEvent("Francesco");
        reconnectedEvent.performAction(controller);
        Assert.assertEquals(4, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertEquals("Evandro", controller.getGameManager().getCurrentRound().getCurrentPlayer().getUsername());
        Assert.assertEquals("Francesco", controller.getGameManager().getModel().getPlayers().get(3).getUsername());
        Assert.assertNull(controller.getGameManager().getModel().getPlayers().get(3).getPosition());
        choiceEvent.performAction(controller);
        requestMessage = hashMap.get("Francesco").getToRemoteView();
        Assert.assertEquals(2, ((RespawnRequestEvent) requestMessage).getPowerUpNames().length);
    }



}
