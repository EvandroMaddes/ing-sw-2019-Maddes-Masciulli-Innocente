package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.cards.AmmoTilesDeck;
import it.polimi.ingsw.model.game_components.cards.PowerUpDeck;
import it.polimi.ingsw.model.game_components.cards.WeaponDeck;

public class GameBoard {
    //todo check attributo
    //private ModeTrack modeTrack; if we develop a new game mode
    private  Map map;
    private  WeaponDeck weaponDeck;
    private  AmmoTilesDeck ammoTilesDeck;
    private PowerUpDeck powerUpDeck;


    private static GameBoard ourInstance = new GameBoard();

    public static GameBoard getInstance()
    {
        return ourInstance;
    }

    private GameBoard() {
    }

    /**
     * setter method
     * @param map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     *setter method
     * @param ammoTilesDeck
     */
    public void setAmmoTilesDeck(AmmoTilesDeck ammoTilesDeck) {
        this.ammoTilesDeck = ammoTilesDeck;
    }

    /**
     *setter method
     * @param powerUpDeck
     */
    public void setPowerUpDeck(PowerUpDeck powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
    }

    /**
     *setter method
     * @param weaponDeck
     */
    public void setWeaponDeck(WeaponDeck weaponDeck) {
        this.weaponDeck = weaponDeck;
    }
}
