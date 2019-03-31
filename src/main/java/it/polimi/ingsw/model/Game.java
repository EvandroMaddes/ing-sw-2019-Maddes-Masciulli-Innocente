package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private GameBoard gameboard;
    private Round round;

    private static Game ourInstance = new Game();
    public static Game getInstance() {
        return ourInstance;
    }

    private Game() {
    }

    private void initialize(){

    }
    private void getGameTrackPoints(){

    }

    private ArrayList<Player> winner(){
        
    }
}
