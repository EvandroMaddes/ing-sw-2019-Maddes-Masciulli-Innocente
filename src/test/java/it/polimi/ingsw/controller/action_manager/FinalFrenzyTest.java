package it.polimi.ingsw.controller.action_manager;

import it.polimi.ingsw.TestPattern;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.RoundManager;
import it.polimi.ingsw.controller.SetUpObserverObservable;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controllerviewevent.*;
import it.polimi.ingsw.event.viewcontrollerevent.*;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.gamecomponents.cards.powerups.TagbackGrenade;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Electroscythe;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.LockRifle;
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
        controller.getGameManager().setCurrentRound(new RoundManager(controller, player3));
        while (controller.getGameManager().getModel().getGameboard().getGameTrack().getSkullBox() > 0)
            controller.getGameManager().getModel().getGameboard().getGameTrack().removeSkull();
        player1.setPosition(map[0][0]);
        SetUpObserverObservable.connect(controller.getGameManager().getModel().getPlayers(), controller.getUsersVirtualView(), controller.getGameManager().getModel());
    }

    /**
     * Check that a player can correctly use his action in the final frenzy phase
     */
    @Test
    public void finalFrenzyUsableActionTest() {
        controller.getGameManager().setPlayerTurn(1);
        player1.setPosition(map[0][0]);
        player2.setPosition(map[0][0]);
        player3.setPosition(map[0][0]);
        Weapon lockRifle = new LockRifle();
        Weapon electroscythe = new Electroscythe();
        player3.addWeapon(lockRifle);
        lockRifle.setUnloaded();
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
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[1]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[2]);

        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player3.getUsername(), 3);
        choiceMessage.performAction(controller);
        //ricevi richiesta movimento
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(3, ((ShotMoveRequestEvent) requestMessage).getPossibleSquareX().length);
        TestPattern.checkSquares(new int[]{0, 0, 1}, new int[]{0, 1, 0}, ((ShotMoveRequestEvent) requestMessage).getPossibleSquareX(), ((ShotMoveRequestEvent) requestMessage).getPossibleSquareY());
        choiceMessage = new ShotMoveChoiceEvent(player3.getUsername(), 0, 1);
        choiceMessage.performAction(controller);
        Assert.assertEquals(map[0][0], player1.getPosition());
        Assert.assertEquals(map[0][0], player2.getPosition());
        Assert.assertEquals(map[0][1], player3.getPosition());
        //ricevi richiesta ricarica
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponReloadRequestEvent) requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponReloadRequestEvent) requestMessage).getWeapons().contains(lockRifle.getName()));
        choiceMessage = new WeaponReloadChoiceEvent(player3.getUsername(), lockRifle.getName());
        choiceMessage.performAction(controller);
        //ricevi richiesta pagamento
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertArrayEquals(new int[]{0, 0, 1}, ((WeaponReloadPaymentRequestEvent) requestMessage).getMinimumPowerUpRequest());
        choiceMessage = new WeaponReloadPaymentChoiceEvent(player3.getUsername(), new String[]{tagbackGrenade.getName()}, new CubeColour[]{CubeColour.Blue});
        choiceMessage.performAction(controller);
        Assert.assertTrue(lockRifle.isLoaded());
        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
        //ricevi richiesta arma
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponRequestEvent) requestMessage).getWeapons().size());
    }

    /**
     * Check that a player after or as first player cannot perform a move action in the final frenzy phase
     */
    @Test
    public void firstPlayerFinalFrenzyGrabTest() {
        controller.getGameManager().setPlayerTurn(2);
        player1.setPosition(map[0][0]);
        controller.getGameManager().newRound();
        roundManager = controller.getGameManager().getCurrentRound();
        Assert.assertEquals(player1, roundManager.getCurrentPlayer());
        Assert.assertTrue(controller.getGameManager().isFirsPlayerPlayed());
        Assert.assertTrue(controller.getGameManager().isFinalFrenzyPhase());
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertFalse(((ActionRequestEvent) requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[1]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[2]);
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        ((BasicSquare) map[0][1]).grabAmmoTile(player2);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(8, ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0, 0, 1, 1, 2, 0, 1, 2};
        int[] expectedY = new int[]{0, 2, 0, 1, 0, 3, 2, 1};
        TestPattern.checkSquares(expectedX, expectedY, ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX(), ((PositionGrabRequestEvent) requestMessage).getPossibleSquareY());
    }

    /**
     * Check that a player before the first one can correctly perform a move action
     */
    @Test
    public void beforeFirstPlayerFinalFrenzyMoveTest() {
        controller.getGameManager().setPlayerTurn(1);
        player3.setPosition(map[0][0]);
        controller.getGameManager().newRound();
        roundManager = controller.getGameManager().getCurrentRound();
        Assert.assertEquals(player3, roundManager.getCurrentPlayer());
        Assert.assertFalse(controller.getGameManager().isFirsPlayerPlayed());
        Assert.assertTrue(controller.getGameManager().isFinalFrenzyPhase());
        Event requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[1]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[2]);
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player3.getUsername(), 1);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player3.getUsername()).getToRemoteView();
        Assert.assertEquals(11, ((PositionMoveRequestEvent) requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0, 0, 0, 1, 1, 1, 2, 0, 1, 2, 2};
        int[] expectedY = new int[]{0, 1, 2, 0, 1, 3, 0, 3, 2, 1, 2};
        TestPattern.checkSquares(expectedX, expectedY, ((PositionMoveRequestEvent) requestMessage).getPossibleSquareX(), ((PositionMoveRequestEvent) requestMessage).getPossibleSquareY());
        choiceMessage = new MoveChoiceEvent(player3.getUsername(), 2, 2);
        choiceMessage.performAction(controller);
        Assert.assertEquals(map[2][2], player3.getPosition());
        Assert.assertEquals(4, controller.getGameManager().getCurrentRound().getPhase());
    }
}
