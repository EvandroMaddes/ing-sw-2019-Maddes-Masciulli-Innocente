package it.polimi.ingsw.controller.action_manager;

import it.polimi.ingsw.TestPattern;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.RoundManager;
import it.polimi.ingsw.controller.SetUpObserverObservable;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controllerviewevent.*;
import it.polimi.ingsw.event.viewcontrollerevent.*;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoTile;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.gamecomponents.cards.powerups.TagbackGrenade;
import it.polimi.ingsw.model.gamecomponents.cards.powerups.TargetingScope;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.*;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class GrabTest {
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

    /**
     * Check that the right squares are given to a player as a choice for a grab action.
     * Check also that the grab af an ammo tile is correclty performed
     */
    @Test
    public void grabAmmoTest() {
        AmmoTile ammoTile = new AmmoTile(new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Red), false);
        ((BasicSquare) map[0][0]).replaceAmmoTile(ammoTile);
        ((BasicSquare) map[0][1]).replaceAmmoTile(ammoTile);
        ((BasicSquare) map[0][3]).replaceAmmoTile(ammoTile);
        ((BasicSquare) map[1][1]).replaceAmmoTile(ammoTile);
        ((SpawnSquare) map[1][0]).getWeapons().clear();
        roundManager.manageRound();
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(2, ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0, 0};
        int[] expectedY = new int[]{0, 1};
        for (int i = 0; i < 2; i++) {
            boolean check = false;
            for (int j = 0; j < 2; j++) {
                if (expectedX[i] == ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionGrabRequestEvent) requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 0, 0);
        choiceMessage.performAction(controller);
        Assert.assertEquals(2, player1.getCubeColourNumber(CubeColour.Red));
        Assert.assertEquals(2, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(2, player1.getCubeColourNumber(CubeColour.Yellow));

        choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX().length);
        expectedX = new int[]{0};
        expectedY = new int[]{1};
        for (int i = 0; i < 1; i++) {
            boolean check = false;
            for (int j = 0; j < 1; j++) {
                if (expectedX[i] == ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionGrabRequestEvent) requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 0, 1);
        choiceMessage.performAction(controller);
        Assert.assertEquals(3, player1.getCubeColourNumber(CubeColour.Red));
        Assert.assertEquals(3, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(3, player1.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(map[0][1], player1.getPosition());
    }

    /**
     * Check that the right squares are given to a player as a choice for a grab action.
     * Check alst that only the weapon that a player can pay are showed for a grab action, and that a weapon grab is correctly performed
     */
    @Test
    public void grabWeaponTest() {
        AmmoTile ammoTile = new AmmoTile(new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Red), false);
        Weapon lockRifle = new LockRifle();
        Weapon flameThrower = new Flamethrower();
        Weapon whisper = new Whisper();
        ((BasicSquare) map[0][0]).replaceAmmoTile(ammoTile);
        ((BasicSquare) map[0][1]).replaceAmmoTile(ammoTile);
        ((BasicSquare) map[0][3]).replaceAmmoTile(ammoTile);
        ((BasicSquare) map[1][1]).replaceAmmoTile(ammoTile);
        ((SpawnSquare) map[1][0]).getWeapons().add(lockRifle);
        ((SpawnSquare) map[1][0]).getWeapons().add(flameThrower);
        ((SpawnSquare) map[1][0]).getWeapons().add(whisper);

        roundManager.manageRound();
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(3, ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0, 0, 1};
        int[] expectedY = new int[]{0, 1, 0};
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedX[i] == ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionGrabRequestEvent) requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 1, 0);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(3, ((WeaponGrabRequestEvent) requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponGrabRequestEvent) requestMessage).getWeapons().contains(lockRifle.getName()));
        Assert.assertTrue(((WeaponGrabRequestEvent) requestMessage).getWeapons().contains(flameThrower.getName()));
        Assert.assertTrue(((WeaponGrabRequestEvent) requestMessage).getWeapons().contains(whisper.getName()));

        choiceMessage = new WeaponGrabChoiceEvent(player1.getUsername(), lockRifle.getName());
        choiceMessage.performAction(controller);

        Assert.assertEquals(1, player1.getNumberOfWeapons());
        Assert.assertEquals(lockRifle, player1.getWeapons()[0]);
        Assert.assertEquals(0, player1.getCubeColourNumber(CubeColour.Blue));

        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[1]);
        Assert.assertFalse(((ActionRequestEvent) requestMessage).getUsableActions()[2]);

        choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(2, ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX().length);
        expectedX = new int[]{1, 0};
        expectedY = new int[]{0, 0};
        for (int i = 0; i < 1; i++) {
            boolean check = false;
            for (int j = 0; j < 1; j++) {
                if (expectedX[i] == ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionGrabRequestEvent) requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 1, 0);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(1, ((WeaponGrabRequestEvent) requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponGrabRequestEvent) requestMessage).getWeapons().contains(flameThrower.getName()));

        choiceMessage = new WeaponGrabChoiceEvent(player1.getUsername(), flameThrower.getName());
        choiceMessage.performAction(controller);

        Assert.assertEquals(2, player1.getNumberOfWeapons());
        Assert.assertEquals(lockRifle, player1.getWeapons()[0]);
        Assert.assertEquals(flameThrower, player1.getWeapons()[1]);
        Assert.assertEquals(0, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Red));
    }

    /**
     * Check that the correct squares are showed as target for a grab in case of adrenalinic grab
     */
    @Test
    public void adrenalinicGrabTest() {
        controller.getGameManager().refillMap();
        ((BasicSquare) map[0][0]).grabAmmoTile(player3);
        player1.getPlayerBoard().addDamages(player2, 4);

        roundManager.manageRound();
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(5, ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0, 0, 1, 1, 2};
        int[] expectedY = new int[]{1, 2, 0, 1, 0};
        TestPattern.checkSquares(expectedX, expectedY, ((PositionGrabRequestEvent) requestMessage).getPossibleSquareX(), ((PositionGrabRequestEvent) requestMessage).getPossibleSquareY());
    }

    /**
     * Check that the grab of a fourth weapon is correcly managed.
     * Weapon is correctly grabbed by the player, that the player have to discard one weapon.
     * Check also that at the end of this operation the inventory of the player is correctly setupped
     */
    @Test
    public void fourthWeaponGrabTest() {
        controller.getGameManager().setPlayerTurn(2);
        player1.setPosition(map[0][0]);
        Weapon lockRifle = new LockRifle();
        player1.addWeapon(lockRifle);
        player1.addWeapon(new MachineGun());
        player1.addWeapon(new PlasmaGun());
        lockRifle.setUnloaded();
        player1.addAmmo(new AmmoCube(CubeColour.Yellow));
        player1.addAmmo(new AmmoCube(CubeColour.Yellow));
        player1.addAmmo(new AmmoCube(CubeColour.Red));
        player1.addAmmo(new AmmoCube(CubeColour.Red));
        player1.addAmmo(new AmmoCube(CubeColour.Blue));
        player1.addAmmo(new AmmoCube(CubeColour.Blue));
        controller.getGameManager().newRound();
        roundManager = controller.getGameManager().getCurrentRound();
        ((SpawnSquare) map[1][0]).getWeapons().clear();
        ((SpawnSquare) map[1][0]).getWeapons().add(new Shotgun());
        ((SpawnSquare) map[1][0]).getWeapons().add(new Zx2());
        ((SpawnSquare) map[1][0]).getWeapons().add(new ShockWave());
        Assert.assertEquals(2, roundManager.getPhase());
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);
        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 1, 0);
        choiceMessage.performAction(controller);
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(3, ((WeaponGrabRequestEvent) requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponGrabRequestEvent) requestMessage).getWeapons().contains("ZX-2"));
        Assert.assertTrue(((WeaponGrabRequestEvent) requestMessage).getWeapons().contains("SHOCKWAVE"));
        Assert.assertTrue(((WeaponGrabRequestEvent) requestMessage).getWeapons().contains("SHOTGUN"));

        choiceMessage = new WeaponGrabChoiceEvent(player1.getUsername(), "SHOTGUN");
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(4, ((WeaponDiscardRequestEvent) requestMessage).getWeapons().size());
        Assert.assertTrue(((WeaponDiscardRequestEvent) requestMessage).getWeapons().contains("SHOTGUN"));
        Assert.assertTrue(((WeaponDiscardRequestEvent) requestMessage).getWeapons().contains("LOCK RIFLE"));
        Assert.assertTrue(((WeaponDiscardRequestEvent) requestMessage).getWeapons().contains("MACHINE GUN"));
        Assert.assertTrue(((WeaponDiscardRequestEvent) requestMessage).getWeapons().contains("PLASMA GUN"));
        choiceMessage = new WeaponDiscardChoiceEvent(player1.getUsername(), lockRifle.getName());
        choiceMessage.performAction(controller);
        Assert.assertEquals(4, controller.getGameManager().getCurrentRound().getPhase());
        Assert.assertEquals(3, player1.getNumberOfWeapons());
        String[] expectedWeapons = new String[]{"SHOTGUN", "MACHINE GUN", "PLASMA GUN"};
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedWeapons[i].equals(player1.getWeapons()[j].getName()))
                    check = true;
            }
            Assert.assertTrue(check);
        }
        expectedWeapons = new String[]{"ZX-2", "SHOCKWAVE", "LOCK RIFLE"};
        Assert.assertEquals(3, ((SpawnSquare) map[1][0]).getWeapons().size());
        for (int i = 0; i < 3; i++) {
            boolean check = false;
            for (int j = 0; j < 3; j++) {
                if (expectedWeapons[i].equals(((SpawnSquare) map[1][0]).getWeapons().get(j).getName()))
                    check = true;
            }
            Assert.assertTrue(check);
        }
        Assert.assertTrue(lockRifle.isLoaded());
        Assert.assertEquals(3, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(2, player1.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(3, player1.getCubeColourNumber(CubeColour.Red));
    }

    /**
     * Check that a weapon can be correcly grabbed with powerUps, and only the usable powerUps are given to the player for the pay choice
     */
    @Test
    public void grabWeaponWithPowerUp() {
        Weapon lockRifle = new LockRifle();
        Weapon flameThrower = new Flamethrower();
        Weapon whisper = new Whisper();
        controller.getGameManager().refillMap();
        ((SpawnSquare) map[1][0]).getWeapons().clear();
        ((SpawnSquare) map[1][0]).getWeapons().add(lockRifle);
        ((SpawnSquare) map[1][0]).getWeapons().add(flameThrower);
        ((SpawnSquare) map[1][0]).getWeapons().add(whisper);
        player1.addPowerUp(new TargetingScope(CubeColour.Blue));
        player1.addPowerUp(new TagbackGrenade(CubeColour.Yellow));
        player1.addPowerUp(new TargetingScope(CubeColour.Red));

        roundManager.manageRound();
        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 2);
        choiceMessage.performAction(controller);

        choiceMessage = new GrabChoiceEvent(player1.getUsername(), 1, 0);
        choiceMessage.performAction(controller);

        choiceMessage = new WeaponGrabChoiceEvent(player1.getUsername(), whisper.getName());
        choiceMessage.performAction(controller);

        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertArrayEquals(new int[]{0, 0, 0}, ((WeaponGrabPaymentRequestEvent) requestMessage).getMinimumPowerUpRequest());
        Assert.assertEquals(2, ((WeaponGrabPaymentRequestEvent) requestMessage).getPowerUpColours().length);
        Assert.assertEquals(2, ((WeaponGrabPaymentRequestEvent) requestMessage).getPowerUpNames().length);
        Assert.assertEquals(CubeColour.Blue, ((WeaponGrabPaymentRequestEvent) requestMessage).getPowerUpColours()[0]);
        Assert.assertEquals(CubeColour.Yellow, ((WeaponGrabPaymentRequestEvent) requestMessage).getPowerUpColours()[1]);
        Assert.assertEquals("TargetingScope", ((WeaponGrabPaymentRequestEvent) requestMessage).getPowerUpNames()[0]);
        Assert.assertEquals("TagbackGrenade", ((WeaponGrabPaymentRequestEvent) requestMessage).getPowerUpNames()[1]);
        choiceMessage = new WeaponGrabPaymentChoiceEvent(player1.getUsername(), new String[]{"TagbackGrenade", "TargetingScope"}, new CubeColour[]{CubeColour.Yellow, CubeColour.Blue});
        choiceMessage.performAction(controller);
        Assert.assertEquals(1, player1.getNumberOfWeapons());
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Blue));
        Assert.assertEquals(1, player1.getCubeColourNumber(CubeColour.Red));
        Assert.assertEquals(whisper, player1.getWeapons()[0]);
        Assert.assertEquals(1, player1.getPowerUps().size());
        Assert.assertEquals(CubeColour.Red, player1.getPowerUps().get(0).getColour());
        Assert.assertEquals(4, controller.getGameManager().getCurrentRound().getPhase());
    }


}
