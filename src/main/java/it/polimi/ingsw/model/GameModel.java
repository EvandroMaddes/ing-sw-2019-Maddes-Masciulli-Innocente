package it.polimi.ingsw.model;

import it.polimi.ingsw.event.model_view_event.NewPlayerJoinedEvent;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;

import java.util.Observable;

import java.util.ArrayList;

public class GameModel extends Observable{
    
    private ArrayList<Player> players;
    private GameBoard gameboard;

    public GameModel(GameBoard gameboard) {
        this.players = new ArrayList<>();
        this.gameboard = gameboard;
    }

    public GameBoard getGameboard() {
        return gameboard;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public static void createModel(GameBoard gameBoard){

    }

    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
        NewPlayerJoinedEvent message = new NewPlayerJoinedEvent(newPlayer);
        notifyObservers(message);
    }
}

