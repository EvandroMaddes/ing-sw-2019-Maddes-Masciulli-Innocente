package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.view_controller_event.DisconnectedEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;
import it.polimi.ingsw.model.board.Square;
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
    public void disconnectionOnAnotherROundTest(){
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

}
