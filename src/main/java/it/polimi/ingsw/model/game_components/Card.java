package it.polimi.ingsw.model.game_components;

import it.polimi.ingsw.model.board.Direction;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public abstract class Card implements BaseFightAction {

    private CubeColour colour;
    private String name;

    //Player damage the targets with the amount number of token
    @Override
    public void damage(ArrayList<Player> target, int amount) {

    }
    //Player mark the targets with the amount number of token
    @Override
    public void mark(ArrayList<Player> target, int amount) {

    }
    //Player move himself from his position to a near and accessible Square
    @Override
    public void move(Player target, Direction direction) {

    }

    public CubeColour getColour()
    {
        CubeColour i=null;
        return i;
    }



}
