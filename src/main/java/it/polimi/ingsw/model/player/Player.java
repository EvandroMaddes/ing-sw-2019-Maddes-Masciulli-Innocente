package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.game_components.AmmoCube;
import it.polimi.ingsw.model.game_components.PowerUp;
import it.polimi.ingsw.model.game_components.Weapon;
import it.polimi.ingsw.model.board.Square;

import java.util.ArrayList;

public class Player {

    private String username;
    private Character character;
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

    }

    public void setUsername(String username)
    {

    }

}
