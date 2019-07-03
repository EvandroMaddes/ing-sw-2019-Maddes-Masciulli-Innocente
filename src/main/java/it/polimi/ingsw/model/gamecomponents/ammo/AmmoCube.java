package it.polimi.ingsw.model.gamecomponents.ammo;

import java.io.Serializable;
import java.util.List;

/**
 * This class represent an AmmoCube
 *
 * @author Federico Innocente
 */

public class AmmoCube implements Serializable {

    /**
     * Is the AmmoCube colour
     */
    private CubeColour colour;

    /**
     * Constructor: set the AmmoCube colour
     *
     * @param colour is the colour that must be set on creation
     */
    public AmmoCube(CubeColour colour) {
        this.colour = colour;
    }

    /**
     * Getter method:
     *
     * @return the AmmoCube colour
     */
    public CubeColour getColour() {
        return colour;
    }

    /**
     * Given an AmmoCube ArrayList, it create an array with, in each position,
     * the number of cubes of each colors in the ArrayList
     *
     * @param ammoCubeList is the list from which we want to derive the numbers of three colours
     * @return an int array, that represent the number of the three colour in the list (order: Red, Yellow, Blue)
     */
    public static int[] getColoursByListRYB(List<AmmoCube> ammoCubeList) {
        int[] colourRYB = new int[]{0, 0, 0};
        for (AmmoCube currentAmmoCube : ammoCubeList) {
            if (currentAmmoCube.getColour() == CubeColour.Red)
                colourRYB[0]++;
            else if (currentAmmoCube.getColour() == CubeColour.Yellow)
                colourRYB[1]++;
            else if (currentAmmoCube.getColour() == CubeColour.Blue)
                colourRYB[2]++;
        }
        return colourRYB;
    }

    /**
     * Given an array of CubeColour elements, it create an array with, in each position,
     * the number of cubes of each colors in the given array
     *
     * @param cubeColourArray is the array from which the method count the CubeColour elements
     * @return an int array, that represent the number of the three colour in the list (order: Red, Yellow, Blue)
     */
    public static int[] getColoursByCubeColourArrayRYB(CubeColour[] cubeColourArray) {
        int[] colourRYB = new int[]{0, 0, 0};
        for (CubeColour currentCubeColour : cubeColourArray) {
            if (currentCubeColour == CubeColour.Red)
                colourRYB[0]++;
            else if (currentCubeColour == CubeColour.Yellow)
                colourRYB[1]++;
            else
                colourRYB[2]++;
        }
        return colourRYB;
    }

    /**
     * Given an AmmoCube array, it create an array with, in each position,
     * the number of cubes of each colors in the ArrayList
     *
     * @param ammoCubeArray is the array from which the method count the AmmoCube elements of each colour
     * @return an int array, that represent the number of the three colour in the list (order: Red, Yellow, Blue)
     */
    public static int[] getColoursByAmmoCubeArrayRYB(AmmoCube[] ammoCubeArray) {
        int[] colourRYB = new int[]{0, 0, 0};
        for (AmmoCube currentAmmoCube : ammoCubeArray) {
            if (currentAmmoCube.getColour() == CubeColour.Red)
                colourRYB[0]++;
            else if (currentAmmoCube.getColour() == CubeColour.Yellow)
                colourRYB[1]++;
            else
                colourRYB[2]++;
        }
        return colourRYB;
    }

    /**
     * This method subtract, for each element of the arrays, the secondRYB's from the firstRYB's
     *
     * @param firstRYB  is the array from wich the elements are subtracted
     * @param secondRYB is the array the element of which are subtracted
     * @return the resulting array of int
     */
    public static int[] cubeDifference(int[] firstRYB, int[] secondRYB) {
        int[] differenceRYB = new int[3];
        for (int i = 0; i < 3; i++) {
            differenceRYB[i] = firstRYB[i] - secondRYB[i];
            if (differenceRYB[i] < 0)
                differenceRYB[i] = 0;
        }
        return differenceRYB;
    }

}
