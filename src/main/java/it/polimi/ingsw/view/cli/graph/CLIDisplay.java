package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

public class CLIDisplay {


    private static final int COLUMN = 100;
    private static final int ROW = 100;
    private CLIMap map;
    private CLIGameTrack gameTrack;
    private ArrayList<CLIPlayerBoard> playerBoard;
    private String[][] display = new String[COLUMN][ROW];


    public void createDisplay(){
        //TODO ciclo for dove si copia la mappa

        //TODO ciclo for dove si copia la gameTrack

        //TODO ciclo for dove si copiano le playerBoard
    }

    public CLIMap getMap() {
        return map;
    }

    public CLIGameTrack getGameTrack() {
        return gameTrack;
    }

    public CLIPlayerBoard getPlayerBoard(Character currCharacter) {
        CLIPlayerBoard found = null;

        for (CLIPlayerBoard currPlayerBoard: playerBoard
             ) {
            if (currPlayerBoard.getCharacter().equals(currCharacter)){
                found = currPlayerBoard;
            }
        }
        return found;
    }

}
