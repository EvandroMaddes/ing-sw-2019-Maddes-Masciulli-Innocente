package it.polimi.ingsw.model.game_components.cards;


import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public interface BaseFightAction {


    public void damage(Player target, int amount);
    public void move(Player target, int direction);
    public void mark(Player target, int amount);

}
