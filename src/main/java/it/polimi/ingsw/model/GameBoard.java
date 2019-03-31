package it.polimi.ingsw.model;

public class GameBoard {

    private ModeTrack modeTrank;
    private Map map;

    private static GameBoard ourInstance = new GameBoard();

    public static GameBoard getInstance() {
        return ourInstance;
    }

    private GameBoard() {
    }
}
