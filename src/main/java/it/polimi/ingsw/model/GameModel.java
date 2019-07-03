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
        setChanged();
        NewPlayerJoinedUpdateEvent message = new NewPlayerJoinedUpdateEvent(newPlayer.getUsername(), newPlayer.getCharacter());
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

    public void endGame(String message){
        notifyObservers(new EndGameUpdate(message));
    }

    public void reconnectionSetting(String username, ArrayList<Player> players){
        ReconnectionSettingsEvent reconnectionEvent = new ReconnectionSettingsEvent(username);
        reconnectionEvent.addEvent(new ReconnectionMapUpdate(this.gameboard.getMap().getChosenMap()));
        reconnectionEvent.addEvent(new KillShotTrackUpdateEvent( (Encoder.encodeDamageTokenList(((KillShotTrack)this.getGameboard().getGameTrack()).getTokenTrack())), this.getGameboard().getGameTrack().getTokenSequence()) );
        for (Player currentPlayer: players) {
            PlayerBoard playerBoard = currentPlayer.getPlayerBoard();
            reconnectionEvent.addEvent(new NewPlayerJoinedUpdateEvent(currentPlayer.getUsername(),currentPlayer.getCharacter()));
            reconnectionEvent.addEvent(new PlayerBoardUpdateEvent(currentPlayer.getCharacter(), playerBoard.getSkullsNumber(), Encoder.encodeDamageTokenList(playerBoard.getMarks()), Encoder.encodeDamagesTokenArray(playerBoard.getDamageReceived(), playerBoard.getDamageAmount())));
        }
        for (Player currentPlayer: players) {
            reconnectionEvent.addEvent(new PlayerPowerUpUpdateEvent(currentPlayer.getCharacter(), Encoder.encodePowerUpsType(currentPlayer.getPowerUps()), Encoder.encodePowerUpColour(currentPlayer.getPowerUps())));
            String[] messageWeapons = new String[currentPlayer.getNumberOfWeapons()];
            for (int i = 0; i < currentPlayer.getNumberOfWeapons(); i++){
                messageWeapons[i] = currentPlayer.getWeapons()[i].getName();
            }
            reconnectionEvent.addEvent(new PlayerWeaponUpdateEvent(currentPlayer.getCharacter(), messageWeapons, currentPlayer.getLoadedWeapons()));
        }
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                if(this.getGameboard().getMap().getSquareMatrix()[row][column] != null) {
                    ArrayList<Player> squarePlayers = this.getGameboard().getMap().getSquareMatrix()[row][column].getSquarePlayers();
                    if (!squarePlayers.isEmpty()) {
                        for (Player currSquarePlayer : squarePlayers) {
                            reconnectionEvent.addEvent(new PlayerPositionUpdateEvent(currSquarePlayer.getCharacter(), column, row));
                        }
                    }
                }
                if (this.getGameboard().getMap().getSpawnSquares().contains(this.getGameboard().getMap().getSquareMatrix()[row][column]))
                    reconnectionEvent.addEvent(new WeaponUpdateEvent(column,row, Encoder.encodeWeaponsIntoArray(((SpawnSquare)this.getGameboard().getMap().getSquareMatrix()[row][column]).getWeapons())));
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

