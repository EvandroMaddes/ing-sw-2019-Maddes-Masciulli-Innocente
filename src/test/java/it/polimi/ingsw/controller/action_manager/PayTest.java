package it.polimi.ingsw.controller.action_manager;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.RoundManager;
import it.polimi.ingsw.controller.SetUpObserverObservable;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.*;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.power_ups.TagbackGrenade;
import it.polimi.ingsw.model.game_components.cards.weapons.Flamethrower;
import it.polimi.ingsw.model.game_components.cards.weapons.PlasmaGun;
import it.polimi.ingsw.model.game_components.cards.weapons.Railgun;
import it.polimi.ingsw.model.game_components.cards.weapons.Whisper;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class PayTest {
    private HashMap<String, VirtualView> hashMap;
    private Controller controller;
    private Player player1;
    private Player player2;
    private Player player3;
    private Square[][] map;
    private RoundManager roundManager;

    @Before
    public void setUp(){
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
    public void payEffectWithPowerUp(){
        Weapon plasmaGun = new PlasmaGun();
        PowerUp tagbackGrenade = new TagbackGrenade(CubeColour.Blue);
        player1.addWeapon(plasmaGun);
        player1.addWeapon(new Whisper());
        player1.setPosition(map[0][0]);
        player2.setPosition(map[1][0]);
        player1.addPowerUp(tagbackGrenade);
        roundManager.manageRound();
        //richiesta azione
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[2]);
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 3);
        choiceMessage.performAction(controller);
        //richiesta arma
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponRequestEvent)requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponRequestEvent)requestMessage).getWeapons().contains(plasmaGun.getName()));
        choiceMessage = new WeaponChoiceEvent(player1.getUsername(), plasmaGun.getName());
        choiceMessage.performAction(controller);
        //richiesta effetto
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((WeaponEffectRequest)requestMessage).getAvailableEffect()[0]);
        Assert.assertTrue(((WeaponEffectRequest)requestMessage).getAvailableEffect()[1]);
        Assert.assertFalse(((WeaponEffectRequest)requestMessage).getAvailableEffect()[2]);
        choiceMessage = new WeaponEffectChioceEvent(player1.getUsername(), 1);
        choiceMessage.performAction(controller);
        // richiesta target
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((TargetPlayerRequestEvent)requestMessage).getPossibleTargets().size());
        Assert.assertTrue(((TargetPlayerRequestEvent)requestMessage).getPossibleTargets().contains(player2.getCharacter()));
        ArrayList<Character> target = new ArrayList<>();
        target.add(player2.getCharacter());
        choiceMessage = new WeaponPlayersTargetChoiceEvent(player1.getUsername(), target);
        choiceMessage.performAction(controller);
        //richiesta effetto
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertFalse(((WeaponEffectRequest)requestMessage).getAvailableEffect()[0]);
        Assert.assertTrue(((WeaponEffectRequest)requestMessage).getAvailableEffect()[1]);
        Assert.assertTrue(((WeaponEffectRequest)requestMessage).getAvailableEffect()[2]);
        choiceMessage = new WeaponEffectChioceEvent(player1.getUsername(), 3);
        choiceMessage.performAction(controller);
        //richiesta pagamento
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertArrayEquals(new int[]{0,0,0}, ((EffectPaymentRequestEvent)requestMessage).getMinimumPowerUpRequest());
        Assert.assertArrayEquals(new int[]{0,0,1}, ((EffectPaymentRequestEvent)requestMessage).getMaximumPowerUpRequest());
        Assert.assertEquals(1, ((EffectPaymentRequestEvent)requestMessage).getPowerUpColours().length);
        Assert.assertEquals(CubeColour.Blue, ((EffectPaymentRequestEvent)requestMessage).getPowerUpColours()[0]);
        Assert.assertEquals(1, ((EffectPaymentRequestEvent)requestMessage).getPowerUpNames().length);
        Assert.assertEquals(tagbackGrenade.getName(), ((EffectPaymentRequestEvent)requestMessage).getPowerUpNames()[0]);
        choiceMessage = new WeaponEffectPaymentChoiceEvent(player1.getUsername(), new String[]{tagbackGrenade.getName()}, new CubeColour[]{CubeColour.Blue});
        choiceMessage.performAction(controller);

        Assert.assertEquals(0, player1.getPowerUps().size());
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Red));
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(3, player2.getPlayerBoard().getDamageAmount());
        Assert.assertTrue(plasmaGun.isLoaded());
    }

    @Test
    public void reloadTest(){
        Weapon railgun = new Railgun();
        Weapon flamethrower = new Flamethrower();
        player1.addWeapon(flamethrower);
        player1.addWeapon(railgun);
        railgun.setUnloaded();
        flamethrower.setUnloaded();
        PowerUp tagbackGrenade = new TagbackGrenade(CubeColour.Yellow);
        player1.addPowerUp(tagbackGrenade);
        player1.setPosition(map[0][0]);
        player1.discardAmmo(new AmmoCube(CubeColour.Red));

        roundManager.manageRound();
        ViewControllerEvent choiceMessage = new SkipActionChoiceEvent(player1.getUsername());
        choiceMessage.performAction(controller);
        choiceMessage.performAction(controller);
        Assert.assertEquals(6, roundManager.getPhase());
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponReloadRequestEvent)requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponReloadRequestEvent)requestMessage).getWeapons().contains(railgun.getName()));

        choiceMessage = new WeaponReloadChoiceEvent(player1.getUsername(), railgun.getName());
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertArrayEquals(new int[]{0,1,0}, ((WeaponReloadPaymentRequestEvent)requestMessage).getMinimumPowerUpRequest());
        Assert.assertArrayEquals(new int[]{0,2,1}, ((WeaponReloadPaymentRequestEvent)requestMessage).getMaximumPowerUpRequest());
        Assert.assertEquals(1, ((WeaponReloadPaymentRequestEvent)requestMessage).getPowerUpColours().length);
        Assert.assertEquals(CubeColour.Yellow, ((WeaponReloadPaymentRequestEvent)requestMessage).getPowerUpColours()[0]);
        Assert.assertEquals(1, ((WeaponReloadPaymentRequestEvent)requestMessage).getPowerUpNames().length);
        Assert.assertEquals(tagbackGrenade.getName(), ((WeaponReloadPaymentRequestEvent)requestMessage).getPowerUpNames()[0]);

        choiceMessage = new WeaponReloadPaymentChoiceEvent(player1.getUsername(), new String[]{tagbackGrenade.getName()}, new CubeColour[]{CubeColour.Yellow});
        choiceMessage.performAction(controller);

        Assert.assertTrue(railgun.isLoaded());
        Assert.assertEquals(0, player1.getPowerUps().size());
        Assert.assertEquals(0, player1.getCubeColourNumber(CubeColour.Red));
        Assert.assertEquals(0, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(0, player1.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
        Assert.assertEquals(player2, controller.getGameManager().getCurrentRound().getCurrentPlayer());
    }
}
