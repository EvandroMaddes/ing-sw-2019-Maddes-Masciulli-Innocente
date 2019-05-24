package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
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
    private Player player1;
    private Player player2;
    private Player player3;
    private Map map;
    private Square square1,square2;
    private Square[][] testedMapMatrix;
    ArrayList<Player> target;

    @Before
    public void setup() {
        weaponDeck = new WeaponDeck();
        player1 = new Player("Evandro", Character.BANSHEE);
        player2 = new Player("Francesco",Character.D_STRUCT_OR);
        player3 = new Player("Federico",Character.DOZER);
        map = new Map("leftFirst","rightFirst");
        testedMapMatrix = map.getSquareMatrix();
        square1 = testedMapMatrix[0][0];
        square2 = testedMapMatrix[0][1];
        player1.setPosition(square1);
        player2.setPosition(square1);
        player3.setPosition(square2);
        square1.addCurrentPlayer(player1);
        square1.addCurrentPlayer(player2);
        square2.addCurrentPlayer(player3);
        target = new ArrayList<Player>();




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
