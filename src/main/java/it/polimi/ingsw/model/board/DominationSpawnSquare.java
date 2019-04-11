package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;

import java.util.ArrayList;

/**
 * @Evandro Maddes
 * todo rivedi metodo damage
 */

public class DominationSpawnSquare extends SpawnSquare {

    /**NON è PIù COMODO NON PASSARE N ESSUN PARAMETRO E CHIAMARE IL METODO GETsQUAREpLAYER SU THIS(?)
     * at the end of every round it damage all the player on this square whit one hit
     * @param target list of players on this square
     */
    public void damage(ArrayList<Player> target) {

                target.get(i).receiveDamege();


    }

}
