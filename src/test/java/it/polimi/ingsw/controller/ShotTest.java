package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.ActionRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controller_view_event.WeaponEffectRequest;
import it.polimi.ingsw.event.controller_view_event.WeaponRequestEvent;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.weapons.LockRifle;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ShotTest {
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
    public void fireTest() {
        player1.setPosition(map[0][0]);
        player2.setPosition(map[0][1]);
        player3.setPosition(map[1][0]);
        Weapon lockRifle = new LockRifle();
        player1.addWeapon(lockRifle);
        roundManager.manageRound();

        // richiesta azione
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        for (int i = 0; i < 3; i++) {
            Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[i]);
        }
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 3);
        choiceMessage.performAction(controller);
        //richiesta arma
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponRequestEvent) requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponRequestEvent) requestMessage).getWeapons().contains(lockRifle.getName()));
        choiceMessage = new WeaponChoiceEvent(player1.getUsername(), lockRifle.getName());
        choiceMessage.performAction(controller);
        //richiesta effetto
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((WeaponEffectRequest)requestMessage).getAvailableEffect()[0]);
        Assert.assertFalse(((WeaponEffectRequest)requestMessage).getAvailableEffect()[1]);
        Assert.assertFalse(((WeaponEffectRequest)requestMessage).getAvailableEffect()[2]);
        choiceMessage = new WeaponEffectChioceEvent(player1.getUsername(), 1);
        choiceMessage.performAction(controller);
        //richiesta target effetto
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(2, ((TargetPlayerRequestEvent)requestMessage).getPossibleTargets().size());
        Assert.assertTrue(((TargetPlayerRequestEvent)requestMessage).getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertTrue(((TargetPlayerRequestEvent)requestMessage).getPossibleTargets().contains(player3.getCharacter()));
        ArrayList<Character> target = new ArrayList<>();
        target.add(player2.getCharacter());
        choiceMessage = new WeaponPlayersTargetChoiceEvent(player1.getUsername(), target);
        choiceMessage.performAction(controller);
        //richiesta effetto
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertFalse(((WeaponEffectRequest)requestMessage).getAvailableEffect()[0]);
        Assert.assertTrue(((WeaponEffectRequest)requestMessage).getAvailableEffect()[1]);
        Assert.assertFalse(((WeaponEffectRequest)requestMessage).getAvailableEffect()[2]);
        choiceMessage = new WeaponEffectChioceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        // richiesta target effetto
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((TargetPlayerRequestEvent)requestMessage).getPossibleTargets().size());
        Assert.assertTrue(((TargetPlayerRequestEvent)requestMessage).getPossibleTargets().contains(player3.getCharacter()));
        target = new ArrayList<>();
        target.add(player3.getCharacter());
        choiceMessage = new WeaponPlayersTargetChoiceEvent(player1.getUsername(), target);
        choiceMessage.performAction(controller);
        // fine azione
        Assert.assertEquals(0, player1.getCubeColourNumber(CubeColour.Red));
        Assert.assertEquals(4, roundManager.getPhase());
        Assert.assertFalse(lockRifle.isLoaded());
    }
}
