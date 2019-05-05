package it.polimi.ingsw.model.player;

public class DamageToken {

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
