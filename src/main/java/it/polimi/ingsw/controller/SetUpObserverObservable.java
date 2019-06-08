package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.List;
import java.util.Map;

public class SetUpObserverObservable {

    public static void connect(List<Player> players, Map<String, VirtualView> playersVirtualView, GameBoard gameBoard){
        for (Player currentPlayer: players){
            for (Player p: players) {
                //adding players and playersBoard
                currentPlayer.addObserver(playersVirtualView.get(p.getUsername()));
                currentPlayer.getPlayerBoard().addObserver(playersVirtualView.get(p.getUsername()));
            }
        }
        for (Player p: players){
            String username = p.getUsername();
            // adding Squares
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 4; j++){
                    gameBoard.getMap().getSquareMatrix()[i][j].addObserver(playersVirtualView.get(username));
                }
            gameBoard.getGameTrack().addObserver(playersVirtualView.get(username));
        }
    }
}
