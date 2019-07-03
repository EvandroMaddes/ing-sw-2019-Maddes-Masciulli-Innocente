package it.polimi.ingsw.model;

import it.polimi.ingsw.event.modelviewevent.*;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.KillShotTrack;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoTile;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.utils.Encoder;

import java.util.Observable;

import java.util.ArrayList;

/**
 * Class that represent the model of the game
 *
 * @author Federico Innocente
 */
public class GameModel extends Observable {

    /**
     * An ArrayList containing the match players
     */
    private ArrayList<Player> players;
    /**
     * The match GameBoard
     */
    private GameBoard gameboard;

    /**
     * Constructor: set the given GameBoard and allocate an empty ArrayList for players attribute
     *
     * @param gameboard
     */
    public GameModel(GameBoard gameboard) {
        this.players = new ArrayList<>();
        this.gameboard = gameboard;
    }

    /**
     * Getter method:
     *
     * @return the gameboard
     */
    public GameBoard getGameboard() {
        return gameboard;
    }

    /**
     * Getter method:
     *
     * @return the players ArrayList
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * when a player is added in the match, this method notifies the VirtualViews
     *
     * @param newPlayer
     */
    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
        setChanged();
        NewPlayerJoinedUpdateEvent message = new NewPlayerJoinedUpdateEvent(newPlayer.getUsername(), newPlayer.getCharacter());
        notifyObservers(message);
    }

    /**
     * Override of Observable notifyObservers method
     * Notify every VirtualView that observes the GameModel
     *
     * @param arg message that must be sent to the VirtualViews
     */
    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    /**
     * This method notifies the EndGameUpdate message
     *
     * @param message is the String that contains the match outcome
     */
    public void endGame(String message) {
        notifyObservers(new EndGameUpdate(message));
    }


    /**
     * This method create and notifies the ReconnectionSettingsEvent, that is sent after a client reconnection in the game;
     * the GameModel create a series of UpdateEvents, giving the reconnected player all of the info about the match;
     *
     * @param username is the reconnected player username
     * @param players  is an ArrayList that contains the players in-game
     */
    public void reconnectionSetting(String username, ArrayList<Player> players) {
        ReconnectionSettingsEvent reconnectionEvent = new ReconnectionSettingsEvent(username);
        reconnectionEvent.addEvent(new ReconnectionMapUpdate(this.gameboard.getMap().getChosenMap()));
        reconnectionEvent.addEvent(new KillShotTrackUpdateEvent((Encoder.encodeDamageTokenList(((KillShotTrack) this.getGameboard().getGameTrack()).getTokenTrack())), this.getGameboard().getGameTrack().getTokenSequence()));
        for (Player currentPlayer : players) {
            PlayerBoard playerBoard = currentPlayer.getPlayerBoard();
            reconnectionEvent.addEvent(new NewPlayerJoinedUpdateEvent(currentPlayer.getUsername(), currentPlayer.getCharacter()));
            reconnectionEvent.addEvent(new PlayerBoardUpdateEvent(currentPlayer.getCharacter(), playerBoard.getSkullsNumber(), Encoder.encodeDamageTokenList(playerBoard.getMarks()), Encoder.encodeDamagesTokenArray(playerBoard.getDamageReceived(), playerBoard.getDamageAmount())));
        }
        for (Player currentPlayer : players) {
            reconnectionEvent.addEvent(new PlayerPowerUpUpdateEvent(currentPlayer.getCharacter(), Encoder.encodePowerUpsType(currentPlayer.getPowerUps()), Encoder.encodePowerUpColour(currentPlayer.getPowerUps())));
            String[] messageWeapons = new String[currentPlayer.getNumberOfWeapons()];
            for (int i = 0; i < currentPlayer.getNumberOfWeapons(); i++) {
                messageWeapons[i] = currentPlayer.getWeapons()[i].getName();
            }
            reconnectionEvent.addEvent(new PlayerWeaponUpdateEvent(currentPlayer.getCharacter(), messageWeapons, currentPlayer.getLoadedWeapons()));
        }
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                if (this.getGameboard().getMap().getSquareMatrix()[row][column] != null) {
                    ArrayList<Player> squarePlayers = this.getGameboard().getMap().getSquareMatrix()[row][column].getSquarePlayers();
                    if (!squarePlayers.isEmpty()) {
                        for (Player currSquarePlayer : squarePlayers) {
                            reconnectionEvent.addEvent(new PlayerPositionUpdateEvent(currSquarePlayer.getCharacter(), column, row));
                        }
                    }
                }
                if (this.getGameboard().getMap().getSpawnSquares().contains(this.getGameboard().getMap().getSquareMatrix()[row][column]))
                    reconnectionEvent.addEvent(new WeaponUpdateEvent(column, row, Encoder.encodeWeaponsIntoArray(((SpawnSquare) this.getGameboard().getMap().getSquareMatrix()[row][column]).getWeapons())));
                else {
                    if (this.getGameboard().getMap().getSquareMatrix()[row][column] != null && ((BasicSquare) this.getGameboard().getMap().getSquareMatrix()[row][column]).checkAmmo()) {
                        AmmoTile ammoTile = ((BasicSquare) this.getGameboard().getMap().getSquareMatrix()[row][column]).getAmmo();
                        if (ammoTile.isPowerUpTile())
                            reconnectionEvent.addEvent(new AmmoTileUpdateEvent(true, column, row, ammoTile.getAmmoCubes()[0].getColour().name(),
                                    ammoTile.getAmmoCubes()[1].getColour().name(), "POWERUP"));
                        else
                            reconnectionEvent.addEvent(new AmmoTileUpdateEvent(true, column, row, ammoTile.getAmmoCubes()[0].getColour().name(),
                                    ammoTile.getAmmoCubes()[1].getColour().name(), ammoTile.getAmmoCubes()[2].getColour().name()));
                    }
                }
            }

        }
        notifyObservers(reconnectionEvent);
    }

}

