package it.polimi.ingsw.model.player;

import it.polimi.ingsw.event.modelviewevent.AmmoUpdateEvent;
import it.polimi.ingsw.event.modelviewevent.PlayerPowerUpUpdateEvent;
import it.polimi.ingsw.event.modelviewevent.PlayerWeaponUpdateEvent;
import it.polimi.ingsw.event.modelviewevent.PlayerPositionUpdateEvent;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.*;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.utils.Encoder;

import java.util.*;

/**
 * class that manage the players
 *
 * @author Federico Innocente
 */
public class Player extends Observable {

    /**
     * Max number of weapon that a player could carry simultaneously, according with the game rules, is 3
     */
    private static final int MAX_WEAPONS = 3;

    /**
     * Is the String that represent the Username of the player, used, with others, as ID
     */
    private final String username;
    /**
     * The character chosen by the player
     */
    private final Character character;
    /**
     * Is the player's PlayerBoard
     */
    private PlayerBoard playerBoard;
    /**
     * The number of points earned by the player during the match
     */
    private int points;
    /**
     * The player position on the map
     */
    private Square position;
    /**
     * An arrayList containing each AmmoCube grabbed by the player
     */
    private ArrayList<AmmoCube> ammo;
    /**
     * This boolean is true if the player is the first in the round sequence, used during the final frenzy
     */
    private boolean firstPlayer;
    /**
     * An array that contains the weapons carried by the player;
     * he could grab a fourth, but then he must discard another one;
     */
    private Weapon[] weapons = new Weapon[MAX_WEAPONS + 1];
    /**
     * An ArrayList that contains each PowerUp grabbed by the player, they will never be more than 3
     */
    private ArrayList<PowerUp> powerUps;
    /**
     * Is the number of weapons actually carried by the player
     */
    private int numberOfWeapons;
    /**
     * This boolean is true if the player died during the last round and needs to respawn
     */
    private boolean dead;
    /**
     * Is the number of times that the player was damaged, this could implicate that the player is dead
     */
    private int timesGetDamaged;

    /**
     * Costructor: set the preferences of the player (username, character) and give him one ammoCube for each colour
     *
     * @param username  player's username
     * @param character is the character chosen by the player
     */
    public Player(String username, Character character) {
        this.username = username;
        this.character = character;
        this.playerBoard = new PlayerBoard(character);
        this.points = 0;
        this.numberOfWeapons = 0;
        this.powerUps = new ArrayList<>();
        this.ammo = new ArrayList<>();
        this.firstPlayer = false;
        this.dead = false;
        timesGetDamaged = 0;
        addAmmo(new AmmoCube(CubeColour.Red));
        addAmmo(new AmmoCube(CubeColour.Blue));
        addAmmo(new AmmoCube(CubeColour.Yellow));
    }

    /**
     * Getter method:
     *
     * @return the playerBoard
     */
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Getter method:
     *
     * @return the chosen Character
     */
    public Character getCharacter() {
        return character;
    }


    /**
     * Getter method:
     *
     * @return the ArrayList that contains the player's actual powerUps
     */
    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }


    /**
     * Getter method:
     *
     * @return the player's position
     */
    public Square getPosition() {
        return position;
    }

    /**
     * Getter method:
     *
     * @return the points earned by the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * Getter method:
     *
     * @return the player's username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Getter method:
     *
     * @return an ArrayList that contains the player's actual ammo
     */
    public ArrayList<AmmoCube> getAmmo() {
        return ammo;
    }


    /**
     * Getter method:
     *
     * @return the array that contains the player's actual weapon
     */
    public Weapon[] getWeapons() {
        return weapons;
    }

    /**
     * Getter method:
     *
     * @return the timesGetDamaged attribute actual value
     */
    public int getTimesGetDamaged() {
        return timesGetDamaged;
    }

    /**
     * Getter method:
     *
     * @return the dead attribute value
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * This method increments one time the timesGetDamaged attribute's value
     */
    public void addTimesGetDamaged() {
        timesGetDamaged++;
    }

    /**
     * This method reset to 0 the value of timesGetDamaged
     */
    public void resetTimesGetDamaged() {
        timesGetDamaged = 0;
    }

    /**
     * This method decrements one time the timesGetDamaged attribute's value
     */
    public void removeOneTimesGetDamaged() {
        timesGetDamaged--;
    }

    /**
     * Getter method:
     *
     * @return firstPlayer value
     */
    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Setter method: set firstPlayer value to true
     */
    public void setFirstPlayer() {
        firstPlayer = true;
    }

    /**
     * Setter method: set the player's position to the given Square
     *
     * @param position is the new position
     */
    public void setPosition(Square position) {
        if (this.position != null)
            this.position.removeCurrentPlayer(this);
        this.position = position;
        this.position.addCurrentPlayer(this);
        PlayerPositionUpdateEvent message = new PlayerPositionUpdateEvent(character, position.getColumn(), position.getRow());
        setChanged();
        notifyObservers(message);
    }

    /**
     * add the ammo into the player reserve and notify the change
     *
     * @param ammo is the ammo picked
     */
    public void addAmmo(AmmoCube ammo) {
        if (getCubeColourNumber(ammo.getColour()) < 3)
            this.ammo.add(ammo);
        AmmoUpdateEvent message = new AmmoUpdateEvent(character, this.ammo);
        setChanged();
        notifyObservers(message);
    }

    /**
     * Remove the given AmmoCube from the player ammo reserve anf notify the change
     *
     * @param ammoCube is the cube that must be removed
     */
    public void discardAmmo(AmmoCube ammoCube) {
        for (AmmoCube a : this.ammo) {
            if (ammoCube.getColour() == a.getColour()) {
                this.ammo.remove(a);
                break;
            }
        }
        setChanged();
        notifyObservers(new AmmoUpdateEvent(character, ammo));
    }

    /**
     * add the powerUp into the player reserve and notify the change
     *
     * @param powerUp, is the picked powerUp
     */
    public void addPowerUp(PowerUp powerUp) {
        if (this.powerUps.size() < 3) {
            powerUp.setOwner(this);
            this.powerUps.add(powerUp);
            setChanged();
            notifyPowerUpChange();
        }
    }

    /**
     * This method, during the first respawn, adds a second PowerUp following the game rules, and notifies the change
     *
     * @param powerUp is the drawn PowerUp
     */
    public void addSpawnPowerUp(PowerUp powerUp) {
        if (this.powerUps.size() < 4) {
            powerUp.setOwner(this);
            this.powerUps.add(powerUp);
            setChanged();
            notifyPowerUpChange();
        }
    }

    /**
     * This method add the given method to the player reserve, notifying the change
     *
     * @param weapon is the added weapon
     */
    public void addWeapon(Weapon weapon) {
        weapons[numberOfWeapons] = weapon;
        weapon.setOwner(this);
        numberOfWeapons++;

        notifyWeaponsChange();
    }

    /**
     * This method notify the VirtualViews that observe this class of a PowerUpUpdateEvent
     */
    private void notifyPowerUpChange() {
        PlayerPowerUpUpdateEvent message = new PlayerPowerUpUpdateEvent(character, Encoder.encodePowerUpsType(powerUps), Encoder.encodePowerUpColour(powerUps));
        setChanged();
        notifyObservers(message);
    }

    /**
     * This method notify the VirtualViews that observe this class of a PlayerWeaponUpdateEvent
     */
    public void notifyWeaponsChange() {
        String[] messageWeapons = new String[numberOfWeapons];
        for (int i = 0; i < numberOfWeapons; i++) {
            messageWeapons[i] = weapons[i].getName();
        }

        PlayerWeaponUpdateEvent message = new PlayerWeaponUpdateEvent(character, messageWeapons, getLoadedWeapons());
        setChanged();
        notifyObservers(message);
    }

    /**
     * Scan the weapons carried and find which of them are loaded
     *
     * @return a boolean array, each element is true if the respectively weapon carried is loaded
     */
    public boolean[] getLoadedWeapons() {
        boolean[] loadedWeapons = new boolean[numberOfWeapons];
        for (int i = 0; i < numberOfWeapons; i++) {
            loadedWeapons[i] = weapons[i].isLoaded();
        }
        return loadedWeapons;
    }


    /**
     * return the number of cubes, owned by the player, with the same colour as the one passed as parameter
     *
     * @param colour is the color of the searched cubes
     * @return amount is the number of cubes of the given color, carried by the player
     */
    public int getCubeColourNumber(CubeColour colour) {
        Iterator iterator = ammo.iterator();
        AmmoCube cube;
        int amount = 0;

        while (iterator.hasNext()) {
            cube = (AmmoCube) iterator.next();

            if (cube.getColour() == colour) {
                amount++;
            }
        }
        return amount;
    }


    /**
     * This method handle a powerUp discard and notifies the change
     *
     * @param powerUp is discarded powerUp
     */
    public void discardPowerUp(PowerUp powerUp) {
        this.powerUps.remove(powerUp);
        powerUp.setOwner(null);
        notifyPowerUpChange();
    }

    public int getNumberOfWeapons() {
        return numberOfWeapons;
    }


    /**
     * Check if the player could afford a payment that derives from a request
     *
     * @param cost is a list of AmmoCubes, that represents the cost
     * @return true if the player can affords the cost, paying with both ammo and powerUp
     */
    public boolean canAffortCost(AmmoCube[] cost) {
        int blueCubes = 0;
        int yellowCubes = 0;
        int redCubes = 0;

        for (AmmoCube cube : cost) {
            switch (cube.getColour()) {
                case Blue:
                    blueCubes++;
                    break;
                case Red:
                    redCubes++;
                    break;
                case Yellow:
                    yellowCubes++;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        blueCubes -= getCubeColourNumber(CubeColour.Blue);
        yellowCubes -= getCubeColourNumber(CubeColour.Yellow);
        redCubes -= getCubeColourNumber(CubeColour.Red);

        for (PowerUp p : powerUps) {
            switch (p.getColour()) {
                case Yellow:
                    yellowCubes--;
                    break;
                case Red:
                    redCubes--;
                    break;
                case Blue:
                    blueCubes--;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return blueCubes <= 0 && yellowCubes <= 0 && redCubes <= 0;
    }

    /**
     * This method handle the discard of a card and notifies the change;
     * it must be invoked only if the player has already drawn his 4th weapon and now need to discard one;
     * is supposed that the player's position coincides with a SpawnSquare because he grabs from there and for what is said before
     *
     * @param weapon is the discarded weapon
     */
    public void discardWeapon(Weapon weapon) {
        int i = 0;
        while (i < numberOfWeapons + 1 && weapons[i] != weapon) {
            i++;
        }
        ((SpawnSquare) position).getWeapons().add(weapons[i]);
        weapon.setLoaded();
        weapons[i].setOwner(null);
        weapons[i] = null;
        numberOfWeapons--;
        if (i < numberOfWeapons) {
            weapons[i] = weapons[numberOfWeapons];
            weapons[numberOfWeapons] = null;
        }
        notifyWeaponsChange();
    }

    /**
     * Add the given number of points to the player's points actual value
     *
     * @param pointsToAdd is the number of points that will be added
     */
    public void addPoints(int pointsToAdd) {
        points += pointsToAdd;
    }


    /**
     * This method invert the dead attribute value
     */
    public void invertDeathState() {
        dead = !dead;
    }

    /**
     * Search in the player's reserve for each of the PowerUp that has its Usability value as WHILE_ACTION
     *
     * @return an ArrayList that contains all of the PowerUps that satisfy the precedent condition
     */
    public ArrayList<PowerUp> getWhileActionPowerUp() {
        ArrayList<PowerUp> whileActionPowerUps = new ArrayList<>();
        for (PowerUp p : this.powerUps) {
            if (p.whenToUse() == PowerUp.Usability.WHILE_ACTION)
                whileActionPowerUps.add(p);
        }
        return whileActionPowerUps;
    }


    /**
     * Set the dead attribute value to true
     */
    public void setDead() {
        dead = true;
    }
}
