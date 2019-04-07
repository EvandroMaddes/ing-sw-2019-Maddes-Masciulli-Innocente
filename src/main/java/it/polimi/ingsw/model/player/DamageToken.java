package it.polimi.ingsw.model.player;

public class DamageToken {

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
