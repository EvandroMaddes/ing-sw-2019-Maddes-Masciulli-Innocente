package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.model.gamecomponents.cards.weapons.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class implementing the Weapon Deck
 * is just the concrete class of the DeckManagement Class, which methods covers each of the WeaponDeck functionality
 *
 * @author Francesco Masciulli
 * @author Evandro Maddes
 */


public class WeaponDeck extends DeckManagement {

    /**
     * Constructor:
     * this method creates a deck of all of the 21 weapons
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
