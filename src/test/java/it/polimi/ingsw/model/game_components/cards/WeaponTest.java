package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.weapons.*;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class WeaponTest {
    private WeaponDeck weaponDeck;
    private Weapon currentWeapon;
    private AmmoCube[] currentReloadCost;
    private ArrayList<Character> currentTargets;
    private Player player1;
    private Player player2;
    private Player player3;
    private Map map;
    private Square square1,square2, square3;
    private Square[][] testedMapMatrix;
    ArrayList<Player> target;

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
        map = new Map("leftFirst","rightFirst");
        testedMapMatrix = map.getSquareMatrix();
        square1 = testedMapMatrix[0][1];
        square2 = testedMapMatrix[1][1];
        square3 = testedMapMatrix[2][2];
        player1.setPosition(square1);
        player2.setPosition(square2);
        player3.setPosition(square3);
        square1.addCurrentPlayer(player1);
        square2.addCurrentPlayer(player2);
        square3.addCurrentPlayer(player3);
        target = new ArrayList<>();


    }


    //public OneOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost)
    @Test
    public void testLockRifle(){

        currentReloadCost = new AmmoCube[] { new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue)};
        currentWeapon = new LockRifle(CubeColour.Blue,"LockRifle",currentReloadCost,new AmmoCube[] {new AmmoCube(CubeColour.Red)});
        currentWeapon.setOwner(player1);
        Assert.assertTrue(currentWeapon.isUsable());
        Event targetEvent = currentWeapon.getTargetEffect(1);
        Assert.assertTrue(currentWeapon.isUsableEffect(1));
        Assert.assertFalse(currentWeapon.isUsableEffect(2));
        //Testing first effect:
        currentTargets = ((TargetPlayerRequestEvent)targetEvent).getPossibleTargets();
        Assert.assertTrue(currentTargets.contains(player2.getCharacter()));
        Assert.assertFalse(currentTargets.contains(player3.getCharacter()));
        ArrayList<Object> testTargets = new ArrayList<>();
        testTargets.add(player2);
        currentWeapon.performEffect(1, testTargets);
        Assert.assertEquals(player1, player2.getPlayerBoard().getDamageReceived()[0].getPlayer());
        Assert.assertEquals(1, player2.getPlayerBoard().checkNumberOfMarks(player1));
        //todo perchè è false?
        Assert.assertFalse(currentWeapon.isUsableEffect(2));
        targetEvent = currentWeapon.getTargetEffect(2);
        Assert.assertTrue(((TargetPlayerRequestEvent)targetEvent).getPossibleTargets().contains(player2.getCharacter()));
        Assert.assertFalse(((TargetPlayerRequestEvent)targetEvent).getPossibleTargets().contains(player3.getCharacter()));

        testTargets.clear();
        testTargets.add(player1);
        currentWeapon.performEffect(2, testTargets);
        Assert.assertEquals(1, player1.getPlayerBoard().checkNumberOfMarks(player1));


        //todo posizione dei quadrati non giusta, player2 non vede player3
    /*    currentReloadCost = new AmmoCube[] { new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue)};
        currentWeapon = new LockRifle(CubeColour.Blue,"LockRifle",currentReloadCost,new AmmoCube[] {new AmmoCube(CubeColour.Red)});
        currentWeapon.setOwner(player2);
        Assert.assertTrue(currentWeapon.isUsable());
        Event targetEvent = currentWeapon.getTargetEffect(1);
        Assert.assertTrue(currentWeapon.isUsableEffect(1));
        Assert.assertFalse(currentWeapon.isUsableEffect(2));
        //Testing first effect:
        currentTargets = ((TargetPlayerRequestEvent)targetEvent).getPossibleTargets();
        Assert.assertTrue(currentTargets.contains(player1.getCharacter()));
        Assert.assertTrue(currentTargets.contains(player3.getCharacter()));
        ArrayList<Object> testTargets = new ArrayList<>();
        testTargets.add(player1);
        currentWeapon.performEffect(1, testTargets);
        Assert.assertEquals(player2, player1.getPlayerBoard().getDamageReceived()[0].getPlayer());
        Assert.assertEquals(1, player1.getPlayerBoard().checkNumberOfMarks(player2));

        Assert.assertFalse(currentWeapon.isUsableEffect(2));
        targetEvent = currentWeapon.getTargetEffect(2);
        Assert.assertFalse(((TargetPlayerRequestEvent)targetEvent).getPossibleTargets().contains(player1.getCharacter()));
        Assert.assertTrue(((TargetPlayerRequestEvent)targetEvent).getPossibleTargets().contains(player3.getCharacter()));
        */


        System.out.println("Tested LockRifle! ︻┳═一- - - ");
    }



    /**
     * @author evandro Maddes
     * checks the metods of alternative weapon
     */
   /*
    @Test
    public void alternativeWeaponTest(){
        for (Object weapon: weaponDeck.getDeck()
        ) {
            if (((Weapon)weapon).getName().equals("ELECTROSCYTHE"))
                currentWeapon = (Electroscythe)weapon;
        }
        ((Electroscythe)currentWeapon).setOwner(player1);


         assertTrue(currentWeapon.isLoaded());
         target.addAll(currentWeapon.getTargets(1));
         assertTrue(player1==currentWeapon.getOwner());
         assertTrue(player2 == target.get(0));
         target.remove(player2);
         target.addAll(((Electroscythe)currentWeapon).getTargets(2));
         assertTrue(player2 == target.get(0));

        ((Electroscythe)currentWeapon).fire(target,square1,1);
        ((Electroscythe)currentWeapon).fire(target,square1,2);

        assertEquals(1, target.size());

    }
*/
    /**
     * @author evandro Maddes
     * checks the metods of two optional weapon
     */
/*    @Test
    public void twoOptionalWeaponTest(){
        for (Object weapon: weaponDeck.getDeck()
        ) {
            if (((Weapon)weapon).getName().equals("CYBERBLADE"))
                currentWeapon = (CyberBlade)weapon;
        }
        ((CyberBlade)currentWeapon).setOwner(player1);
        assertTrue(currentWeapon.isLoaded());
        target.addAll(currentWeapon.getTargets(1));
        assertTrue(player1==currentWeapon.getOwner());
        assertTrue(player2 == target.get(0));
        target.remove(player2);

        target.addAll(((CyberBlade)currentWeapon).getTargets(3));
        assertTrue(player2 == target.get(0));

        ((CyberBlade)currentWeapon).fire(target,square1,1);
        ((CyberBlade)currentWeapon).fire(target,square1,2);
        ((CyberBlade)currentWeapon).fire(target,square1,3);




        assertEquals(1, target.size());

    }
*/
    /**
     * @author evandro Maddes
     * checks the metods of one optional weapon
     */
/*    @Test
    public void oneOptionalWeaponTest(){

        for (Object weapon: weaponDeck.getDeck()
        ) {
            if (((Weapon)weapon).getName().equals("GRENADA LAUNCHER"))
                currentWeapon = (GrenadaLauncher)weapon;
        }
        ((GrenadaLauncher)currentWeapon).setOwner(player1);

        assertTrue(currentWeapon.isLoaded());
        target.addAll(currentWeapon.getTargets(1));
        assertTrue(player1==currentWeapon.getOwner());
        assertTrue( target.contains(player2));
        assertTrue( target.contains(player3));
        target.remove(player2);
        target.remove(player3);

        target.addAll(currentWeapon.getTargets(2));
        assertTrue(player1==currentWeapon.getOwner());
        assertTrue( target.contains(player2));
        assertTrue( target.contains(player3));

        ((GrenadaLauncher)currentWeapon).fire(target,square1,1);
        ((GrenadaLauncher)currentWeapon).fire(target,square1,2);


    }
*/
/*    @Test
    public void onlyOneEffectWeaponTest(){
        for (Object weapon: weaponDeck.getDeck()
        ) {
            if (((Weapon)weapon).getName().equals("WHISPER"))
                currentWeapon = (Whisper)weapon;
        }
        ((Whisper)currentWeapon).setOwner(player1);
        assertTrue(currentWeapon.isLoaded());
        target.addAll(currentWeapon.getTargets(1));
        assertTrue(player1==currentWeapon.getOwner());
        target.add(player3);

        ((Whisper)currentWeapon).fire(target,square2,1);

    }
*/

}
