package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.weapons.*;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
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
    private Square square;
    ArrayList<Player> target;

    @Before
    public void setup() {
        weaponDeck = new WeaponDeck();
        player1 = new Player("Evandro", Character.BANSHEE);
        player2 = new Player("Francesco",Character.D_STRUCT_OR);
        square = new BasicSquare(0,0);
        player1.setPosition(square);
        player2.setPosition(square);
        square.addCurrentPlayer(player1);
        square.addCurrentPlayer(player2);
        target = new ArrayList<Player>();



    }

    /**
     * @author evandro Maddes
     * checks the metods of Electroscythe weapon
     */
    @Test
    public void electroscytheTest(){
        for (Object weapon: weaponDeck.getDeck()
        ) {
            if (((Weapon)weapon).getName().equals("ELECTROSCYTHE"))
                currentWeapon = (Electroscythe)weapon;
        }
        currentWeapon.setOwner(player1);

         assertTrue(currentWeapon.isLoaded());
         target.addAll(currentWeapon.getTargetsBaseEffect());
         assertTrue(player1==currentWeapon.getOwner());
         assertTrue(player2 == target.get(0));
         target.remove(player2);
         target.addAll(((Electroscythe)currentWeapon).getTargetsAlternativeEffect());
         assertTrue(player2 == target.get(0));

        ((Electroscythe)currentWeapon).fireBaseEffect(target,square);
        ((Electroscythe)currentWeapon).fireAlternativeEffect(target,square);
        assertEquals(1, target.size());


    }
}
