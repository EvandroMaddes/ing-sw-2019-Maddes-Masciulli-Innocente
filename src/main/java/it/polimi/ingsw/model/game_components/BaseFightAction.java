package it.polimi.ingsw.model.game_components;


import it.polimi.ingsw.model.board.Direction;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public interface BaseFightAction {


    public void damage(ArrayList<Player> target, int amount);
    public void move(Player target, Direction direction);
    public void mark(ArrayList<Player> target, int amount);

}
