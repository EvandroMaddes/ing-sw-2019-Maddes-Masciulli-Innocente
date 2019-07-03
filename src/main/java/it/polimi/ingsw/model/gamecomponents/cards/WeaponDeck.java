package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.model.gamecomponents.cards.weapons.*;

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


        ArrayList<Object> deck = new ArrayList<>();

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

}
