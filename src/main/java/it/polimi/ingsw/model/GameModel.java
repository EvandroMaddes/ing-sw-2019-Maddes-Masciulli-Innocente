package it.polimi.ingsw.model;

import it.polimi.ingsw.event.model_view_event.NewPlayerJoinedUpdateEvent;
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

    /**
     * when a plyer is added, this method notify VirtualView
     * @param newPlayer
     */
    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
        NewPlayerJoinedUpdateEvent message = new NewPlayerJoinedUpdateEvent(newPlayer.getUsername());
        notifyObservers(message);
    }

    /**
     *
     * @param arg message to send to VirtualView
     */
    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}

