package it.polimi.ingsw.model.gamecomponents.cards;


import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.player.Player;

public interface BaseFightAction {


    public void damage(Player target, int amount);
    public void move(Player target, Square destination);
    public void mark(Player target, int amount);

}
