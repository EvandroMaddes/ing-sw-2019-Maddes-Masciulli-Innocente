package it.polimi.ingsw.model.player;

import java.io.Serializable;

public class DamageToken implements Serializable {

    private final Player player;


    public DamageToken(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }
}
