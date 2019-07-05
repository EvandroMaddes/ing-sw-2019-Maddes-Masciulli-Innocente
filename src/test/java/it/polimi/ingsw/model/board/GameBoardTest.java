package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.gamecomponents.cards.AmmoTilesDeck;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUpDeck;
import it.polimi.ingsw.model.gamecomponents.cards.WeaponDeck;
import org.junit.Assert;
import org.junit.Test;

public class GameBoardTest {

    /**
     * Check that the gameboard is correctly assembled
     */
    @Test
    public void createGameBoardTest() {
        GameTrack gameTrack = new KillShotTrack();
        Map map = new Map("leftFirst", "rightSecond");
        WeaponDeck weaponDeck = new WeaponDeck();
        AmmoTilesDeck ammoTilesDeck = new AmmoTilesDeck();
        PowerUpDeck powerUpDeck = new PowerUpDeck();
        GameBoard gameBoard = new GameBoard(gameTrack, map, weaponDeck, ammoTilesDeck, powerUpDeck);
        Assert.assertFalse(gameBoard.isFinalFrenzy());
        Assert.assertFalse((gameBoard.getGameTrack()).checkEndTrack());
        Assert.assertEquals(21, gameBoard.getWeaponDeck().getDeck().size());
        Assert.assertEquals(36, gameBoard.getAmmoTilesDeck().getDeck().size());
        Assert.assertEquals(24, gameBoard.getPowerUpDeck().getDeck().size());
    }

}
