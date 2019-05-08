package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.cards.AmmoTilesDeck;
import it.polimi.ingsw.model.game_components.cards.PowerUpDeck;
import it.polimi.ingsw.model.game_components.cards.WeaponDeck;

public class GameBoard {

    private GameTrack track;
    private  Map map;
    private  WeaponDeck weaponDeck;
    private  AmmoTilesDeck ammoTilesDeck;
    private PowerUpDeck powerUpDeck;

    /**
     * al momento è fatto per gestire solo la modalità base
     * @param map
     * @param weaponDeck
     * @param ammoTilesDeck
     * @param powerUpDeck
     */
    public GameBoard(Map map, WeaponDeck weaponDeck, AmmoTilesDeck ammoTilesDeck, PowerUpDeck powerUpDeck) {
        this.map = map;
        this.weaponDeck = weaponDeck;
        this.ammoTilesDeck = ammoTilesDeck;
        this.powerUpDeck = powerUpDeck;
        //per gestire più modalità questo va come parametro
        this.track = new KillShotTrack();
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

    public GameTrack getTrack() {
        return track;
    }
}
