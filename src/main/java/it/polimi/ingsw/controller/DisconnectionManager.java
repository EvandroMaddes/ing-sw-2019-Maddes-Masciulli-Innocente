package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.modelviewevent.PlayerDisconnectionNotify;
import it.polimi.ingsw.event.modelviewevent.PlayerReconnectionNotify;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Decoder;

import java.util.ArrayList;

/**
 * Class to manage players disconnection e reconnection.
 * A player who disconnect is added to a disconnecting queue list: if he reconnect before his round no action are performed.
 * When a player in the disconnection queue have to play his round, or when a player left during his own round, he is removed from the game.
 * A player removed from the game is no more visible on the map, and other players cannot interact with him.
 * When a disconnected player reconnect, he is insert in the last position of the round queue.
 *
 * @author Federico Innocente
 */
public class DisconnectionManager {
    /**
     * The game controller
     */
    private Controller controller;
    /**
     * A list of all the player disconnected and removed from the game
     */
    private ArrayList<Player> disconnectedPlayers;
    /**
     * A list of all the players who joined the game
     */
    private ArrayList<Player> gamePlayers;
    /**
     * A list of the players that disconnected but didn't play their round since their disconnection.
     * If tehy will join back the game before their round, they will be readded to the game with no penalties
     */
    private ArrayList<Player> disconnectingQueue;
    /**
     * A vector with the avaiable characters to give the default ones
     */
    private boolean[] availableCharacter;

    /**
     * Constructor
     *
     * @param controller is the controller of the game
     */
    DisconnectionManager(Controller controller) {
        this.controller = controller;
        disconnectedPlayers = new ArrayList<>();
        disconnectingQueue = new ArrayList<>();
        gamePlayers = controller.getGameManager().getModel().getPlayers();
        availableCharacter = new boolean[]{true, true, true, true, true};
    }



    /*
     * Disconnection managing methods
     */

    /**
     * Manage player disconnection, removing him in his round or putting him in the disconnecting queue
     *
     * @param username is the username of the disconnected player
     */
    public void disconnectionManage(String username) {
        boolean disconnectedInChampSelect = false;
        if (controller.getGameManager().getCurrentRound() == null) {
            boolean isInGamePlayers = containsPlayer(username, gamePlayers);
            boolean isInDisconnectedPlayers = containsPlayer(username, disconnectedPlayers);
            boolean isInDisconnectingQueue = containsPlayer(username, disconnectingQueue);
            if (controller.getUsersVirtualView().get(username) != null && !isInGamePlayers && !isInDisconnectedPlayers && !isInDisconnectingQueue) {
                defaultSetupDisconnection(username);
                disconnectedInChampSelect = true;
            }
        }
        Player disconnectedPlayer = Decoder.decodePlayerFromUsername(username, gamePlayers);
        if (controller.getGameManager().getCurrentRound() == null || controller.getGameManager().getCurrentRound().getCurrentPlayer() != disconnectedPlayer) {
            disconnectingQueue.add(disconnectedPlayer);
            if (controller.getGameManager().getCurrentRound() != null && controller.getGameManager().getCurrentRound().getPhase() == 7)
                controller.getGameManager().getCurrentRound().manageRound();
        } else
            removePlayer(disconnectedPlayer);
        if (disconnectedInChampSelect)
            controller.getGameManager().characterSelect();
    }

    /**
     * Check a list contains a player
     *
     * @param username   is the username of the searched player
     * @param playerList is the list interrogated
     * @return true if the list contains the player
     */
    private boolean containsPlayer(String username, ArrayList<Player> playerList) {
        boolean containsPlayer = false;
        for (Player p : playerList) {
            if (p.getUsername().equals(username))
                containsPlayer = true;
            switch (p.getCharacter()) {
                case D_STRUCT_OR:
                    availableCharacter[0] = false;
                    break;
                case SPROG:
                    availableCharacter[1] = false;
                    break;
                case DOZER:
                    availableCharacter[2] = false;
                    break;
                case BANSHEE:
                    availableCharacter[3] = false;
                    break;
                case VIOLET:
                    availableCharacter[4] = false;
                    break;
            }
        }
        return containsPlayer;
    }

    /**
     * If the disconnected player didn't already choose a character, give him a default one from the available ones and create is player in the game model
     *
     * @param username is the username of the new player created
     */
    private void defaultSetupDisconnection(String username) {
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
        if (!gamePlayers.contains(newPlayer))
            gamePlayers.add(newPlayer);
    }

    /**
     * Remove the player from the game or the disconnecting queue.
     * The player is no longer visible on the map
     *
     * @param disconnectedPlayer is the removed player
     */
    void removePlayer(Player disconnectedPlayer) {
        if (disconnectingQueue.contains(disconnectedPlayer)) {
            controller.getGameManager().goNextPlayerTurn();
            disconnectingQueue.remove(disconnectedPlayer);
        }
        controller.getGameManager().getModel().getPlayers().remove(disconnectedPlayer);
        this.disconnectedPlayers.add(disconnectedPlayer);
        controller.getUsersVirtualView().get(disconnectedPlayer.getUsername()).setPlayerDisonnected();
        controller.getGameManager().getModel().notifyObservers(new PlayerDisconnectionNotify(disconnectedPlayer.getCharacter()));
        roundFlowManaging();
    }

    /**
     * Method called after a player remove because of disconnection:
     * if at least 3 players are still connected, start a new round, otherwise edn the game
     */
    private void roundFlowManaging() {
        if (controller.getGameManager().getModel().getPlayers().size() < 3)
            controller.getGameManager().endGame();
        else
            controller.getGameManager().newRound();
    }

    /**
     * Reconnect a player, setting him back on the map and on the player list
     *
     * @param username is the username of the reconnected player
     */
    public void reconnectPlayer(String username) {
        Player reconnectedPlayer;
        boolean playerInRemoveQueue = false;
        for (Player p : disconnectingQueue) {
            if (p.getUsername().equals(username))
                playerInRemoveQueue = true;
        }
        if (playerInRemoveQueue) {
            reconnectedPlayer = Decoder.decodePlayerFromUsername(username, disconnectingQueue);
            if (reconnectedPlayer.getPosition() == null && !controller.getGameManager().isFirstRoundPhase())
                reconnectedPlayer.setPosition(controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[0][2]);
            disconnectingQueue.remove(reconnectedPlayer);

        } else {
            reconnectedPlayer = Decoder.decodePlayerFromUsername(username, disconnectedPlayers);
            if (reconnectedPlayer.getPosition() == null && !controller.getGameManager().isFirstRoundPhase())
                reconnectedPlayer.setPosition(controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[0][2]);
            controller.getGameManager().getModel().getPlayers().add(reconnectedPlayer);
            disconnectedPlayers.remove(reconnectedPlayer);
            controller.getUsersVirtualView().get(username).setPlayerConnected();
            if (reconnectedPlayer.getPosition() != null) {
                controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[reconnectedPlayer.getPosition().getRow()][reconnectedPlayer.getPosition().getColumn()].addCurrentPlayer(reconnectedPlayer);
                reconnectedPlayer.setPosition(reconnectedPlayer.getPosition());
            }
            controller.getGameManager().getModel().notifyObservers(new PlayerReconnectionNotify(reconnectedPlayer.getCharacter()));
            controller.getGameManager().getModel().reconnectionSetting(username, gamePlayers);
        }
    }



    /*
     * Getter methods
     */

    /**
     * Getter method
     *
     * @return a list of all the players in the game, connected or disconnected
     */
    ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    /**
     * Getter method
     *
     * @return a list of the disconnected players
     */
    ArrayList<Player> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }

    /**
     * Getter method
     *
     * @return a list of the players into the disconnecting queue
     */
    ArrayList<Player> getDisconnectingQueue() {
        return disconnectingQueue;
    }
}
