package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.board.Square;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Federico Innocente
 *
 * class to manage the players
 */
public class Player {

    private static final int MAX_WEAPONS = 3;

    private final String username;
    private final Character character;
    private final String battleCry;
    private PlayerBoard playerBoard;
    private int points;
    private Square position;
    private ArrayList<AmmoCube> ammo;
    private boolean firstPlayer;
    private Weapon[] weapons;
    private ArrayList<PowerUp> powerUps;
    private int numberOfWeapons;

    /**
     *
     * @param username player's username
     * @param character is the character choosen by the player
     * @param battleCry is the battle cry
     *
     * costructor of player, set the preferences of the player (username, character, battleCry) and give him one ammoCube for each colour
     */
    public Player(String username, Character character, String battleCry)
    {
        this.username = username;
        this.character = character;
        this.battleCry = battleCry;
        this.playerBoard = new PlayerBoard();
        this.points = 0;
        this.numberOfWeapons = 0;
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
     * @return battleCry
     */
    public String getBattleCry()
    {
        return battleCry;
    }


    /**
     *
     * @return ammo list
     */
    public ArrayList<AmmoCube> getAmmo()
    {
        return ammo;
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

    /**
     *
     * @param position is the new position
     */
    public void setPosition(Square position)
    {
        this.position = position;
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

}
