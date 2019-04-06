package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.AmmoTilesDeck;
import it.polimi.ingsw.model.game_components.PowerUpDeck;
import it.polimi.ingsw.model.game_components.WeaponDeck;

public class GameBoard {

    //private ModeTrack modeTrack; if we develop a new game mode
    private Map map;
    private WeaponDeck weaponDeck;
    private AmmoTilesDeck ammoTilesDeck;
    private PowerUpDeck powerUpDeck;


    private static GameBoard ourInstance = new GameBoard();

    public static GameBoard getInstance()
    {
        return ourInstance;
    }

    private GameBoard() {
    }
}
