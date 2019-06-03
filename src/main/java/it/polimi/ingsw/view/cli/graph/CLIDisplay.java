package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

public class CLIDisplay {


    private static final int COLUMN = 210;
    private static final int ROW = 100;
    private CLIMap map;
    private CLIGameTrack gameTrack;
    private ArrayList<CLIPlayerBoard> playerBoard;
    private String[][] display = new String[COLUMN][ROW];


    public void createDisplay(){

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


    public void weaponsSpawnSquare(int x, int y,String[] weapons){
        int h=0;
        int j=0;

        display[2][20]="SPAWN SQUARE RED HAS:";
        display[4][20]="SPAWN SQUARE BLU HAS:";
        display[8][20]="SPAWN SQUARE YELLOW HAS:";

        if (x==0 && y==1){
            j=2;
        }else if (x==2 && y==0){
            j=4;
        }else if( x==3 && y==2){
            j=8;
        }

        for (int i =21; i<24; i++ ) {
            if(h<weapons.length && weapons[h]!=null) {
                display[j][i] = weapons[h];
                h++;
            }
        }
    }

    
}
