package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Evandro Maddes
 *
 *
 *
 */

public class DominationSpawnSquare extends SpawnSquare {

    /**
     *Constructor of a single square
     * @param row
     * @param column
     * @param colour
     */
    public DominationSpawnSquare(int row, int column,Square north,boolean reachableNorth, Square south,boolean reachableSouth, Square east,boolean reachableEast, Square west, boolean reachableWest, String colour){
        super( row,  column, north, reachableNorth,  south, reachableSouth,  east, reachableEast,  west,  reachableWest,  colour);
    }



    /**
     * @author Francesco Masciulli
     * at the end of every round it damage all the player on this square whit one hit
     * @param targets list of players on this square, visited with an Iterator
     */

    public void damage(ArrayList<Player> targets) {

        Iterator iterator = targets.iterator();
        Player target;
        while (iterator.hasNext())
        {
            target = (Player)iterator.next();
            target.getPlayerBoard().addDamages(target, 1);
        }


    }

}
