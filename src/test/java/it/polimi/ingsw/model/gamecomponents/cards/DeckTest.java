package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.model.gamecomponents.ammo.AmmoTile;
import org.junit.Assert;
import org.junit.Test;

public class DeckTest {

    @Test
    public void emptyPowerUpDeckTest(){
        PowerUpDeck powerUpDeck1 = new PowerUpDeck();
        PowerUpDeck powerUpDeck2 = new PowerUpDeck();
        Assert.assertEquals(24, powerUpDeck1.getDeck().size());
        Assert.assertEquals(24, powerUpDeck2.getDeck().size());
        boolean sameOrder = true;
        for (int i = 0; i < 24; i++) {
            boolean sameCards = false;
            for (int j = 0; j < 24; j++) {
                if (((PowerUp)powerUpDeck1.getDeck().get(i)).getName().equals(((PowerUp)powerUpDeck2.getDeck().get(j)).getName()) && ((PowerUp)powerUpDeck1.getDeck().get(i)).getColour() == ((PowerUp)powerUpDeck2.getDeck().get(j)).getColour()) {
                    sameCards = true;
                    if (i != j)
                        sameOrder = false;
                }
            }
            Assert.assertTrue(sameCards);
        }
        Assert.assertFalse(sameOrder);
    }

    @Test
    public void reshufflePowerUpDeckTest(){
        PowerUpDeck powerUpDeck = new PowerUpDeck();
        PowerUpDeck powerUpDeck1 = new PowerUpDeck();
        for (int i = 0; i < 24; i++) {
            PowerUp powerUp = (PowerUp) powerUpDeck.draw();
            powerUpDeck.discardCard(powerUp);
        }
        Assert.assertEquals(24, powerUpDeck.getDiscardDeck().size());
        Assert.assertEquals(0, powerUpDeck.getDeck().size());
        powerUpDeck.reshuffle();
        Assert.assertEquals(0, powerUpDeck.getDiscardDeck().size());
        Assert.assertEquals(24, powerUpDeck.getDeck().size());
        boolean sameOrder = true;
        for (int i = 0; i < 24; i++) {
            boolean sameCards = false;
            for (int j = 0; j < 24; j++) {
                if (((PowerUp)powerUpDeck1.getDeck().get(i)).getName().equals(((PowerUp)powerUpDeck.getDeck().get(j)).getName()) && ((PowerUp)powerUpDeck1.getDeck().get(i)).getColour() == ((PowerUp)powerUpDeck.getDeck().get(j)).getColour()) {
                    sameCards = true;
                    if (i != j)
                        sameOrder = false;
                }
            }
            Assert.assertTrue(sameCards);
        }
        Assert.assertFalse(sameOrder);

    }

    @Test
    public void reshuffleAmmoTileDeckTest(){
        AmmoTilesDeck ammoTilesDeck1 = new AmmoTilesDeck();
        for (int i = 0; i < 36; i++) {
            AmmoTile ammoTile = (AmmoTile) ammoTilesDeck1.draw();
            ammoTilesDeck1.discardCard(ammoTile);
        }
        Assert.assertEquals(36, ammoTilesDeck1.getDiscardDeck().size());
        Assert.assertEquals(0, ammoTilesDeck1.getDeck().size());
        ammoTilesDeck1.reshuffle();
        Assert.assertEquals(0, ammoTilesDeck1.getDiscardDeck().size());
        Assert.assertEquals(36, ammoTilesDeck1.getDeck().size());
    }
}
