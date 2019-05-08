package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;

import java.util.Observable;

import java.util.ArrayList;

public class GameModel extends Observable{
    
    private ArrayList<Player> players;
    private GameBoard gameboard;

    public GameModel(GameBoard gameboard, ArrayList<Player> players) {
        this.players = players;
        this.gameboard = gameboard;
    }

    public void notifyObservers()
    {

    }

    public GameBoard getGameboard() {
        return gameboard;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}

