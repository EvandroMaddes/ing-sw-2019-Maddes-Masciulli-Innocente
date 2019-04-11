package it.polimi.ingsw.model.board;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 * Todo implementare metodi set
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
     * lega tutte le stanze alla mappa
     */
    public void setRooms()
    {

    }

    /**
     * lega tutti i punti di generazione alla mappa
     */
    public void setSpawnSquares()
    {

    }

}
