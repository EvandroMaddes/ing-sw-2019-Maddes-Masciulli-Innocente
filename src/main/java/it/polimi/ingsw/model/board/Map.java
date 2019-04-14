package it.polimi.ingsw.model.board;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 */
public class Map {

    private int mapNumber;
    private Map map;
    private ArrayList<Room> rooms;
    private ArrayList<SpawnSquare> spawnSquares;

    private static Map ourInstance = new Map();

    public static Map getInstance()
    {
        return ourInstance;
    }

    private Map() {
    }

    /**
     *
     * @param rooms rooms belonging at the current game
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @param spawnSquares spawnSquare belonging at the current game
     */
    public void setSpawnSquares(ArrayList<SpawnSquare> spawnSquares) {
        this.spawnSquares = spawnSquares;
    }
}
