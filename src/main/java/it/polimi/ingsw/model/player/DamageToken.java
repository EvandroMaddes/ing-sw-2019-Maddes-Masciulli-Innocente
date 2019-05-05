package it.polimi.ingsw.model.player;

import java.io.Serializable;

public class DamageToken implements Serializable {

    private final Character character;


    public DamageToken(Player player)
    {
        character = player.getCharacter();
    }

    public Character getCharacter()
    {
        return character;
    }
}
