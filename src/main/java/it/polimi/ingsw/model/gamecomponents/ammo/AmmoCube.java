package it.polimi.ingsw.model.gamecomponents.ammo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Federico Innocente
 */

public class AmmoCube implements Serializable {

    private CubeColour colour;

    public AmmoCube (CubeColour colour)
    {
        this.colour = colour;
    }

    public CubeColour getColour()
    {
        return colour;
    }

    /**
     *
     * @param ammoCubeList is the list from which we want to derive the numbers of three colours
     * @return an int array, that rappresent the number of the three colour in the list (order: Red, Yellow, Blue)
     */
    public static int[] getColoursByListRYB(List<AmmoCube> ammoCubeList){
        int[] colourRYB = new int[]{0, 0, 0};
        for (AmmoCube currentAmmoCube: ammoCubeList){
            if (currentAmmoCube.getColour() == CubeColour.Red)
                colourRYB[0]++;
            else if (currentAmmoCube.getColour() == CubeColour.Yellow)
                colourRYB[1]++;
            else if (currentAmmoCube.getColour() == CubeColour.Blue)
                colourRYB[2]++;
        }
        return colourRYB;
    }

    public static int[] getColoursByCubeColourArrayRYB(CubeColour[] cubeColourArray){
        int[] colourRYB = new int[]{0, 0, 0};
        for (CubeColour currentCubeColour: cubeColourArray){
            if (currentCubeColour == CubeColour.Red)
                colourRYB[0]++;
            else if (currentCubeColour == CubeColour.Yellow)
                colourRYB[1]++;
            else
                colourRYB[2]++;
        }
        return colourRYB;
    }

    public static int[] getColoursByAmmoCubeArrayRYB(AmmoCube[] ammoCubeArray){
        int[] colourRYB = new int[]{0, 0, 0};
        for (AmmoCube currentAmmoCube: ammoCubeArray){
            if (currentAmmoCube.getColour() == CubeColour.Red)
                colourRYB[0]++;
            else if (currentAmmoCube.getColour() == CubeColour.Yellow)
                colourRYB[1]++;
            else
                colourRYB[2]++;
        }
        return colourRYB;
    }

    public static int[] cubeDifference(int[] firstRYB, int[] secondRYB){
        int[] differenceRYB = new int[3];
        for (int i = 0; i < 3; i++){
            differenceRYB[i] = firstRYB[i] - secondRYB[i];
            if (differenceRYB[i] < 0)
                differenceRYB[i] = 0;
        }
        return differenceRYB;
    }

}
