package it.polimi.ingsw.model.gamecomponents.cards;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoTile;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class implementing the Ammo Tiles Deck
 *
 * @author Francesco Masciulli
 * @author Evandro Maddes
 */

public class AmmoTilesDeck extends DeckManagement {

    /**
     * ArrayList with the discarded cards
     */
    private ArrayList<AmmoTile> discardDeck = new ArrayList<>();

    /**
     * Constructor: this method creates the ammo tiles deck from the schema saved on a resource Json
     */
    public AmmoTilesDeck() {


        ArrayList<Object> deck = new ArrayList<>();
        JsonParser parser = new JsonParser();
        InputStream input = getClass().getClassLoader().getResourceAsStream("json/ammoTile.json");
        Reader reader = new InputStreamReader(input);
        JsonElement rootElement = parser.parse(reader);
        JsonArray jsonCubeAmmo = rootElement.getAsJsonObject().getAsJsonArray("CubeAmmoTile");
        JsonArray jsonPowerUpAmmo = rootElement.getAsJsonObject().getAsJsonArray("PowerUpAmmoTile");


        for (int i = 0; i < 6; i++) {
            createAmmoTile(jsonCubeAmmo.get(i), deck);
            createAmmoTile(jsonCubeAmmo.get(i), deck);
            createAmmoTile(jsonCubeAmmo.get(i), deck);
            createAmmoTile(jsonPowerUpAmmo.get(i), deck);
            createAmmoTile(jsonPowerUpAmmo.get(i), deck);
        }

        for (int i = 3; i < 6; i++) {
            createAmmoTile(jsonPowerUpAmmo.get(i), deck);
            createAmmoTile(jsonPowerUpAmmo.get(i), deck);
        }
        Collections.shuffle(deck);
        this.setDeck(deck);

    }

    /**
     * Getter method:
     *
     * @return the discardDeck;
     */
    public ArrayList<AmmoTile> getDiscardDeck() {
        return discardDeck;
    }

    /**
     * this method add a discarded AmmoTile in the discardDeck, used by reshuffle method
     *
     * @param discardedAmmoTile is the card that a player discard (if he grab the 4th one)
     */
    public void discardCard(AmmoTile discardedAmmoTile) {
        discardDeck.add(discardedAmmoTile);
    }

    /**
     * the super method couldn't throw an exception, because the reshuffle method calling prevent it
     *
     * @return the first Weapon Card element from the Deck, if the last one is not empty;
     */
    @Override
    public Object draw() {
        if (getDeck().isEmpty()) {
            reshuffle();
        }
        return super.draw();
    }

    /**
     * When the Deck's empty, it is filled with the discardDeck Elements end shuffled;
     */
    public void reshuffle() {
        setDeck((ArrayList<Object>) discardDeck.clone());
        shuffle();
        discardDeck.clear();
    }


    /**
     * this method takes property of a single ammo tile from a json file and create an ammo tile
     *
     * @param jsonElement group of ammo tiles
     * @param deck        is the deck in which is put the created AmmoTile
     */
    private void createAmmoTile(JsonElement jsonElement, ArrayList<Object> deck) {
        boolean isPowerUpTile = false;


        CubeColour first = CubeColour.valueOf(jsonElement.getAsJsonObject().get("firstAmmo").getAsString());
        CubeColour second = CubeColour.valueOf(jsonElement.getAsJsonObject().get("secondAmmo").getAsString());

        CubeColour third;
        try {
            third = CubeColour.valueOf(jsonElement.getAsJsonObject().get("thirdAmmo").getAsString());
        } catch (NullPointerException e) {
            third = null;
            isPowerUpTile = true;

        }

        AmmoCube firstCube = new AmmoCube(first);
        AmmoCube secondCube = new AmmoCube(second);
        AmmoCube thirdCube = new AmmoCube(third);
        AmmoTile currentAmmo = new AmmoTile(firstCube, secondCube, thirdCube, isPowerUpTile);

        deck.add(currentAmmo);


    }


}
