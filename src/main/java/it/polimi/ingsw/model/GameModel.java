package it.polimi.ingsw.model;

import it.polimi.ingsw.event.model_view_event.*;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.KillShotTrack;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
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

    public void reconnectionSetting(String username, ArrayList<Player> players){
        ReconnectionSettingsEvent reconnectionEvent = new ReconnectionSettingsEvent(username);
        reconnectionEvent.addEvent(new ReconnectionMapUpdate(this.gameboard.getMap().getChosenMap()));
        reconnectionEvent.addEvent(new KillShotTrackUpdateEvent( (Encoder.encodeDamageTokenList(((KillShotTrack)this.getGameboard().getGameTrack()).getTokenTrack())), this.getGameboard().getGameTrack().getTokenSequence()) );
        for (Player currentPlayer: players) {
            PlayerBoard playerBoard = currentPlayer.getPlayerBoard();
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.getGameboard().getMap().getSpawnSquares().contains(this.getGameboard().getMap().getSquareMatrix()[i][j]))
                    reconnectionEvent.addEvent(new WeaponUpdateEvent(i,j, Encoder.encodeWeaponsIntoArray(((SpawnSquare)this.getGameboard().getMap().getSquareMatrix()[i][j]).getWeapons())));
                else {
                    if (this.getGameboard().getMap().getSquareMatrix()[i][j] != null && ((BasicSquare) this.getGameboard().getMap().getSquareMatrix()[i][j]).checkAmmo()) {
                        AmmoTile ammoTile = ((BasicSquare) this.getGameboard().getMap().getSquareMatrix()[i][j]).getAmmo();
                        if (ammoTile.isPowerUpTile())
                            reconnectionEvent.addEvent(new AmmoTileUpdateEvent(true, i, j, ammoTile.getAmmoCubes()[0].toString(), ammoTile.getAmmoCubes()[1].toString(), "POWERUP"));
                        else
                            reconnectionEvent.addEvent(new AmmoTileUpdateEvent(true, i, j, ammoTile.getAmmoCubes()[0].toString(), ammoTile.getAmmoCubes()[1].toString(), ammoTile.getAmmoCubes()[2].toString()));
                    }
                }
            }

        }
        notifyObservers(reconnectionEvent);
    }

}

