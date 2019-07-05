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
    public void setUp() {
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

    /**
     * Check that a player disconnected during an another player round is correctly managed, putting him into the disconnection queue
     */
    @Test
    public void disconnectionOnAnotherRoundTest() {
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

    /**
     * Check that a player disconnected in his round is correctly managed, removing him from the game and skipping to the next round
     */
    @Test
    public void disconnectedOnHisRoundTest() {
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

    /**
     * Check that a player disconnected in another player round is correctly removed from the game at his first round, if he didn't reconnect before
     */
    @Test
    public void disconnectedOnAnotherRoundThanWaitHisRoundTest() {
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

    /**
     * Check that a player can correctly disconnect before the character choice.
     * A default character is given to him.
     * Than check that if he reconnect during the first round phase, he can correctly have his spawn phase
     */
    @Test
    public void disconnectionBeforeToChoseTheCharacterAndReconnectedInFirstRoundPhaseTest() {
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        hashMap.put("Evandro", new VirtualView("Evandro"));
        hashMap.put("Chiara", new VirtualView("Chiara"));
        controller = new Controller(hashMap, 3);
        Event requestMessage = hashMap.get("Federico").getToRemoteView();
        Assert.assertEquals(5, ((CharacterRequestEvent) requestMessage).getAvailableCharacter().size());
        ViewControllerEvent choiceEvent = new CharacterChoiceEvent("Federico", Character.DOZER);
        choiceEvent.performAction(controller);
        Assert.assertEquals(1, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals("Federico", controller.getGameManager().getModel().getPlayers().get(0).getUsername());
        requestMessage = hashMap.get("Chiara").getToRemoteView();
        Assert.assertEquals(4, ((CharacterRequestEvent) requestMessage).getAvailableCharacter().size());
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
        for (Player p : controller.getGameManager().getModel().getPlayers()) {
            Assert.assertNotEquals("Francesco", p.getUsername());
        }
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertEquals("Francesco", controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().get(0).getUsername());
        Assert.assertEquals("Federico", controller.getGameManager().getModel().getPlayers().get(0).getUsername());
        Assert.assertEquals("Chiara", controller.getGameManager().getModel().getPlayers().get(1).getUsername());
        Assert.assertEquals("Evandro", controller.getGameManager().getModel().getPlayers().get(2).getUsername());
        choiceEvent = new SpawnChoiceEvent("Chiara", "Teleporter", CubeColour.Blue);
        controller.getGameManager().getModel().getPlayers().get(1).getPowerUps().clear();
        controller.getGameManager().getModel().getPlayers().get(1).addPowerUp(new Teleporter(CubeColour.Blue));
        choiceEvent.performAction(controller);
        Assert.assertEquals("Chiara", controller.getGameManager().getModel().getPlayers().get(1).getUsername());
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

    /**
     * Check that a player reconnected before of his next round after disconnection is correctly rejoin the game with no penalties
     */
    @Test
    public void reconnectionInTheDisconnectionQueue() {
        controller.getGameManager().newRound();
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals(player2, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        ViewControllerEvent choiceMessage = new SkipActionChoiceEvent(player2.getUsername());
        choiceMessage.performAction(controller);
        DisconnectedEvent disconnectedEvent = new DisconnectedEvent(player4.getUsername());
        disconnectedEvent.performAction(controller);
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals(1, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertTrue(controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().contains(player4));
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        choiceMessage.performAction(controller);
        Assert.assertEquals(player3, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        ReconnectedEvent reconnectedEvent = new ReconnectedEvent(player4.getUsername());
        reconnectedEvent.performAction(controller);
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectedPlayers().size());
        Assert.assertEquals(0, controller.getGameManager().getDisconnectionManager().getDisconnectingQueue().size());
        Assert.assertEquals(player3, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        choiceMessage.performAction(controller);
        choiceMessage.performAction(controller);
        Assert.assertEquals(player4, controller.getGameManager().getCurrentRound().getCurrentPlayer());
        choiceMessage.performAction(controller);
        choiceMessage.performAction(controller);
        Assert.assertEquals(player5, controller.getGameManager().getCurrentRound().getCurrentPlayer());
    }

    /**
     * Check that a default character is correctly given to a player that disconnect without choosing one
     */
    @Test
    public void defaultCharacterTest() {
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        hashMap.put("Evandro", new VirtualView("Evandro"));
        hashMap.put("Chiara", new VirtualView("Chiara"));
        hashMap.put("TestPlayer", new VirtualView("TestPlayer"));
        controller = new Controller(hashMap, 3);
        Event requestMessage = hashMap.get("Federico").getToRemoteView();
        Assert.assertEquals(5, ((CharacterRequestEvent) requestMessage).getAvailableCharacter().size());
        ViewControllerEvent choiceEvent = new CharacterChoiceEvent("Federico", Character.DOZER);
        choiceEvent.performAction(controller);
        Assert.assertEquals(1, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals("Federico", controller.getGameManager().getModel().getPlayers().get(0).getUsername());
        requestMessage = hashMap.get("Chiara").getToRemoteView();
        Assert.assertEquals(4, ((CharacterRequestEvent) requestMessage).getAvailableCharacter().size());
        choiceEvent = new CharacterChoiceEvent("Chiara", Character.SPROG);
        choiceEvent.performAction(controller);
        Assert.assertEquals(2, controller.getGameManager().getModel().getPlayers().size());
        requestMessage = hashMap.get("Francesco").getToRemoteView();
        Assert.assertEquals(3, ((CharacterRequestEvent) requestMessage).getAvailableCharacter().size());
        choiceEvent = new CharacterChoiceEvent("Francesco", Character.BANSHEE);
        choiceEvent.performAction(controller);
        Assert.assertEquals(3, controller.getGameManager().getModel().getPlayers().size());
        requestMessage = hashMap.get("TestPlayer").getToRemoteView();
        Assert.assertEquals(2, ((CharacterRequestEvent) requestMessage).getAvailableCharacter().size());
        choiceEvent = new CharacterChoiceEvent("TestPlayer", Character.D_STRUCT_OR);
        choiceEvent.performAction(controller);
        DisconnectedEvent disconnectedEvent = new DisconnectedEvent("Evandro");
        disconnectedEvent.performAction(controller);
        Assert.assertEquals(5, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals(Character.VIOLET, controller.getGameManager().getModel().getPlayers().get(4).getCharacter());
        Assert.assertEquals("Federico", controller.getGameManager().getCurrentRound().getCurrentPlayer().getUsername());
    }

}
