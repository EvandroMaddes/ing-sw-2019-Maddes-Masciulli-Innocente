package it.polimi.ingsw.model.board;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 */
public class Map {

    public final static int DIM_X = 4;
    public final static int DIM_Y = 3;

    private int mapNumber;
    private Map map;
    private ArrayList<SpawnSquare> spawnSquares;

    private static Map ourInstance = new Map();

    public static Map getInstance()
    {
        return ourInstance;
    }

    private Map() {
    }


    /**
     * @param spawnSquares spawnSquare belonging at the current game
     */
    public void setSpawnSquares(ArrayList<SpawnSquare> spawnSquares) {
        this.spawnSquares = spawnSquares;
    }
}
