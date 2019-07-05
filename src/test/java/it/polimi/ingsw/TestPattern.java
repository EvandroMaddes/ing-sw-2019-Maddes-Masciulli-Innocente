package it.polimi.ingsw;

import org.junit.Assert;

public class TestPattern {

    /**
     * Pattern to test the correctness of some square's lite form
     * @param expectedX Are the correct row expected
     * @param expectedY are the correct column expected
     * @param actualX are the row checked
     * @param actualY are the column checked
     */
    public static void checkSquares(int[] expectedX, int[] expectedY, int[] actualX, int[] actualY){
        for (int i = 0; i < expectedX.length; i++) {
            boolean check = false;
            for (int j = 0; j < expectedX.length; j++) {
                if (expectedX[i] == actualX[j] && expectedY[i] == actualY[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }
    }
}
