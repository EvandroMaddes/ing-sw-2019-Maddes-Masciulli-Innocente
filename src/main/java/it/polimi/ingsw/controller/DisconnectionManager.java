package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.model_view_event.PlayerDisconnectionNotify;
import it.polimi.ingsw.event.model_view_event.PlayerReconnectionNotify;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Decoder;

import java.util.ArrayList;

public class DisconnectionManager {
    private Controller controller;
    private ArrayList<Player> disconnectedPlayers;
    private ArrayList<Player> gamePlayers;
    private ArrayList<Player> disconnectingQueue;
    private boolean[] availableCharacter;

    public DisconnectionManager(Controller controller) {
        this.controller = controller;
        disconnectedPlayers = new ArrayList<>();
        disconnectingQueue = new ArrayList<>();
        gamePlayers = controller.getGameManager().getModel().getPlayers();
        availableCharacter = new boolean[]{true, true, true, true, true};
    }

    public void disconnectionManage(String username){
        if (controller.getGameManager().getCurrentRound() == null){
            boolean isInGamePlayers = containsPlayer(username, gamePlayers);
            boolean isInDisconnectedPlayers = containsPlayer(username, disconnectedPlayers);
            boolean isInDisconnectingQueue = containsPlayer(username, disconnectingQueue);
            if (controller.getUsersVirtualView().get(username) != null && !isInGamePlayers && !isInDisconnectedPlayers && !isInDisconnectingQueue){
                defaultSetupDisconnection(username);
            }
        }

        Player disconnectedPlayer = Decoder.decodePlayerFromUsername(username, controller.getGameManager().getModel().getPlayers());
        if (controller.getGameManager().getCurrentRound() == null || controller.getGameManager().getCurrentRound().getCurrentPlayer() != disconnectedPlayer) {
            disconnectingQueue.add(disconnectedPlayer);
            if (controller.getGameManager().getCurrentRound().getPhase() == 7)
                controller.getGameManager().getCurrentRound().manageRound();
        }
        else
            removePlayer(disconnectedPlayer);
    }

    private boolean containsPlayer(String username, ArrayList<Player> playerList){
        boolean containsPlayer = false;
        for (Player p:playerList) {
            if(p.getUsername().equals(username))
                containsPlayer = true;
            switch (p.getCharacter()){
                case D_STRUCT_OR:{ availableCharacter[0] = false; break;}
                case SPROG:{ availableCharacter[1] = false; break;}
                case DOZER:{ availableCharacter[2] = false; break;}
                case BANSHEE:{ availableCharacter[3] = false; break;}
                case VIOLET:{ availableCharacter[4] = false; break;}
            }
        }
        return containsPlayer;
    }

    private void defaultSetupDisconnection(String username){
        Character defaultCharacter;
        if (availableCharacter[0])
            defaultCharacter = Character.D_STRUCT_OR;
        else if (availableCharacter[1])
            defaultCharacter = Character.SPROG;
        else if (availableCharacter[2])
            defaultCharacter = Character.DOZER;
        else if (availableCharacter[3])
            defaultCharacter = Character.BANSHEE;
        else
            defaultCharacter = Character.VIOLET;
        Player newPlayer = new Player(username, defaultCharacter);
        disconnectedPlayers.add(newPlayer);
        newPlayer.setPosition(controller.getGameManager().getModel().getGameboard().getMap().getSpawnSquares().get(0));
        newPlayer.getPosition().getSquarePlayers().remove(newPlayer);
        if (!gamePlayers.contains(newPlayer))
            gamePlayers.add(newPlayer);
    }

    public void removePlayer(Player disconnectedPlayer){
        disconnectingQueue.remove(disconnectedPlayer);
        controller.getGameManager().getModel().getPlayers().remove(disconnectedPlayer);
        this.disconnectedPlayers.add(disconnectedPlayer);
        controller.getUsersVirtualView().get(disconnectedPlayer.getUsername()).setPlayerDisonnected();
        if (disconnectedPlayer.getPosition() == null) {
            disconnectedPlayer.setPosition(controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[0][1]);
            controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[disconnectedPlayer.getPosition().getRow()][disconnectedPlayer.getPosition().getColumn()].removeCurrentPlayer(disconnectedPlayer);
        }
        controller.getGameManager().getModel().notifyObservers(new PlayerDisconnectionNotify(disconnectedPlayer.getCharacter()));
        if (controller.getGameManager().getModel().getPlayers().size() < 3)
            controller.getGameManager().endGame();
        else
            controller.getGameManager().newRound();
    }

    public void reconnectPlayer(String username){
        Player reconnectedPlayer;
        boolean playerInRemoveQueue = false;
        for (Player p: disconnectingQueue) {
            if (p.getUsername().equals(username))
                playerInRemoveQueue = true;
        }
        if(playerInRemoveQueue){
            reconnectedPlayer = Decoder.decodePlayerFromUsername(username, disconnectingQueue);
            disconnectingQueue.remove(reconnectedPlayer);
        }
        else {
            reconnectedPlayer = Decoder.decodePlayerFromUsername(username, disconnectedPlayers);
            controller.getGameManager().getModel().getPlayers().add(reconnectedPlayer);
            disconnectedPlayers.remove(reconnectedPlayer);
            controller.getUsersVirtualView().get(username).setPlayerConnected();
            controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[reconnectedPlayer.getPosition().getRow()][reconnectedPlayer.getPosition().getColumn()].addCurrentPlayer(reconnectedPlayer);
            reconnectedPlayer.setPosition(reconnectedPlayer.getPosition());
            controller.getGameManager().getModel().notifyObservers(new PlayerReconnectionNotify(reconnectedPlayer.getCharacter()));
            controller.getGameManager().getModel().reconnectionSetting(username, gamePlayers);
        }
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
