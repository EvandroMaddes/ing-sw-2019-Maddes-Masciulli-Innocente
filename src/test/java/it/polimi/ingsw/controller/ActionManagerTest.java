package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.*;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.weapons.Flamethrower;
import it.polimi.ingsw.model.game_components.cards.weapons.LockRifle;
import it.polimi.ingsw.model.game_components.cards.weapons.Whisper;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class ActionManagerTest {
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
    public void moveTest(){
        roundManager.manageRound();
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[1]);
        Assert.assertFalse(((ActionRequestEvent)requestMessage).getUsableActions()[2]);

        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 1);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(9, ((PositionMoveRequestEvent)requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0,0,0,0,1,1,1,2,2};
        int[] expectedY = new int[]{0,1,2,3,0,1,2,0,1};
        for (int i = 0; i < 9; i++) {
            boolean check = false;
            for (int j = 0; j < 9; j++) {
                if (expectedX[i] == ((PositionMoveRequestEvent)requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionMoveRequestEvent)requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        choiceMessage = new MoveChoiceEvent(player1.getUsername(), 2, 0);
        choiceMessage.performAction(controller);
        Assert.assertEquals(map[2][0], player1.getPosition());
        Assert.assertEquals(4, roundManager.getPhase());
    }

    @Test
    public void grabAmmoTest(){
        AmmoTile ammoTile = new AmmoTile(new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Red), false);
        ((BasicSquare)map[0][0]).replaceAmmoTile(ammoTile);
        ((BasicSquare)map[0][1]).replaceAmmoTile(ammoTile);
        ((BasicSquare)map[0][3]).replaceAmmoTile(ammoTile);
        ((BasicSquare)map[1][1]).replaceAmmoTile(ammoTile);
        ((SpawnSquare)map[1][0]).getWeapons().clear();
        roundManager.manageRound();
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(2, ((PositionGrabRequestEvent)requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0,0};
        int[] expectedY = new int[]{0,1};
        for (int i = 0; i < 2; i++) {
            boolean check = false;
            for (int j = 0; j < 2; j++) {
                if (expectedX[i] == ((PositionGrabRequestEvent)requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionGrabRequestEvent)requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 0,0);
        choiceMessage.performAction(controller);
        Assert.assertEquals(2, player1.getCubeColourNumber(CubeColour.Red));
        Assert.assertEquals(2, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(2, player1.getCubeColourNumber(CubeColour.Yellow));

        choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((PositionGrabRequestEvent)requestMessage).getPossibleSquareX().length);
        expectedX = new int[]{0};
        expectedY = new int[]{1};
        for (int i = 0; i < 1; i++) {
            boolean check = false;
            for (int j = 0; j < 1; j++) {
                if (expectedX[i] == ((PositionGrabRequestEvent)requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionGrabRequestEvent)requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 0,1);
        choiceMessage.performAction(controller);
        Assert.assertEquals(3, player1.getCubeColourNumber(CubeColour.Red));
        Assert.assertEquals(3, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(3, player1.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(map[0][1], player1.getPosition());
    }

    @Test
    public void grabWeaponTest(){
        AmmoTile ammoTile = new AmmoTile(new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Red), false);
        Weapon lockRifle = new LockRifle();
        Weapon flameThrower = new Flamethrower();
        Weapon whisper = new Whisper();
        ((BasicSquare)map[0][0]).replaceAmmoTile(ammoTile);
        ((BasicSquare)map[0][1]).replaceAmmoTile(ammoTile);
        ((BasicSquare)map[0][3]).replaceAmmoTile(ammoTile);
        ((BasicSquare)map[1][1]).replaceAmmoTile(ammoTile);
        ((SpawnSquare)map[1][0]).getWeapons().add(lockRifle);
        ((SpawnSquare)map[1][0]).getWeapons().add(flameThrower);
        ((SpawnSquare)map[1][0]).getWeapons().add(whisper);

        roundManager.manageRound();
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(3, ((PositionGrabRequestEvent)requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0,0,1};
        int[] expectedY = new int[]{0,1,0};
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((PositionGrabRequestEvent)requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionGrabRequestEvent)requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 1,0);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(3, ((WeaponGrabRequestEvent)requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponGrabRequestEvent)requestMessage).getWeapons().contains(lockRifle.getName()));
        Assert.assertTrue(((WeaponGrabRequestEvent)requestMessage).getWeapons().contains(flameThrower.getName()));
        Assert.assertTrue(((WeaponGrabRequestEvent)requestMessage).getWeapons().contains(whisper.getName()));

        choiceMessage = new WeaponGrabChoiceEvent(player1.getUsername(), lockRifle.getName());
        choiceMessage.performAction(controller);

        Assert.assertEquals(1, player1.getNumberOfWeapons());
        Assert.assertEquals(lockRifle, player1.getWeapons()[0]);
        Assert.assertEquals(0, player1.getCubeColourNumber(CubeColour.Blue));

        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[1]);
        Assert.assertFalse(((ActionRequestEvent)requestMessage).getUsableActions()[2]);

        choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(2, ((PositionGrabRequestEvent)requestMessage).getPossibleSquareX().length);
        expectedX = new int[]{1,0};
        expectedY = new int[]{0,0};
        for (int i = 0; i < 1; i++) {
            boolean check = false;
            for (int j = 0; j < 1; j++) {
                if (expectedX[i] == ((PositionGrabRequestEvent)requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionGrabRequestEvent)requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 1,0);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponGrabRequestEvent)requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponGrabRequestEvent)requestMessage).getWeapons().contains(flameThrower.getName()));

        choiceMessage = new WeaponGrabChoiceEvent(player1.getUsername(), flameThrower.getName());
        choiceMessage.performAction(controller);

        Assert.assertEquals(2, player1.getNumberOfWeapons());
        Assert.assertEquals(lockRifle, player1.getWeapons()[0]);
        Assert.assertEquals(flameThrower, player1.getWeapons()[1]);
        Assert.assertEquals(0, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Red));
    }

}
