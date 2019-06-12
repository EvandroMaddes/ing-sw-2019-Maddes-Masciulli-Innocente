package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.weapons.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Francesco Masciulli
 * Class implementing the Weapon Deck
 * is just the concrete class of the DeckManagement Class
 */



public class WeaponDeck extends DeckManagement {

    /**@author Evandro Maddes
     * this method creates deck of all 21 weapons
     */
    public WeaponDeck() {


        ArrayList<Object> deck = new ArrayList<Object>();
        /*

        JsonParser parser = new JsonParser();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("weapon.json");
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject currentWeapon = rootElement.getAsJsonObject().getAsJsonObject("LOCK RIFLE");
        */

        deck.add(new LockRifle());
        deck.add(new Electroscythe());
        deck.add(new MachineGun());
        deck.add(new TractorBeam());
        deck.add(new Thor());
        deck.add(new VortexCannon());
        deck.add(new Furnace());
        deck.add(new PlasmaGun());
        deck.add(new Heatseeker());
        deck.add(new Whisper());
        deck.add(new Hellion());
        deck.add(new Flamethrower());
        deck.add(new Zx2());
        deck.add(new GrenadaLauncher());
        deck.add(new Shotgun());
        deck.add(new RocketLauncher());
        deck.add(new PowerGlove());
        deck.add(new Railgun());
        deck.add(new ShockWave());
        deck.add(new CyberBlade());
        deck.add(new Sledgehammer());

        Collections.shuffle(deck);
        this.setDeck(deck);

    }

    public Weapon findWeapon(String weapon){
        Weapon weaponFind = null;

        for (Object currWeapon:getDeck()
             ) {
            if (((Weapon)currWeapon).getName().equals(weapon)){

                weaponFind = ((Weapon)currWeapon);
            }

        }
        return weaponFind;
    }

}
