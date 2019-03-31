package it.polimi.ingsw.model;


import java.util.ArrayList;

public interface BaseFightAction {


    public void damage(ArrayList<Player> target, int amount);
    public void move(Player target, Direction direction);
    public void mark(ArrayList<Player> target, int amount);

}
