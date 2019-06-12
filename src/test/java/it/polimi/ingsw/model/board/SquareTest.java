package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SquareTest {
    private Map map;
    private Player player1;
    private Player player2;
    private Player player3;

    @Before
    public void setUp() {
        map = new Map("leftFirst", "rightSecond");
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.VIOLET);
        player3 = new Player("Evandro", Character.DOZER);
    }

    @Test
    public void squarePlayersTest() {
        Square testSquare = new Square(0,0) {
            @Override
            public boolean isGrabbable(Player grabber) {
                return false;
            }
        };
        Player testPlayer = new Player("Federico", Character.SPROG);
        testSquare.addCurrentPlayer(testPlayer);
        Assert.assertEquals(1, testSquare.getSquarePlayers().size());
        Assert.assertEquals(testPlayer, testSquare.getSquarePlayers().get(0));
        testSquare.removeCurrentPlayer(testPlayer);
        Assert.assertEquals(0, testSquare.getSquarePlayers().size());
    }

    @Test
    public void findVisibleSquaresTest(){
        ArrayList<Square> visibleSquareResult;
        ArrayList<Square> expectedVisibleSquare = new ArrayList<>();
        Square startingSquare = map.getSquareMatrix()[0][0];
        visibleSquareResult = startingSquare.findVisibleSquare();
        expectedVisibleSquare.add(map.getSquareMatrix()[0][0]);
        expectedVisibleSquare.add(map.getSquareMatrix()[0][1]);
        expectedVisibleSquare.add(map.getSquareMatrix()[0][2]);
        expectedVisibleSquare.add(map.getSquareMatrix()[1][0]);
        for (Square s: visibleSquareResult) {
            Assert.assertTrue(expectedVisibleSquare.contains(s));
        }
        for (Square s: expectedVisibleSquare) {
            Assert.assertTrue(visibleSquareResult.contains(s));
        }
    }

    @Test
    public void getNextSquarePlayersTest(){
        player1.setPosition(map.getSquareMatrix()[1][0]);
        player2.setPosition(map.getSquareMatrix()[1][1]);
        player3.setPosition(map.getSquareMatrix()[1][2]);
        ArrayList<Player> expectedResult = new ArrayList<>();
        expectedResult.add(player3);
        Assert.assertEquals(expectedResult, map.getSquareMatrix()[1][1].getNextSquarePlayer());
    }

    @Test
    public void reachableInMovesTest(){
        ArrayList<Square> result = map.getSquareMatrix()[2][0].reachableInMoves(2);
        ArrayList<Square> expectedResult = new ArrayList<>();
        expectedResult.add(map.getSquareMatrix()[2][0]);
        expectedResult.add(map.getSquareMatrix()[1][0]);
        expectedResult.add(map.getSquareMatrix()[0][0]);
        expectedResult.add(map.getSquareMatrix()[2][1]);
        expectedResult.add(map.getSquareMatrix()[1][1]);
        expectedResult.add(map.getSquareMatrix()[2][2]);
        for (Square s:result) {
            Assert.assertTrue(expectedResult.contains(s));
        }
        for (Square s:expectedResult) {
            Assert.assertTrue(result.contains(s));
        }

        result = map.getSquareMatrix()[0][0].reachableInMoves(0);
        expectedResult.clear();
        expectedResult.add(map.getSquareMatrix()[0][0]);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void findRoomPlayersTest(){
        player1.setPosition(map.getSquareMatrix()[2][0]);
        player2.setPosition(map.getSquareMatrix()[2][2]);
        player3.setPosition(map.getSquareMatrix()[1][0]);
        ArrayList<Player> result = map.getSquareMatrix()[2][0].findRoomPlayers();
        ArrayList<Player> expectedResult = new ArrayList<>();
        expectedResult.add(player1);
        expectedResult.add(player2);
        for (Player p:result) {
            Assert.assertTrue(expectedResult.contains(p));
        }
        for (Player p:expectedResult) {
            Assert.assertTrue(result.contains(p));
        }
        expectedResult.clear();
        result = map.getSquareMatrix()[0][1].findRoomPlayers();
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void findVisiblePlayersTest(){
        player1.setPosition(map.getSquareMatrix()[1][3]);
        player1.setPosition(map.getSquareMatrix()[2][0]);
        player1.setPosition(map.getSquareMatrix()[1][2]);
        ArrayList<Player> result = map.getSquareMatrix()[0][0].findVisiblePlayers();
        ArrayList<Player> expectedResult = new ArrayList<>();
        Assert.assertEquals(expectedResult, result);
        result = map.getSquareMatrix()[2][2].findVisiblePlayers();
        for (Player p:result) {
            Assert.assertTrue(expectedResult.contains(p));
        }
        for (Player p:expectedResult) {
            Assert.assertTrue(result.contains(p));
        }
    }

}
