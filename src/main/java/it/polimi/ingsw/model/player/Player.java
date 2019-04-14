package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.board.Square;

import javax.swing.text.Position;
import java.util.ArrayList;

public class Player {

    private String username;
    private Character character;
    private String battleCry;
    private PlayerBoard playerBoard;
    private int points;
    private Square position;
    private ArrayList<AmmoCube> ammo;
    private boolean firstPlayer;
    private Weapon[] weapons;
    private ArrayList<PowerUp> powerUps;


    public void addAmmo(AmmoCube ammo)
    {

    }

    public Character getCharacter()
    {
        return character;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void addPowerUp(PowerUp powerUp)
    {

    }

    public Square getPosition()
    {
        return position;
    }

    public void setUsername(String username)
    {

    }

    public int getCubeColourNumber(CubeColour colour)
    {   int cubeColourNumber=0;
        int i=0;
            while(i<ammo.size()){
                if (ammo.get(i).getColour()==colour)
                {
                    cubeColourNumber++;
                }
            }

        return(cubeColourNumber);
    }

    /**
     * when player is on a spawn square in domination mode
     */
    public void receiveDamege()
    {
        this.playerBoard.addDamages(this,1);
    }

}
