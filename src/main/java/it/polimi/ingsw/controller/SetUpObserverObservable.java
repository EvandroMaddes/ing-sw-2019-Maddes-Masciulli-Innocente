package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.List;
import java.util.Map;

/**
 * Class to manage the adding of the observers
 *
 * @author Federico Innocente
 */
public class SetUpObserverObservable {

    private SetUpObserverObservable() {
    }

    /**
     * Add the observers to the updateable objects to enable the notify to the virtual views
     *
     * @param players            are all the players joined in the game
     * @param playersVirtualView is the mapping between usernames and virtual views
     * @param model              is the game model of the game
     */
    public static void connect(List<Player> players, Map<String, VirtualView> playersVirtualView, GameModel model) {
        for (Player currentPlayer : players) {
            for (Player p : players) {
                //adding players and playersBoard
                currentPlayer.addObserver(playersVirtualView.get(p.getUsername()));
                currentPlayer.getPlayerBoard().addObserver(playersVirtualView.get(p.getUsername()));
            }
        }
        for (Player p : players) {
            String username = p.getUsername();
            // adding Squares
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 4; j++)
                    if (model.getGameboard().getMap().getSquareMatrix()[i][j] != null)
                        model.getGameboard().getMap().getSquareMatrix()[i][j].addObserver(playersVirtualView.get(username));
            model.getGameboard().getGameTrack().addObserver(playersVirtualView.get(username));
            // adding GameModel
            model.addObserver(playersVirtualView.get(username));
        }
    }
}
