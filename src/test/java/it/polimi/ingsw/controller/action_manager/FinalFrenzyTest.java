package it.polimi.ingsw.controller.action_manager;

import it.polimi.ingsw.TestPattern;
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
import it.polimi.ingsw.model.game_components.cards.weapons.Electroscythe;
import it.polimi.ingsw.model.game_components.cards.weapons.LockRifle;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class FinalFrenzyTest {
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
        controller.getGameManager().setCurrentRound(new RoundManager(controller, player3));
        while (controller.getGameManager().getModel().getGameboard().getGameTrack().getSkullBox() > 0)
            controller.getGameManager().getModel().getGameboard().getGameTrack().removeSkull();
        player1.setPosition(map[0][0]);
        SetUpObserverObservable.connect(controller.getGameManager().getModel().getPlayers(), controller.getUsersVirtualView(), controller.getGameManager().getModel());
    }

    @Test
    public void finalFrenzyUsableActionTest(){
        controller.getGameManager().setPlayerTurn(1);
        player1.setPosition(map[0][0]);
        player2.setPosition(map[0][0]);
        player3.setPosition(map[0][0]);
        Weapon lockRifle = new LockRifle();
        lockRifle.setUnloaded();
        Weapon electroscythe = new Electroscythe();
        player3.addWeapon(lockRifle);
        player1.addWeapon(electroscythe);
        PowerUp tagbackGrenade = new TagbackGrenade(CubeColour.Blue);
        player3.addPowerUp(tagbackGrenade);
        controller.getGameManager().newRound();
        roundManager = controller.getGameManager().getCurrentRound();

        Assert.assertEquals(3, controller.getGameManager().getModel().getPlayers().size());
        Assert.assertEquals(player3, roundManager.getCurrentPlayer());
        Assert.assertFalse(controller.getGameManager().isFirsPlayerPlayed());
        Assert.assertTrue(controller.getGameManager().isFinalFrenzyPhase());
        Event requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[1]);
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[2]);

        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player3.getUsername(),3);
        choiceMessage.performAction(controller);
        //ricevi richiesta movimento
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(3, ((ShotMoveRequestEvent)requestMessage).getPossibleSquareX().length);
        TestPattern.checkSquares(new int[]{0,0,1}, new int[]{0,1,0}, ((ShotMoveRequestEvent)requestMessage).getPossibleSquareX(), ((ShotMoveRequestEvent)requestMessage).getPossibleSquareY());
        choiceMessage = new ShotMoveChoiceEvent(player3.getUsername(), 0,1);
        choiceMessage.performAction(controller);
        Assert.assertEquals(map[0][0], player1.getPosition());
        Assert.assertEquals(map[0][0], player2.getPosition());
        Assert.assertEquals(map[0][1], player3.getPosition());
        //ricevi richiesta ricarica
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponReloadRequestEvent)requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponReloadRequestEvent)requestMessage).getWeapons().contains(lockRifle.getName()));
        choiceMessage = new WeaponReloadChoiceEvent(player3.getUsername(), lockRifle.getName());
        choiceMessage.performAction(controller);
        //ricevi richiesta pagamento
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertArrayEquals(new int[]{0,0,1}, ((WeaponReloadPaymentRequestEvent)requestMessage).getMinimumPowerUpRequest());
        choiceMessage = new WeaponReloadPaymentChoiceEvent(player3.getUsername(), new String[]{tagbackGrenade.getName()}, new CubeColour[]{CubeColour.Blue});
        choiceMessage.performAction(controller);
        Assert.assertTrue(lockRifle.isLoaded());
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
        //ricevi richiesta arma
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponRequestEvent)requestMessage).getWeapons().size());

    }
}
