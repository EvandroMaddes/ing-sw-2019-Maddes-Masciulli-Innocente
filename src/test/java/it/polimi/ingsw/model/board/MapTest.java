package it.polimi.ingsw.model.board;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class MapTest {
    private Square[][] testedMapMatrix;


    @Before
    public void setUp(){
        Map testedMap = new Map("leftFirst", "rightFirst");
        testedMapMatrix = testedMap.getSquareMatrix();
    }

    @Test
    public void testMap(){
        //riga 1, colonna 1 -> 1,2 notreach;1,1 -> 0, 1 reach
        Assert.assertEquals(testedMapMatrix[1][2], testedMapMatrix[1][1].getNearSquares()[2]);
        Assert.assertTrue(testedMapMatrix[1][1].checkDirection(0));
        Assert.assertFalse(testedMapMatrix[1][1].checkDirection(2));
    }
}
