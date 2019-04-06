package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.board.Square;

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

    public void addPowerUp(PowerUp powerUp)
    {

    }

    public Square getPosition()
    {
        Square currentSquare = null;
        return currentSquare;
    }

    public void setUsername(String username)
    {

    }

}
