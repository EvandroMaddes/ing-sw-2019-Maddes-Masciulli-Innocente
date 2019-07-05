package it.polimi.ingsw.model.gamecomponents.ammo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class AmmoCubeTest {
    AmmoCube ammoCube1;
    AmmoCube ammoCube2;
    AmmoCube ammoCube3;

    @Before
    public void setUp() {
        ammoCube1 = new AmmoCube(CubeColour.Blue);
        ammoCube2 = new AmmoCube(CubeColour.Red);
        ammoCube3 = new AmmoCube(CubeColour.Blue);
    }

    /**
     * Check that the costs are rightly encoded in a in a int[]
     */
    @Test
    public void getColoursTest() {
        int[] expectedRYB = new int[]{1, 0, 2};

        AmmoCube[] ammoCubesArray = new AmmoCube[]{ammoCube1, ammoCube2, ammoCube3};
        int[] result = AmmoCube.getColoursByAmmoCubeArrayRYB(ammoCubesArray);
        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(expectedRYB[i], result[i]);
        }

        ArrayList<AmmoCube> ammoCubesList = new ArrayList<>();
        ammoCubesList.add(ammoCube1);
        ammoCubesList.add(ammoCube2);
        ammoCubesList.add(ammoCube3);
        result = AmmoCube.getColoursByListRYB(ammoCubesList);
        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(expectedRYB[i], result[i]);
        }

        CubeColour[] cubeColours = new CubeColour[]{CubeColour.Blue, CubeColour.Blue, CubeColour.Red};
        result = AmmoCube.getColoursByCubeColourArrayRYB(cubeColours);
        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(expectedRYB[i], result[i]);
        }
    }

    /**
     * Check that the method cubeDifference(int[], int[]) works correctly
     */
    @Test
    public void cubeDifferenceTest() {
        int[] a = new int[]{2, 3, 1};
        int[] b = new int[]{1, 2, 1};
        int[] expectedResult = new int[]{1, 1, 0};
        int[] result = AmmoCube.cubeDifference(a, b);
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(expectedResult[i], result[i]);
        }
        a = new int[]{2, 3, 1};
        b = new int[]{3, 3, 3};
        expectedResult = new int[]{0, 0, 0};
        result = AmmoCube.cubeDifference(a, b);
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(expectedResult[i], result[i]);
        }


    }
}
