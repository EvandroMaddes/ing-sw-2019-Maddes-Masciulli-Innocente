package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.List;
import java.util.Map;

public class SetUpObserverObservable {

    public static void connect(List<Player> players, Map<String, VirtualView> playersVirtualView, GameModel model){
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
                for (int j = 0; j < 4; j++)
                    if (model.getGameboard().getMap().getSquareMatrix()[i][j] != null)
                        model.getGameboard().getMap().getSquareMatrix()[i][j].addObserver(playersVirtualView.get(username));
            model.getGameboard().getGameTrack().addObserver(playersVirtualView.get(username));
            // adding GameModel
//            model.addObserver(playersVirtualView.get(username));
        }
    }
}
