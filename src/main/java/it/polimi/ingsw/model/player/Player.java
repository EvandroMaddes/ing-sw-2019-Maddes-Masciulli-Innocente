package it.polimi.ingsw.model.player;

import it.polimi.ingsw.event.model_view_event.AmmoUpdateEvent;
import it.polimi.ingsw.event.model_view_event.PlayerPowerUpUpdateEvent;
import it.polimi.ingsw.event.model_view_event.PlayerWeaponUpdateEvent;
import it.polimi.ingsw.event.model_view_event.PositionUpdateEvent;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.*;
import it.polimi.ingsw.model.board.Square;

import java.util.*;

/**
 * @author Federico Innocente
 *
 * class to manage the players
 */
public class Player extends Observable {

    private static final int MAX_WEAPONS = 3;

    private final String username;
    private final Character character;
    private PlayerBoard playerBoard;
    private int points;
    private Square position;
    private ArrayList<AmmoCube> ammo;
    private boolean firstPlayer;
    private Weapon[] weapons = new Weapon[MAX_WEAPONS + 1];
    private ArrayList<PowerUp> powerUps;
    private int numberOfWeapons;
    private boolean dead;

    /**
     *
     * @param username player's username
     * @param character is the character choosen by the player
     *
     * costructor of player, set the preferences of the player (username, character, battleCry) and give him one ammoCube for each colour
     */
    public Player(String username, Character character)
    {
        this.username = username;
        this.character = character;
        this.playerBoard = new PlayerBoard();
        this.points = 0;
        this.numberOfWeapons = 0;
        this.powerUps = new ArrayList<>();
        this.ammo = new ArrayList<>();
        this.firstPlayer = false;
        this.dead = true;
        addAmmo(new AmmoCube(CubeColour.Red));
        addAmmo(new AmmoCube(CubeColour.Blue));
        addAmmo(new AmmoCube(CubeColour.Yellow));
    }


    /**
     *
     * @return the player board
     */
    public PlayerBoard getPlayerBoard()
    {
        return playerBoard;
    }

    /**
     *
     * @return the charecter that the player is using
     */
    public Character getCharacter()
    {
        return character;
    }

    /**
     *
     * @return player's power ups
     */
    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    /**
     *
     * @return position
     */
    public Square getPosition()
    {
        return position;
    }

    /**
     *
     * @return player's points
     */
    public int getPoints()
    {
        return points;
    }

    /**
     *
     * @return username
     */
    public String getUsername()
    {
        return username;
    }


    /**
     *
     * @return ammo list
     */
    public ArrayList<AmmoCube> getAmmo()
    {
        return ammo;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    /**
     *
     * @param weapon is the weapon searched
     * @return the index of the weapon in the inventory, -1 if the player haven't it
     */
    public int getWeaponIndex(Weapon weapon)
    {
        for(int i = 0; i < weapons.length; i++)
        {
            if (weapons[i] == weapon)
                return i;
        }
        return -1;
    }

    /**
     *
     * @return firstPlayer , true or false
     */
    public boolean isFirstPlayer()
    {
        return firstPlayer;
    }

    public void setFirstPlayer(){
        firstPlayer = true;
    }

    /**
     *
     * @param position is the new position
     */
    public void setPosition(Square position)
    {
        this.position = position;
        PositionUpdateEvent message = new PositionUpdateEvent( username, position.getRow(), position.getColumn());
        notifyObservers(message);
    }

    /**
     *
     * @param ammo is the ammo picked
     *
     * add the ammo into the player reserve
     */
    public void addAmmo(AmmoCube ammo)
    {
        this.ammo.add(ammo);
        AmmoUpdateEvent message = new AmmoUpdateEvent(username, this.ammo );
        notifyObservers(message);
    }

    /**
     *
     * @param powerUp, is the powerUp picked
     *
     * add the powerUp into the player reserve
     */
    public void addPowerUp(PowerUp powerUp)
    {
        this.powerUps.add(powerUp);
        notifyPowerUpChange();
    }

    /**
     *
     * @param weapon
     */
    public void addWeapon(Weapon weapon)
    {
        weapons[numberOfWeapons] = weapon;
        weapon.setOwner(this);
        numberOfWeapons++;

        notifyWeaponsChange();
    }

    private void notifyPowerUpChange(){
        Map<String, CubeColour> messagePowerUps = new HashMap<>();
        for (PowerUp p: this.powerUps) {
            if (p.getName().equals("Newton"))
                messagePowerUps.put("Newton", p.getColour());
            else if (p.getName().equals("Teleporter"))
                messagePowerUps.put("Teleporter", p.getColour());
            else if (p.getName().equals("TargetingScope"))
                messagePowerUps.put("TargetingScope", p.getColour());
            else
                messagePowerUps.put("TagbackGranade", p.getColour());
        }

        PlayerPowerUpUpdateEvent message = new PlayerPowerUpUpdateEvent(username,messagePowerUps);
        notifyObservers(message);
    }

    private void notifyWeaponsChange(){
        String[] messageWeapons = new String[numberOfWeapons];
        for (int i = 0; i < numberOfWeapons; i++){
            messageWeapons[i] = weapons[i].getName();
        }

        PlayerWeaponUpdateEvent message = new PlayerWeaponUpdateEvent(username, messageWeapons);
        notifyObservers(message);
    }


    /**
     *
     * @param weapon is the weapon dropped
     * @param spawnSquare is the spawn square in which the weapon is dropped
     */
    public void removeWeapon(Weapon weapon, SpawnSquare spawnSquare)
    {
        for (int i = 0; i < MAX_WEAPONS; i++)
        {
            if (weapons[i] == weapon)
            {
                weapons[i] = null;
            }
        }
        numberOfWeapons--;
        weapon.setOwner(null);
        if ( !weapon.isLoaded() )
        {
            weapon.invertLoadedState();
        }

        notifyWeaponsChange();
    }


    /**
     *
     * @param colour , is the colour of the cube that i want to know the number of
     * @return amount , is the number of cube of that colour that the player has
     *
     * return the number of the cube of the colour passed as paramether owned by the player
     */
    public int getCubeColourNumber(CubeColour colour)
    {
        Iterator iterator = ammo.iterator();
        AmmoCube cube;
        int amount = 0;

        while (iterator.hasNext())
        {
            cube = (AmmoCube)iterator.next();

            if( cube.getColour() == colour )
            {
                amount++;
            }
        }
        return amount;
    }


    /**
     * todo va passata al deck per reshuffle()
     * @param powerUp is discarded powerUp
     */
    public void discardPowerUp(PowerUp powerUp)
    {
        powerUps.remove(powerUp);
        powerUp.setOwner(null);
        notifyPowerUpChange();
    }

    public int getNumberOfWeapons() {
        return numberOfWeapons;
    }


    /**
     *
     * @param cost is a list of AmmoCubes, that rappresents the cost of something
     * @return true if the player can affort the cost, with both ammo and powerUp
     */
    public boolean canAffortCost(AmmoCube[] cost){
        int blueCubes = 0;
        int yellowCubes = 0;
        int redCubes = 0;

        for (AmmoCube cube: cost) {
            switch (cube.getColour()){
                case Blue: {
                    blueCubes++;
                    break;
                }
                case Red: {
                    redCubes++;
                    break;
                }
                case Yellow:{
                    yellowCubes++;
                    break;
                }
            }
        }
        blueCubes -= getCubeColourNumber(CubeColour.Blue);
        yellowCubes -= getCubeColourNumber(CubeColour.Yellow);
        redCubes -= getCubeColourNumber(CubeColour.Red);

        for (PowerUp p: powerUps) {
            switch (p.getColour()){
                case Yellow:{
                    yellowCubes--;
                    break;
                }
                case Red:{
                    redCubes--;
                    break;
                }
                case Blue:{
                    blueCubes--;
                    break;
                }
            }
        }
        return blueCubes <= 0 && yellowCubes <= 0 && redCubes <= 0;
    }

    /**
     * Discard a card. This method should be invocate only if the player has altready draw his 4th weapon and now need to discard one
     * The player is supposed to stand on a SpawnSquare, because he can only discard there
     * @param weapon is the discarded weapon
     */
    public void discardWeapon(Weapon weapon){
        int i = 0;
        while (i < MAX_WEAPONS + 1 && weapons[i] != weapon){
            i++;
        }
        ((SpawnSquare)position).getWeapons().add(weapons[i]);
        weapons[i].setOwner(null);
        numberOfWeapons--;
        if (i < MAX_WEAPONS){
            weapons[i] = weapons[MAX_WEAPONS];
        }
    }

    public void addPoints(int pointsToAdd){
        points += pointsToAdd;
    }

    public boolean isDead() {
        return dead;
    }

    public void invertDeathState(){
        dead = !dead;
    }
}
