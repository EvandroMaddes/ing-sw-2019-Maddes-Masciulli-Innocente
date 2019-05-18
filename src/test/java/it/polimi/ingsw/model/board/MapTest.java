package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class MapTest {
    private Square[][] testedMapMatrix;
    private Player player1;
    private Player player2;
    private Square square1;
    private Square square2;





    @Before
    public void setUp(){
        Map testedMap = new Map("leftFirst", "rightFirst");
        testedMapMatrix = testedMap.getSquareMatrix();
        player1 = new Player("Evandro", Character.D_STRUCT_OR);
        player2 = new Player("Francecso", Character.BANSHEE);
        square1 = testedMapMatrix[0][0];
        player1.setPosition(square1);
        square2 = testedMapMatrix[1][0];
        player2.setPosition(square2);
        square1.addCurrentPlayer(player1);
        square2.addCurrentPlayer(player2);
    }

    @Test
    public void testMap(){
        //riga 1, colonna 1 -> 1,2 notreach;1,1 -> 0, 1 reach

        Assert.assertEquals(testedMapMatrix[1][2], testedMapMatrix[1][1].getNearSquares()[2]);
        Assert.assertTrue(testedMapMatrix[1][1].checkDirection(0));
        Assert.assertFalse(testedMapMatrix[1][1].checkDirection(2));
        Assert.assertEquals(square1,player1.getPosition());
        Assert.assertEquals(square2,player2.getPosition());
        Assert.assertTrue(player1.getPosition().findVisiblePlayers().contains(player2));
        Assert.assertTrue(player2.getPosition().findVisiblePlayers().contains(player1));



    }
}
