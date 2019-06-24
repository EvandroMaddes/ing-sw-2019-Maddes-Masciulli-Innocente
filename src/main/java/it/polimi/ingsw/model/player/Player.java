package it.polimi.ingsw.model.player;

import it.polimi.ingsw.event.model_view_event.AmmoUpdateEvent;
import it.polimi.ingsw.event.model_view_event.PlayerPowerUpUpdateEvent;
import it.polimi.ingsw.event.model_view_event.PlayerWeaponUpdateEvent;
import it.polimi.ingsw.event.model_view_event.PlayerPositionUpdateEvent;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.*;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.utils.Encoder;

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
    private int timesGetDamaged;

    /**
     *
     * @param username player's username
     * @param character is the character choosen by the player
     *
     * costructor of player, set the preferences of the player (username, character) and give him one ammoCube for each colour
     */
    public Player(String username, Character character)
    {
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

    public PlayerBoard getPlayerBoard()
    {
        return playerBoard;
    }

    public Character getCharacter()
    {
        return character;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public Square getPosition()
    {
        return position;
    }

    public int getPoints()
    {
        return points;
    }

    public String getUsername()
    {
        return username;
    }

    public ArrayList<AmmoCube> getAmmo()
    {
        return ammo;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    public void addTimesGetDamaged(){
        timesGetDamaged++;
    }

    public void resetTimesGetDamaged(){
        timesGetDamaged = 0;
    }

    public void removeOneTimesGetDamaged(){
        timesGetDamaged--;
    }

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
        if (this.position != null)
            this.position.removeCurrentPlayer(this);
        this.position = position;
        position.addCurrentPlayer(this);
        PlayerPositionUpdateEvent message = new PlayerPositionUpdateEvent(character, position.getRow(), position.getColumn());
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
        if(getCubeColourNumber(ammo.getColour()) < 3)
            this.ammo.add(ammo);
        AmmoUpdateEvent message = new AmmoUpdateEvent(character, this.ammo );
        notifyObservers(message);
    }

    public void discardAmmo(AmmoCube ammoCube){
        for (AmmoCube a: this.ammo) {
            if (ammoCube.getColour() == a.getColour()) {
                this.ammo.remove(a);
                break;
            }
        }
        notifyObservers(new AmmoUpdateEvent(character, ammo));
    }

    /**
     *
     * @param powerUp, is the powerUp picked
     *
     * add the powerUp into the player reserve
     */
    public void addPowerUp(PowerUp powerUp)
    {
        powerUp.setOwner(this);
        if (this.powerUps.size() < 3)
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
        PlayerPowerUpUpdateEvent message = new PlayerPowerUpUpdateEvent(character, Encoder.encodePowerUpsType(powerUps), Encoder.encodePowerUpColour(powerUps));
        notifyObservers(message);
    }

    private void notifyWeaponsChange(){
        String[] messageWeapons = new String[numberOfWeapons];
        for (int i = 0; i < numberOfWeapons; i++){
            messageWeapons[i] = weapons[i].getName();
        }

        PlayerWeaponUpdateEvent message = new PlayerWeaponUpdateEvent(messageWeapons, character);
        notifyObservers(message);
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
        while (i < numberOfWeapons + 1 && weapons[i] != weapon){
            i++;
        }
        ((SpawnSquare)position).getWeapons().add(weapons[i]);
        weapons[i].setOwner(null);
        weapons[i] = null;
        numberOfWeapons--;
        if (i < numberOfWeapons){
            weapons[i] = weapons[numberOfWeapons];
            weapons[numberOfWeapons] = null;
        }
        notifyWeaponsChange();
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

    public ArrayList<PowerUp> getWhileActionPowerUp(){
        ArrayList<PowerUp> whileActionPowerUps = new ArrayList<>();
        for (PowerUp p: this.powerUps) {
            if (p.whenToUse() == PowerUp.Usability.WHILE_ACTION)
                whileActionPowerUps.add(p);
        }
        return whileActionPowerUps;
    }

    public int getTimesGetDamaged() {
        return timesGetDamaged;
    }

    public void setDead(){
        dead = true;
    }
}
