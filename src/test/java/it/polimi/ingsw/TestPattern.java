package it.polimi.ingsw;

import org.junit.Assert;

public class TestPattern {

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
