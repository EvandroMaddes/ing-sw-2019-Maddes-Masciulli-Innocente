package it.polimi.ingsw.controller.action_manager;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.RoundManager;
import it.polimi.ingsw.controller.SetUpObserverObservable;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.*;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.power_ups.Newton;
import it.polimi.ingsw.model.game_components.cards.power_ups.TagbackGrenade;
import it.polimi.ingsw.model.game_components.cards.power_ups.Teleporter;
import it.polimi.ingsw.model.game_components.cards.weapons.Electroscythe;
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

    @Test
    public void tagbackGrenadeTest(){
        controller.getGameManager().setPlayerTurn(2);
        player1.setPosition(map[0][0]);
        player2.setPosition(map[0][0]);
        player3.setPosition(map[0][0]);
        PowerUp tagbackGrenade1 = new TagbackGrenade(CubeColour.Blue);
        PowerUp tagbackGrenade2 = new TagbackGrenade(CubeColour.Yellow);
        player2.addPowerUp(tagbackGrenade1);
        player3.addPowerUp(tagbackGrenade2);
        Weapon electroscythe = new Electroscythe();
        player1.addWeapon(electroscythe);
        controller.getGameManager().newRound();
        Assert.assertFalse(controller.getGameManager().isFirstRoundPhase());
        ViewControllerEvent choiceMessage = new SkipActionChoiceEvent(player1.getUsername());
        hashMap.get(player1.getUsername()).addObserver(controller);
        hashMap.get(player1.getUsername()).toController(choiceMessage);
        Assert.assertEquals(4, controller.getGameManager().getCurrentRound().getPhase());
        choiceMessage = new ActionChoiceEvent(player1.getUsername(), 3);
        choiceMessage.performAction(controller);
        choiceMessage = new WeaponChoiceEvent(player1.getUsername(), electroscythe.getName());
        choiceMessage.performAction(controller);
        choiceMessage = new WeaponEffectChioceEvent(player1.getUsername(), 1);
        choiceMessage.performAction(controller);
        Assert.assertEquals(6, controller.getGameManager().getCurrentRound().getPhase());
        choiceMessage = new SkipActionChoiceEvent(player1.getUsername());
        choiceMessage.performAction(controller);
        Assert.assertEquals(7, controller.getGameManager().getCurrentRound().getPhase());
        Event requestMessage = hashMap.get(player2.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((EndRoundPowerUpRequestEvent)requestMessage).getMaxUsablePowerUps());
        Assert.assertEquals(tagbackGrenade1.getName(), ((EndRoundPowerUpRequestEvent)requestMessage).getPowerUpNames()[0]);
        Assert.assertEquals(tagbackGrenade1.getColour(), ((EndRoundPowerUpRequestEvent)requestMessage).getPowerUpColours()[0]);
        choiceMessage = new EndRoundPowerUpChoiceEvent(player2.getUsername(), new String[]{tagbackGrenade1.getName()}, new CubeColour[]{tagbackGrenade1.getColour()});
        choiceMessage.performAction(controller);
        Assert.assertEquals(1, player1.getPlayerBoard().getMarks().size());
        Assert.assertEquals(player2, player1.getPlayerBoard().getMarks().get(0).getPlayer());
        Assert.assertEquals(0, player2.getPowerUps().size());
        Assert.assertEquals(7, controller.getGameManager().getCurrentRound().getPhase());
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((EndRoundPowerUpRequestEvent)requestMessage).getMaxUsablePowerUps());
        Assert.assertEquals(tagbackGrenade2.getName(), ((EndRoundPowerUpRequestEvent)requestMessage).getPowerUpNames()[0]);
        Assert.assertEquals(tagbackGrenade2.getColour(), ((EndRoundPowerUpRequestEvent)requestMessage).getPowerUpColours()[0]);
        choiceMessage = new EndRoundPowerUpChoiceEvent(player3.getUsername(), new String[]{tagbackGrenade2.getName()}, new CubeColour[]{tagbackGrenade2.getColour()});
        choiceMessage.performAction(controller);
        Assert.assertEquals(2, player1.getPlayerBoard().getMarks().size());
        Assert.assertEquals(player3, player1.getPlayerBoard().getMarks().get(1).getPlayer());
        Assert.assertEquals(0, player3.getPowerUps().size());
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
        Assert.assertEquals(player2, controller.getGameManager().getCurrentRound().getCurrentPlayer());
    }
}
