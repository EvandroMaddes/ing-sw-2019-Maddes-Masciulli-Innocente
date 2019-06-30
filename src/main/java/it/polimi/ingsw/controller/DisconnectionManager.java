package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.model_view_event.PlayerDisconnectionNotify;
import it.polimi.ingsw.event.model_view_event.PlayerReconnectionNotify;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Decoder;

import java.util.ArrayList;

public class DisconnectionManager {
    private Controller controller;
    private ArrayList<Player> disconnectedPlayers;
    private ArrayList<Player> gamePlayers;
    private ArrayList<Player> disconnectingQueue;

    public DisconnectionManager(Controller controller) {
        this.controller = controller;
        disconnectedPlayers = new ArrayList<>();
        disconnectingQueue = new ArrayList<>();
        gamePlayers = controller.getGameManager().getModel().getPlayers();
    }

    public void disconnectionManage(String username){
        Player disconnectedPlayer = Decoder.decodePlayerFromUsername(username, controller.getGameManager().getModel().getPlayers());
        if (controller.getGameManager().getCurrentRound().getCurrentPlayer() != disconnectedPlayer)
            disconnectingQueue.add(disconnectedPlayer);
        else
            removePlayer(disconnectedPlayer);
    }

    public void removePlayer(Player disconnectedPlayer){
        disconnectingQueue.remove(disconnectedPlayer);
        controller.getGameManager().getModel().getPlayers().remove(disconnectedPlayer);
        this.disconnectedPlayers.add(disconnectedPlayer);
        controller.getUsersVirtualView().get(disconnectedPlayer.getUsername()).setPlayerDisonnected();
        controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[disconnectedPlayer.getPosition().getRow()][disconnectedPlayer.getPosition().getColumn()].removeCurrentPlayer(disconnectedPlayer);
        controller.getGameManager().getModel().notifyObservers(new PlayerDisconnectionNotify(disconnectedPlayer.getCharacter()));
        if (controller.getGameManager().getModel().getPlayers().size() < 3)
            controller.getGameManager().endGame();
        else
            controller.getGameManager().newRound();
    }

    public void reconnectPlayer(String username){
        Player reconnectedPlayer = Decoder.decodePlayerFromUsername(username, disconnectedPlayers);
        controller.getGameManager().getModel().getPlayers().add(reconnectedPlayer);
        disconnectedPlayers.remove(reconnectedPlayer);
        controller.getUsersVirtualView().get(username).setPlayerConnected();
        controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[reconnectedPlayer.getPosition().getRow()][reconnectedPlayer.getPosition().getColumn()].addCurrentPlayer(reconnectedPlayer);
        controller.getGameManager().getModel().notifyObservers(new PlayerReconnectionNotify(reconnectedPlayer.getCharacter()));
    }

    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public ArrayList<Player> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }

    public ArrayList<Player> getDisconnectingQueue() {
        return disconnectingQueue;
    }
}
