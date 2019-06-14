package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.weapons.*;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * This is the Weapons TestClass:
 *  every test method check, for each weapon, the correct targets encoded in the Event
 *  and the execution of every effect.
 */
public class WeaponTest {
    private WeaponDeck weaponDeck;
    private Weapon currentWeapon;
    private AmmoCube[] currentReloadCost;
    private ArrayList<Character> currentTargets;
    private Player player1;
    private Player player2;
    private Player player3;
    private PlayerBoard playerBoard1, playerBoard2, playerBoard3;
    private Map map;
    private Square square1,square2, square3;
    private Square[][] testedMapMatrix;
    ArrayList<Player> target;
    ArrayList<Object> testTargets;

    /**
     * The setUp method:
     * player1 sees player2 but not player3,
     * player2 sees everyone,
     * player3 doesn't see anyone;
     */
    @Before
    public void setUp() {
        weaponDeck = new WeaponDeck();
        player1 = new Player("Evandro", Character.BANSHEE);
        player2 = new Player("Francesco",Character.D_STRUCT_OR);
        player3 = new Player("Federico",Character.DOZER);
        playerBoard1 = player1.getPlayerBoard();
        playerBoard2 = player2.getPlayerBoard();
        playerBoard3 = player3.getPlayerBoard();
        map = new Map("leftFirst","rightFirst");
        testedMapMatrix = map.getSquareMatrix();
        square1 = testedMapMatrix[2][1];
        square2 = testedMapMatrix[1][1];
        square3 = testedMapMatrix[0][2];
        player1.setPosition(square1);
        player2.setPosition(square2);
        player3.setPosition(square3);
        target = new ArrayList<>();
        testTargets = new ArrayList<>();
    }





    /**
     * Test for the MachineGun Class
     */
    @Test
    public void testMachineGun(){
        currentReloadCost = new AmmoCube[] { new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Red)};
        currentWeapon = new MachineGun(
        );
        currentWeapon.setOwner(player2);
        Assert.assertTrue(currentWeapon.isUsable());
        Player tempPlayer = new Player("User", Character.VIOLET);
        tempPlayer.setPosition(player1.getPosition());
        Assert.assertTrue(currentWeapon.isUsableEffect(1));
        Assert.assertFalse(currentWeapon.isUsableEffect(2));
        Assert.assertFalse(currentWeapon.isUsableEffect(3));

        //Testing first effect:
        Event targetEvent = currentWeapon.getTargetEffect(1);
        currentTargets = ((TargetPlayerRequestEvent)targetEvent).getPossibleTargets();
        Assert.assertTrue(currentTargets.contains(player1.getCharacter()));
        Assert.assertTrue(currentTargets.contains(player3.getCharacter()));
        Assert.assertTrue(currentTargets.contains(tempPlayer.getCharacter()));

        testTargets.add(player1);
        testTargets.add(player3);
        testTargets.add(tempPlayer);
        currentWeapon.performEffect(1, testTargets.subList(0,2));
        Assert.assertEquals(player2, playerBoard1.getDamageReceived()[0].getPlayer());
        Assert.assertEquals(player2, playerBoard3.getDamageReceived()[0].getPlayer());
        Assert.assertEquals(1, playerBoard3.getDamageAmount());

        currentWeapon.performEffect(2,testTargets.subList(0,1));
        Assert.assertEquals(player2, playerBoard1.getDamageReceived()[1].getPlayer());

    
        currentWeapon.performEffect(3, testTargets.subList(2,3));
        Assert.assertEquals(player2, tempPlayer.getPlayerBoard().getDamageReceived()[0].getPlayer());
        Assert.assertEquals(player2, playerBoard3.getDamageReceived()[1].getPlayer());

        System.out.println("Tested MachineGun! \t︻┳═一- - - \n");
    }

    /**
     * Test for the TractorBeam Class
     */
    @Test
    public void testTractorBeam(){
        currentReloadCost = new AmmoCube[] { new AmmoCube(CubeColour.Blue)};
        currentWeapon = new TractorBeam(
        );
        currentWeapon.setOwner(player2);
        //trying to move a target that isn't visible at the beginning, according with the Weapon Guide
        player1.setPosition(testedMapMatrix[1][0]);
        Assert.assertTrue(currentWeapon.isUsable());
        Assert.assertTrue(currentWeapon.isUsableEffect(1));
        Assert.assertTrue(currentWeapon.isUsableEffect(2));

        //Testing first effect:
        Event targetEvent = currentWeapon.getTargetEffect(1);
        currentTargets = ((TargetPlayerRequestEvent)targetEvent).getPossibleTargets();

        Assert.assertTrue(currentTargets.contains(player1.getCharacter()));
        Assert.assertTrue(currentTargets.contains(player3.getCharacter()));

        testTargets.add(player1);
        testTargets.add(testedMapMatrix[0][1]);
        currentWeapon.performEffect(1, testTargets.subList(0,1));
        currentWeapon.performEffect(1, testTargets.subList(1,2));
        Assert.assertEquals(player2, playerBoard1.getDamageReceived()[0].getPlayer());
        Assert.assertEquals(testedMapMatrix[0][1], player1.getPosition());
        Assert.assertEquals(1, playerBoard1.getDamageAmount());

        //Testing alternative effect:
        player1.getPosition().removeCurrentPlayer(player1);
        player1.setPosition(testedMapMatrix[1][0]);

        testTargets.clear();
        targetEvent = currentWeapon.getTargetEffect(2);
        currentWeapon.setLoaded();
        currentTargets = ((TargetPlayerRequestEvent)targetEvent).getPossibleTargets();
        Assert.assertFalse(currentTargets.contains(player1.getCharacter()));
        Assert.assertTrue(currentTargets.contains(player3.getCharacter()));
        testTargets.add(player3);

        currentWeapon.performEffect(2, testTargets.subList(0,1));
        Assert.assertEquals(player2, playerBoard3.getDamageReceived()[0].getPlayer());
        Assert.assertEquals(3,playerBoard3.getDamageAmount());

        System.out.println("Tested TractorBeam! \t︻┳═一- - - \n");
    }

    /**
     * Test for the Thor Class
     */
    @Test
    public void testTHOR(){
        currentReloadCost = new AmmoCube[] { new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Red)};
        currentWeapon = new Thor(
        );
        currentWeapon.setOwner(player1);
        //Creating another player that is seen just by player 3
        Player tempPlayer = new Player("User", Character.SPROG);
        tempPlayer.setPosition(testedMapMatrix[0][3]);

        Assert.assertTrue(currentWeapon.isUsableEffect(1));
        Assert.assertFalse(currentWeapon.isUsableEffect(2));
        Assert.assertFalse(currentWeapon.isUsableEffect(3));

        //Testing first effect:
        Event targetEvent = currentWeapon.getTargetEffect(1);
        currentTargets = ((TargetPlayerRequestEvent)targetEvent).getPossibleTargets();

        Assert.assertTrue(currentTargets.contains(player2.getCharacter()));
        Assert.assertFalse(currentTargets.contains(player3.getCharacter()));
        Assert.assertFalse(currentTargets.contains(tempPlayer.getCharacter()));

        testTargets.add(player2);
        currentWeapon.performEffect(1, testTargets);
        Assert.assertEquals(player1, playerBoard2.getDamageReceived()[1].getPlayer());
        Assert.assertEquals(2, playerBoard2.getDamageAmount());

        //Testing second (additional) effect:
        targetEvent = currentWeapon.getTargetEffect(2);
        currentTargets = ((TargetPlayerRequestEvent)targetEvent).getPossibleTargets();

        Assert.assertTrue(currentTargets.contains(player3.getCharacter()));
        Assert.assertFalse(currentTargets.contains(player2.getCharacter()));
        Assert.assertFalse(currentTargets.contains(tempPlayer.getCharacter()));

        testTargets.clear();
        testTargets.add(player3);
        currentWeapon.performEffect(2, testTargets);
        Assert.assertEquals(player1, playerBoard3.getDamageReceived()[0].getPlayer());
        Assert.assertEquals(1, playerBoard3.getDamageAmount());

        //Testing third (additional) effect:
        targetEvent = currentWeapon.getTargetEffect(3);
        currentTargets = ((TargetPlayerRequestEvent)targetEvent).getPossibleTargets();

        Assert.assertTrue(currentTargets.contains(tempPlayer.getCharacter()));
        Assert.assertFalse(currentTargets.contains(player2.getCharacter()));
        Assert.assertFalse(currentTargets.contains(player3.getCharacter()));

        testTargets.clear();
        testTargets.add(tempPlayer);
        currentWeapon.performEffect(3, testTargets);
        Assert.assertEquals(player1, tempPlayer.getPlayerBoard().getDamageReceived()[1].getPlayer());
        Assert.assertEquals(2, tempPlayer.getPlayerBoard().getDamageAmount());

        System.out.println("Tested THOR! \t︻┳═一- - - \n");
    }
    
}
