package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Direction;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
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

    public String getName(){ return this.name; }

    public void setName(String name) {
        this.name = name;
    }

    public CubeColour getColour()
    {
       return this.colour;
    }



}
