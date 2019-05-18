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

        CubeColour colour = CubeColour.Blue;
        AmmoCube[] reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1]= new AmmoCube(colour);
        AmmoCube[] alternativeEffectCost;
        AmmoCube[] firstOptionalEffectCost = new AmmoCube[1];
        firstOptionalEffectCost[0] = new AmmoCube(CubeColour.Red);
        AmmoCube[] secondOptionalEffectCost;
        LockRifle lockRifle = new LockRifle(colour,"LOCK RIFLE",reloadCost,firstOptionalEffectCost);
        deck.add(lockRifle);

        colour = CubeColour.Blue;
        reloadCost = new AmmoCube[1];
        reloadCost[0] = new AmmoCube(colour);
        alternativeEffectCost = new AmmoCube[2];
        alternativeEffectCost[0] = new AmmoCube(CubeColour.Blue);
        alternativeEffectCost[1] = new AmmoCube(CubeColour.Red);
        Electroscythe electroscythe = new Electroscythe(colour,"ELECTROSCYTHE",reloadCost,alternativeEffectCost);
        deck.add(electroscythe);

        colour = CubeColour.Blue;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Red);
        firstOptionalEffectCost = new AmmoCube[1];
        firstOptionalEffectCost[0] = new AmmoCube(CubeColour.Yellow);
        secondOptionalEffectCost = new AmmoCube[1];
        secondOptionalEffectCost[0] = new AmmoCube(CubeColour.Blue);
        MachineGun machineGun = new MachineGun(colour,"MACHINE GUN",reloadCost,firstOptionalEffectCost,secondOptionalEffectCost);
        deck.add(machineGun);

        colour = CubeColour.Blue;
        reloadCost = new AmmoCube[1];
        reloadCost[0] = new AmmoCube(colour);
        alternativeEffectCost = new AmmoCube[2];
        alternativeEffectCost[0] = new AmmoCube(CubeColour.Red);
        alternativeEffectCost[1] = new AmmoCube(CubeColour.Yellow);
        TractorBeam tractorBeam = new TractorBeam(colour,"TRACTOR BEAN",reloadCost,alternativeEffectCost);
        deck.add(tractorBeam);

        colour = CubeColour.Blue;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Red);
        firstOptionalEffectCost = new AmmoCube[1];
        firstOptionalEffectCost[0] = new AmmoCube(CubeColour.Blue);
        secondOptionalEffectCost = new AmmoCube[1];
        secondOptionalEffectCost[0] = new AmmoCube(CubeColour.Blue);
        Thor thor = new Thor(colour,"T.H.O.R",reloadCost,firstOptionalEffectCost,secondOptionalEffectCost);
        deck.add(thor);

        colour = CubeColour.Red;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1]= new AmmoCube(CubeColour.Blue);
        firstOptionalEffectCost = new AmmoCube[1];
        firstOptionalEffectCost[0] = new AmmoCube(CubeColour.Red);
        VortexCannon vortexCannon = new VortexCannon(colour,"VORTEX CANNON",reloadCost,firstOptionalEffectCost);
        deck.add(vortexCannon);

        colour = CubeColour.Red;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Blue);
        alternativeEffectCost = null;
        Furnace furnace = new Furnace(colour,"FURNACE",reloadCost,alternativeEffectCost);
        deck.add(furnace);


        colour = CubeColour.Blue;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Yellow);
        firstOptionalEffectCost = null;
        secondOptionalEffectCost = new AmmoCube[1];
        secondOptionalEffectCost[0] = new AmmoCube(CubeColour.Blue);
        PlasmaGun plasmaGun = new PlasmaGun(colour,"PLASMA GUN",reloadCost,firstOptionalEffectCost,secondOptionalEffectCost);
        deck.add(plasmaGun);

        colour = CubeColour.Red;
        reloadCost = new AmmoCube[3];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Red);
        reloadCost[2] = new AmmoCube(CubeColour.Yellow);
        Heatseeker heatseeker = new Heatseeker(colour,"HEATSEEKER",reloadCost);
        deck.add(heatseeker);

        colour = CubeColour.Blue;
        reloadCost = new AmmoCube[3];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Blue);
        reloadCost[2] = new AmmoCube(CubeColour.Yellow);
        Whisper whisper= new Whisper(colour,"WHISPER",reloadCost);
        deck.add(whisper);

        colour = CubeColour.Red;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Yellow);
        alternativeEffectCost = new AmmoCube[1];
        alternativeEffectCost[0] = new AmmoCube(CubeColour.Red);
        Hellion hellion = new Hellion(colour,"HELLION",reloadCost,alternativeEffectCost);
        deck.add(hellion);

        colour = CubeColour.Red;
        reloadCost = new AmmoCube[1];
        reloadCost[0] = new AmmoCube(colour);
        alternativeEffectCost = new AmmoCube[2];
        alternativeEffectCost[0] = new AmmoCube(CubeColour.Yellow);
        alternativeEffectCost[1] = new AmmoCube(CubeColour.Yellow);
        Flamethrower flamethrower = new Flamethrower(colour,"FLAMETHROWER",reloadCost,alternativeEffectCost);
        deck.add(flamethrower);

        colour = CubeColour.Yellow;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Red);
        alternativeEffectCost =null;
        Zx2 zx2= new Zx2(colour,"ZX-2",reloadCost,alternativeEffectCost);
        deck.add(zx2);

        colour = CubeColour.Red;
        reloadCost = new AmmoCube[1];
        reloadCost[0] = new AmmoCube(colour);
        firstOptionalEffectCost = new AmmoCube[1];
        firstOptionalEffectCost[0] = new AmmoCube(CubeColour.Red);
        GrenadaLauncher grenadaLauncher = new GrenadaLauncher(colour,"GRENADA LAUNCHER",reloadCost,firstOptionalEffectCost);
        deck.add(grenadaLauncher);

        colour = CubeColour.Yellow;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Yellow);
        alternativeEffectCost =null;
        Shotgun shotgun= new Shotgun(colour,"SHOTGUN",reloadCost,alternativeEffectCost);
        deck.add(shotgun);

        colour = CubeColour.Red;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Red);
        firstOptionalEffectCost = new AmmoCube[1];
        firstOptionalEffectCost[0] = new AmmoCube(CubeColour.Blue);
        secondOptionalEffectCost = new AmmoCube[1];
        secondOptionalEffectCost[0] = new AmmoCube(CubeColour.Yellow);
        RocketLauncher rocketLauncher = new RocketLauncher(colour,"ROCKET LAUNCHER",reloadCost,firstOptionalEffectCost,secondOptionalEffectCost);
        deck.add(rocketLauncher);

        colour = CubeColour.Yellow;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Blue);
        alternativeEffectCost = new AmmoCube[1];
        alternativeEffectCost[0] = new AmmoCube(CubeColour.Blue);
        PowerGlove powerGlove = new PowerGlove(colour,"POWER GLOVE",reloadCost,alternativeEffectCost);
        deck.add(powerGlove);

        colour = CubeColour.Yellow;
        reloadCost = new AmmoCube[3];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Yellow);
        reloadCost[2] = new AmmoCube(CubeColour.Blue);
        alternativeEffectCost =null;
        RailGun railGun = new RailGun(colour,"RAILGUN",reloadCost,alternativeEffectCost);
        deck.add(railGun);

        colour = CubeColour.Yellow;
        reloadCost = new AmmoCube[1];
        reloadCost[0] = new AmmoCube(colour);
        alternativeEffectCost = new AmmoCube[1];
        alternativeEffectCost[0] = new AmmoCube(CubeColour.Yellow);
        ShockWave shockwave = new ShockWave(colour,"SHOCKWAVE",reloadCost,alternativeEffectCost);
        deck.add(shockwave);

        colour = CubeColour.Yellow;
        reloadCost = new AmmoCube[2];
        reloadCost[0] = new AmmoCube(colour);
        reloadCost[1] = new AmmoCube(CubeColour.Red);
        firstOptionalEffectCost = null;
        secondOptionalEffectCost = new AmmoCube[1];
        secondOptionalEffectCost[0] = new AmmoCube(CubeColour.Yellow);
        CyberBlade cyberblade = new CyberBlade(colour,"CYBERBLADE",reloadCost,firstOptionalEffectCost,secondOptionalEffectCost);
        deck.add(cyberblade);

        colour = CubeColour.Yellow;
        reloadCost = new AmmoCube[1];
        reloadCost[0] = new AmmoCube(colour);
        alternativeEffectCost = new AmmoCube[1];
        alternativeEffectCost[0] = new AmmoCube(CubeColour.Red);
        Sledgehammer sledgehammer = new Sledgehammer(colour,"SLEDGEHAMMER",reloadCost,alternativeEffectCost);
        deck.add(sledgehammer);

        Collections.shuffle(deck);
        this.setDeck(deck);


    }

}
