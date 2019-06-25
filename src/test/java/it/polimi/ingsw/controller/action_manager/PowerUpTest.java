package it.polimi.ingsw.controller.action_manager;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.RoundManager;
import it.polimi.ingsw.controller.SetUpObserverObservable;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.AsActionPowerUpRequestEvent;
import it.polimi.ingsw.event.controller_view_event.NewtonPlayerTargetRequestEvent;
import it.polimi.ingsw.event.controller_view_event.NewtonTargetSquareRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TeleporterTargetRequestEvent;
import it.polimi.ingsw.event.view_controller_event.NewtonPlayerTargetChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.PowerUpChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.PowerUpSquareTargetChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.power_ups.Newton;
import it.polimi.ingsw.model.game_components.cards.power_ups.Teleporter;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class PowerUpTest {
    private HashMap<String, VirtualView> hashMap;
    private Controller controller;
    private Player player1;
    private Player player2;
    private Player player3;
    private Square[][] map;
    private RoundManager roundManager;

    @Before
    public void setUp() {
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        hashMap.put("Evandro", new VirtualView("Evandro"));
        controller = new Controller(hashMap, 0);
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.DOZER);
        player3 = new Player("Evandro", Character.D_STRUCT_OR);
        controller.getGameManager().getModel().getPlayers().add(player1);
        controller.getGameManager().getModel().getPlayers().add(player2);
        controller.getGameManager().getModel().getPlayers().add(player3);
        map = controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix();
        controller.getGameManager().setCurrentRound(new RoundManager(controller, player1));
        roundManager = controller.getGameManager().getCurrentRound();
        controller.getGameManager().setPlayerTurn(0);
        player1.setPosition(map[0][0]);
        SetUpObserverObservable.connect(controller.getGameManager().getModel().getPlayers(), controller.getUsersVirtualView(), controller.getGameManager().getModel());
    }

    @Test
    public void teleporterTest(){
        PowerUp teleporter = new Teleporter(CubeColour.Blue);
        player1.addPowerUp(teleporter);
        player1.setPosition(map[0][0]);
        roundManager.manageRound();
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((AsActionPowerUpRequestEvent)requestMessage).getPowerUpNames().length);
        Assert.assertEquals(teleporter.getName(), ((AsActionPowerUpRequestEvent)requestMessage).getPowerUpNames()[0]);
        ViewControllerEvent choiceMessage = new PowerUpChoiceEvent(player1.getUsername(), teleporter.getName(), CubeColour.Blue);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(12, ((TeleporterTargetRequestEvent)requestMessage).getPossibleSquareX().length);
        choiceMessage = new PowerUpSquareTargetChoiceEvent(player1.getUsername(), 2, 0);
        choiceMessage.performAction(controller);
        Assert.assertEquals(map[2][0], player1.getPosition());
        Assert.assertEquals(0, player1.getPowerUps().size());
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
    }

    @Test
    public void newtonTest(){
        PowerUp newton = new Newton(CubeColour.Blue);
        player1.addPowerUp(newton);
        player1.setPosition(map[0][0]);
        player2.setPosition(map[0][0]);
        Assert.assertFalse(controller.getGameManager().isFirstRoundPhase());
        roundManager.manageRound();
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((AsActionPowerUpRequestEvent)requestMessage).getPowerUpNames().length);
        Assert.assertEquals(newton.getName(), ((AsActionPowerUpRequestEvent)requestMessage).getPowerUpNames()[0]);
        ViewControllerEvent choiceMessage = new PowerUpChoiceEvent(player1.getUsername(), newton.getName(), CubeColour.Blue);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((NewtonPlayerTargetRequestEvent)requestMessage).getPossibleTargets().size());
        choiceMessage = new NewtonPlayerTargetChoiceEvent(player1.getUsername(), player2.getCharacter());
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(4, ((NewtonTargetSquareRequestEvent)requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0,0,1,2};
        int[] expectedY = new int[]{1,2,0,0};
        for (int i = 0; i < 4; i++) {
            boolean check = false;
            for (int j = 0; j < 4; j++) {
                if (expectedX[i] == ((NewtonTargetSquareRequestEvent)requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((NewtonTargetSquareRequestEvent)requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
        choiceMessage = new PowerUpSquareTargetChoiceEvent(player1.getUsername(), 0, 2);
        choiceMessage.performAction(controller);

        Assert.assertEquals(map[0][2], player2.getPosition());
        Assert.assertEquals(0, player1.getPowerUps().size());
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
    }
}
