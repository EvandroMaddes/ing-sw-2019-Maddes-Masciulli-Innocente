package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Encoder {

    public static ArrayList<Character> encodePlayerTargets(List<Player> targets){
        ArrayList<Character> targetsLite = new ArrayList<>();
        for (Player p: targets) {
            targetsLite.add(p.getCharacter());
        }
        return targetsLite;
    }

    public static int[] encodeSquareTargetsX(List<Square> targets){
        int[] targetsX = new int[targets.size()];
        for(int i = 0 ; i < targets.size(); i++){
            targetsX[i] = targets.get(i).getColumn();
        }
        return targetsX;
    }

    public static int[] encodeSquareTargetsY(List<Square> targets){
        int[] targetsY = new int[targets.size()];
        for(int i = 0 ; i < targets.size(); i++){
            targetsY[i] = targets.get(i).getRow();
        }
        return targetsY;
    }
}
