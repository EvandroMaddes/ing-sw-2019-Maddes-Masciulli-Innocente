package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.cards.AmmoTilesDeck;
import it.polimi.ingsw.model.game_components.cards.PowerUpDeck;
import it.polimi.ingsw.model.game_components.cards.WeaponDeck;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameBoardTest{
    private GameBoard gameBoard;
    private GameTrack testedTrack;
    private Map testedMap;
    private Player testPlayer;
    private WeaponDeck weaponDeck;
    private AmmoTilesDeck ammoTilesDeck;
    private PowerUpDeck powerUpDeck;


    @Before
    public void setUp(){
        testedTrack = new KillShotTrack();
        testPlayer = new Player("Evandro", Character.D_STRUCT_OR);
        weaponDeck = new WeaponDeck();
        ammoTilesDeck = new AmmoTilesDeck();
        powerUpDeck = new PowerUpDeck();
        testedMap = new Map("leftSecond","rightSecond");
        gameBoard = new GameBoard(testedTrack,testedMap,weaponDeck,ammoTilesDeck,powerUpDeck);

    }

    @Test

    public void createGameboardTest(){
        Assert.assertFalse(gameBoard.isFinalFrenzy());
        gameBoard.getGameTrack().setSkullBox(5);
        Assert.assertEquals(5,testedTrack.getSkullBox());
        Assert.assertFalse(((KillShotTrack)gameBoard.getGameTrack()).checkEndTrack());
        Assert.assertEquals(21,gameBoard.getWeaponDeck().getDeck().size());
        Assert.assertEquals(36,gameBoard.getAmmoTilesDeck().getDeck().size());
        Assert.assertEquals(24,gameBoard.getPowerUpDeck().getDeck().size());



    }


}
