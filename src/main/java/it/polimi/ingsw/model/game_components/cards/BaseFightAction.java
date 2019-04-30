package it.polimi.ingsw.model.game_components.cards;


import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public interface BaseFightAction {


    public void damage(ArrayList<Player> target, int amount, Player damager);
    public void move(Player target, int direction);
    public void mark(ArrayList<Player> target, int amount, Player marker);

}
