package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.cards.AmmoTilesDeck;
import it.polimi.ingsw.model.game_components.cards.PowerUpDeck;
import it.polimi.ingsw.model.game_components.cards.WeaponDeck;

public class GameBoard {

    private GameTrack gameTrack;
    private Map map;
    private WeaponDeck weaponDeck;
    private AmmoTilesDeck ammoTilesDeck;
    private PowerUpDeck powerUpDeck;
    private boolean finalFrenzy = false;


    public GameBoard(GameTrack gameTrack, Map map, WeaponDeck weaponDeck, AmmoTilesDeck ammoTilesDeck, PowerUpDeck powerUpDeck) {
        this.gameTrack = gameTrack;
        this.map = map;
        this.weaponDeck = weaponDeck;
        this.ammoTilesDeck = ammoTilesDeck;
        this.powerUpDeck = powerUpDeck;
    }

    public GameTrack getGameTrack() {
        return gameTrack;
    }

    public WeaponDeck getWeaponDeck() {
        return weaponDeck;
    }

    public AmmoTilesDeck getAmmoTilesDeck() {
        return ammoTilesDeck;
    }

    public PowerUpDeck getPowerUpDeck() {
        return powerUpDeck;
    }

    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    public void setFinalFrenzy(){ finalFrenzy = true; }

    public Map getMap() {
        return map;
    }
}
