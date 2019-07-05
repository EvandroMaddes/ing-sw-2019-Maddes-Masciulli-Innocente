package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


public class MapTest {
    private Square[][] testedMapMatrix;
    private Player player1;
    private Player player2;
    private Square square1;
    private Square square2, square3, square4, square5, square6, square7, square8, square9, square10, square11, square12;
    private ArrayList<Square> mapList = new ArrayList<>();
    private Map testedMap;


    @Before
    public void setUp() {
        testedMap = new Map("leftFirst", "rightFirst");
        testedMapMatrix = testedMap.getSquareMatrix();
        player1 = new Player("Evandro", Character.D_STRUCT_OR);
        player2 = new Player("Francecso", Character.BANSHEE);


        square1 = testedMapMatrix[0][0];
        square2 = testedMapMatrix[1][0];
        square3 = testedMapMatrix[2][0];
        square4 = testedMapMatrix[0][1];
        square5 = testedMapMatrix[1][1];
        square6 = testedMapMatrix[2][1];
        square7 = testedMapMatrix[0][2];
        square8 = testedMapMatrix[1][2];
        square9 = testedMapMatrix[2][2];
        square10 = testedMapMatrix[0][3];
        square11 = testedMapMatrix[1][3];
        square12 = testedMapMatrix[2][3];

        mapList.add(square1);
        mapList.add(square2);
        mapList.add(square3);
        mapList.add(square4);
        mapList.add(square5);
        mapList.add(square6);
        mapList.add(square7);
        mapList.add(square8);
        mapList.add(square9);
        mapList.add(square10);
        mapList.add(square11);
        mapList.add(square12);


        player1.setPosition(square1);
        player2.setPosition(square2);
        square1.addCurrentPlayer(player1);
        square2.addCurrentPlayer(player2);
    }

    /**
     * Check that the map can correctly see its players on the square
     */
    @Test
    public void testMap() {
        //riga 1, colonna 1 -> 1,2 notreach;1,1 -> 0, 1 reach/

        Assert.assertEquals(testedMapMatrix[1][2], testedMapMatrix[1][1].getNearSquares()[2]);
        Assert.assertTrue(testedMapMatrix[1][1].checkDirection(0));
        Assert.assertFalse(testedMapMatrix[1][1].checkDirection(2));
        Assert.assertEquals(square1, player1.getPosition());
        Assert.assertEquals(square2, player2.getPosition());
        Assert.assertTrue(player1.getPosition().findVisiblePlayers().contains(player2));
        Assert.assertTrue(player2.getPosition().findVisiblePlayers().contains(player1));
    }

    /**
     * Check that the spawn squares are correctly save into the map
     */
    @Test
    public void spawnSquareTest() {
        for (Square spawn : testedMap.getSpawnSquares()
        ) {
            if (spawn.getRow() == 1 && spawn.getColumn() == 0) {
                Assert.assertEquals("Red", spawn.getSquareColour());
            } else if (spawn.getRow() == 0 && spawn.getColumn() == 2) {
                Assert.assertEquals("Blue", spawn.getSquareColour());
            } else if (spawn.getRow() == 2 && spawn.getColumn() == 3) {
                Assert.assertEquals("Yellow", spawn.getSquareColour());
            }
        }
    }
}
