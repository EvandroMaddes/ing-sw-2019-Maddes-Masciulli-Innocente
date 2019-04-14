package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;

import java.util.ArrayList;

/**
 * @Evandro Maddes
 * todo rivedi metodo damage
 * @Francesco Masciulli
 * commentata la chiamata alla riga 27 per compilare
 */

public class DominationSpawnSquare extends SpawnSquare {

    /**NON è PIù COMODO NON PASSARE NESSUN PARAMETRO E CHIAMARE IL METODO GETsQUAREpLAYER SU THIS(?)
     * at the end of every round it damage all the player on this square whit one hit
     * @param target list of players on this square
     */
    public void damage(ArrayList<Player> target) {
        int i=0;

        while(i<target.size())
        {
           // target.get(i).receiveDamege();
        }

    }

}
