package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;



public class CLIDisplay {


    private static final int COLUMN = 150;
    private static final int ROW =72;
    private CLIMap map;
    private CLIGameTrack gameTrack;
    private ArrayList<CLIPlayerBoard> playerBoard = new ArrayList<CLIPlayerBoard>();
    private String[][] display = new String[COLUMN][ROW];

    public CLIDisplay(){
        for(int i=0; i<ROW;i++) {//ROW
            for (int j = 0; j < COLUMN; j++) {//COLUMN
                display[j][i] = Color.ANSI_BLACK_BACKGROUND.escape()+" ";
            }
        }
    }

    public void createDisplay(){
        int size = playerBoard.size();

        //GAMATRACK
        for(int i=0; i<3;i++) {//ROW
            for (int j = 0; j < 32; j++) {//COLUMN
                display[j][i] = gameTrack.getTrack()[j][i];
            }
        }
            //MAP
           for(int i=3; i<20;i++) {//ROW
               for (int j = 3; j < 45; j++) {//COLUMN
                   display[j][i] = map.getMap()[j-3][i-3];
               }
           }
            //PLAYERBOARD
        for (int i=0;i<size;i++) {

            putPlayerBoard(i*10+21,2,playerBoard.get(i));

        }


        display[60][3]=Color.ANSI_GREEN.escape()+"S";
        display[61][3]=Color.ANSI_GREEN.escape()+"P";
        display[62][3]=Color.ANSI_GREEN.escape()+"W";
        display[63][3]=Color.ANSI_GREEN.escape()+"A";
        display[64][3]=Color.ANSI_GREEN.escape()+"N";

        display[66][3]=Color.ANSI_GREEN.escape()+"S";
        display[67][3]=Color.ANSI_GREEN.escape()+"Q";
        display[68][3]=Color.ANSI_GREEN.escape()+"U";
        display[69][3]=Color.ANSI_GREEN.escape()+"A";
        display[70][3]=Color.ANSI_GREEN.escape()+"R";
        display[71][3]=Color.ANSI_GREEN.escape()+"E";
        display[72][3]=Color.ANSI_GREEN.escape()+"=";

        display[69][5]=Color.ANSI_RED.escape()+"R";
        display[70][5]=Color.ANSI_RED.escape()+"E";
        display[71][5]=Color.ANSI_RED.escape()+"D";
        display[72][5]=Color.ANSI_RED.escape()+":";


        display[69][7]=Color.ANSI_BLUE.escape()+"B";
        display[70][7]=Color.ANSI_BLUE.escape()+"L";
        display[71][7]=Color.ANSI_BLUE.escape()+"U";
        display[72][7]=Color.ANSI_BLUE.escape()+":";

        display[66][9]=Color.ANSI_YELLOW.escape()+"Y";
        display[67][9]=Color.ANSI_YELLOW.escape()+"E";
        display[68][9]=Color.ANSI_YELLOW.escape()+"L";
        display[69][9]=Color.ANSI_YELLOW.escape()+"L";
        display[70][9]=Color.ANSI_YELLOW.escape()+"O";
        display[71][9]=Color.ANSI_YELLOW.escape()+"W";
        display[72][9]=Color.ANSI_YELLOW.escape()+":";


    }

    /**
     * Add playerBoard on then display
     * @param row
     * @param column
     * @param playerBoard
     */
    private void putPlayerBoard(int row, int column, CLIPlayerBoard playerBoard) {
        for (int i =row; i < 10+row; i++) {//ROW
            for (int j = column; j < 57+column; j++) {//COLUMN
                display[j][i] = playerBoard.getPlayerBoard()[j - column][i -row];
            }
        }
    }

    public CLIMap getMap() {
        return map;
    }

    public CLIGameTrack getGameTrack() {
        return gameTrack;
    }


    /**
     * find the playerBoard of passed character
     * @param currCharacter
     * @return
     */
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

    /**
     * add one by one player to array
     * @param newGameTrack
     */
    public void setGameTrack(CLIGameTrack newGameTrack){
    this.gameTrack = newGameTrack;
    }

    public void setMap(CLIMap map) {
        this.map = map;
    }

    /**
     * add one by one player to array
     * @param newPlayerBoard
     */
    public void setPlayerBoard(CLIPlayerBoard newPlayerBoard) {

        this.playerBoard.add(newPlayerBoard);
    }

    /**
     * Set available weapon on spawn square
     * @param x
     * @param y
     * @param weapons
     */
    public void weaponsSpawnSquare(int x, int y, String[] weapons){
        int h=0;
        int j=0;

                //RED
        if (x==0 && y==1){
            j=3;
        }else //BLUE
            if (x==2 && y==0){
            j=5;
        }else //YELLOW
            if( x==3 && y==2){
            j=7;
        }

        for (int i =73; i<76; i++ ) {
            if(h<weapons.length && weapons[h]!=null) {
                display[j][i] = weapons[h];
                h++;
            }
        }
    }



    public void printDisplay(){
        for(int i=0; i<ROW;i++) {//ROW
            System.out.println("");
            for (int j = 0; j < COLUMN; j++) {//COLUMN
                System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+display[j][i]);
            }
        }

    }


}
