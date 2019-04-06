package it.polimi.ingsw.model.board;

import java.util.ArrayList;

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



}
