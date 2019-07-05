package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.validator.Validator;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.LockRifle;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ValidatorTest {
    private Validator validator;
    private Square[][] map;


    @Before
    public void setUp() {
        validator = new Validator() {
            @Override
            public ArrayList<Square> availableMoves(Controller controller) {
                return null;
            }

            @Override
            public ArrayList<Square> availableGrab(Controller controller) {
                return null;
            }

            @Override
            public boolean[] getUsableActions(Controller controller) {
                return new boolean[0];
            }
        };
        Map gameMap = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
        map = gameMap.getSquareMatrix();
    }

    /**
     * Check that the method reachableInMoves(int ) of a square return the correct squares of the map
     */
    @Test
    public void reachableInMovesTest() {
        ArrayList<Square> possibleSquares = map[0][0].reachableInMoves(2);
        Assert.assertEquals(6, possibleSquares.size());
        ArrayList<Square> expectedSquares = new ArrayList<>();
        expectedSquares.add(map[0][0]);
        expectedSquares.add(map[1][0]);
        expectedSquares.add(map[2][0]);
        expectedSquares.add(map[0][1]);
        expectedSquares.add(map[0][2]);
        expectedSquares.add(map[1][1]);
        for (Square s : expectedSquares) {
            Assert.assertTrue(possibleSquares.contains(s));
        }
    }

    /**
     * Check that the availableToFireWeapons(Player) return the correct weapons of the player
     */
    @Test
    public void availableToFireWeaponsTest() {
        Player player1 = new Player("Federico", Character.SPROG);
        player1.setPosition(map[0][0]);
        ArrayList<Weapon> possibleWeapon;
        ArrayList<Weapon> expectedWeapon = new ArrayList<>();
        possibleWeapon = Validator.availableToFireWeapons(player1);
        Assert.assertTrue(possibleWeapon.isEmpty());
        Weapon lockRifle = new LockRifle();
        player1.addWeapon(lockRifle);
        possibleWeapon = Validator.availableToFireWeapons(player1);
        Assert.assertTrue(possibleWeapon.isEmpty());
        lockRifle.setUnloaded();
        Player player2 = new Player("Francesco", Character.DOZER);
        player2.setPosition(map[0][1]);
        possibleWeapon = Validator.availableToFireWeapons(player1);
        Assert.assertTrue(possibleWeapon.isEmpty());
        lockRifle.setLoaded();
        possibleWeapon = Validator.availableToFireWeapons(player1);
        Assert.assertEquals(1, possibleWeapon.size());
        Assert.assertTrue(possibleWeapon.contains(lockRifle));
    }
}
