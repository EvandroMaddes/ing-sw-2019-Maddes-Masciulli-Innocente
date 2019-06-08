package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.model_view_event.PlayerDisconnectionNotify;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Decoder;

import java.util.ArrayList;

public class DisconnectionManager {
    Controller controller;
    ArrayList<Player> disconnectedPlayers;


    public DisconnectionManager(Controller controller) {
        this.controller = controller;
        disconnectedPlayers = new ArrayList<>();
    }

    public void removePlayer(Player disconnectedPlayer){
        controller.getGameManager().getModel().getPlayers().remove(disconnectedPlayer);
        this.disconnectedPlayers.add(disconnectedPlayer);
        controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[disconnectedPlayer.getPosition().getRow()][disconnectedPlayer.getPosition().getColumn()].removeCurrentPlayer(disconnectedPlayer);
        controller.getGameManager().getModel().notifyObservers(new PlayerDisconnectionNotify(disconnectedPlayer.getUsername(), disconnectedPlayer.getCharacter()));

    }

    public void reconnectPlayer(String username){
        Player reconnectedPlayer = Decoder.decodePlayerFromUsername(username, disconnectedPlayers);
        disconnectedPlayers.remove(reconnectedPlayer);
        controller.getGameManager().getModel().getPlayers().add(reconnectedPlayer);
        controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[reconnectedPlayer.getPosition().getRow()][reconnectedPlayer.getPosition().getColumn()].addCurrentPlayer(reconnectedPlayer);
        controller.getGameManager().getModel().notifyObservers(new PlayerReconnectionNotify(reconnectedPlayer.getUsername(), reconnectedPlayer.getCharacter()));
    }
}
