package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.gamecomponents.cards.AmmoTilesDeck;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUpDeck;
import it.polimi.ingsw.model.gamecomponents.cards.WeaponDeck;

/**
 * Class that represent the GameBoard
 * @author Evandro Maddes
 */
public class GameBoard {

    /**
     * Is the GameTrack implementation, that could depend on the mod chosen
     */
    private GameTrack gameTrack;
    /**
     * Is the map, chosen by the Game creator client after the connection to the lobby
     */
    private Map map;
    /**
     * is the match WeaponDeck that contains the weapons
     */
    private WeaponDeck weaponDeck;
    /**
     * is the match AmmoTilesDeck that contains the ammoTiles
     */
    private AmmoTilesDeck ammoTilesDeck;
    /**
     * is the match PowerUpDeck that contains the powerUps
     */
    private PowerUpDeck powerUpDeck;
    /**
     * This boolean is true, during a match, exclusively during the final frenzy
     */
    private boolean finalFrenzy = false;

    /**
     * Constructor: set the GameBoard elements depending on the Lobby creator's preferences
     * @param gameTrack is the GameTrack implementation
     * @param map is the chosen map implementation
     * @param weaponDeck is a shuffled and complete WeaponDeck
     * @param ammoTilesDeck is a shuffled and complete AmmoTileDeck
     * @param powerUpDeck is a shuffled and complete PowerUpDeck
     */
    public GameBoard(GameTrack gameTrack, Map map, WeaponDeck weaponDeck, AmmoTilesDeck ammoTilesDeck, PowerUpDeck powerUpDeck) {
        this.gameTrack = gameTrack;
        this.map = map;
        this.weaponDeck = weaponDeck;
        this.ammoTilesDeck = ammoTilesDeck;
        this.powerUpDeck = powerUpDeck;
    }

    /**
     * Getter method:
     * @return the gameTrack
     */
    public GameTrack getGameTrack() {
        return gameTrack;
    }

    /**
     * Getter method:
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Getter method:
     * @return the weaponDeck
     */
    public WeaponDeck getWeaponDeck() {
        return weaponDeck;
    }

    /**
     * Getter method:
     * @return the ammoTilesDeck
     */
    public AmmoTilesDeck getAmmoTilesDeck() {
        return ammoTilesDeck;
    }

    /**
     * Getter method:
     * @return the powerUpDeck
     */
    public PowerUpDeck getPowerUpDeck() {
        return powerUpDeck;
    }


    /**
     * Getter method:
     * @return true if is the final frenzy moment
     */
    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * set finalFrenzy value to true
     */
    public void setFinalFrenzy(){ finalFrenzy = true; }

}
