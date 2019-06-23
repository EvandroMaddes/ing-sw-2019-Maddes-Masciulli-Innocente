package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;



public class CLIDisplay {


    private static final int COLUMN = 150;
    private static final int ROW =77;
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

        //GAMETRACK
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
        if(!playerBoard.isEmpty()) {
            for (int i = 0; i < size; i++) {

                putPlayerBoard(i * 10 + 21, 2, playerBoard.get(i));

            }
        }

        display[60][3]=Color.ANSI_GREEN.escape()+"S";
        display[61][3]=Color.ANSI_GREEN.escape()+"P";
        display[62][3]=Color.ANSI_GREEN.escape()+"A";
        display[63][3]=Color.ANSI_GREEN.escape()+"W";
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
        display[72][5]=Color.ANSI_RED.escape()+": ";
        display[73][5]=Color.ANSI_GREEN.escape()+"*";



        display[69][7]=Color.ANSI_BLUE.escape()+"B";
        display[70][7]=Color.ANSI_BLUE.escape()+"L";
        display[71][7]=Color.ANSI_BLUE.escape()+"U";
        display[72][7]=Color.ANSI_BLUE.escape()+": ";
        display[73][7]=Color.ANSI_GREEN.escape()+"*";


        display[66][9]=Color.ANSI_YELLOW.escape()+"Y";
        display[67][9]=Color.ANSI_YELLOW.escape()+"E";
        display[68][9]=Color.ANSI_YELLOW.escape()+"L";
        display[69][9]=Color.ANSI_YELLOW.escape()+"L";
        display[70][9]=Color.ANSI_YELLOW.escape()+"O";
        display[71][9]=Color.ANSI_YELLOW.escape()+"W";
        display[72][9]=Color.ANSI_YELLOW.escape()+": ";
        display[73][9]=Color.ANSI_GREEN.escape()+"*";



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
     * @param column
     * @param row
     * @param weapons
     */
    public void weaponsSpawnSquare(int column, int row, String[] weapons){
        int h=0;
        int j=0;

                //RED
        if (column==0 && row==1){
            j=5;
        }else //BLUE
            if (column==2 && row==0){
            j=7;
        }else //YELLOW
            if( column==3 && row==2){
            j=9;
        }

        for (int i =74; i<77; i++ ) {
            if(h<weapons.length && weapons[h]!=null) {
                display[i][j] = Color.ANSI_GREEN.escape()+weapons[h]+" * ";
                h++;
            }
        }
    }



    public void printDisplay(){
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape());
        for(int i=0; i<ROW;i++) {//ROW

            for (int j = 0; j < COLUMN; j++) {//COLUMN
                System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+display[j][i]);
            }
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape());
        }
        System.out.flush();

    }


}
