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
    private ArrayList<CLIPlayerBoard> playerBoard = new ArrayList<>();
    private String[][] display = new String[COLUMN][ROW];

    public CLIDisplay(){
        for(int i=0; i<ROW;i++) {//ROW
            for (int j = 0; j < COLUMN; j++) {//COLUMN
                display[j][i] = Color.ANSI_BLACK_BACKGROUND.escape()+" ";
            }
        }
    }

    /**
     * Create
     */
    public void createDisplay(){
        int size = playerBoard.size();

        //GAMETRACK
        for(int i=55; i<58;i++) {//ROW
            for (int j = 0; j < 32; j++) {//COLUMN
                display[j][i] = gameTrack.getTrack()[j][i-55];
            }
        }
            //MAP
           for(int i=60; i<77;i++) {//ROW
               for (int j = 3; j < 45; j++) {//COLUMN
                   display[j][i] = map.getMap()[j-3][i-60];
               }
           }
            //PLAYERBOARD
        if(!playerBoard.isEmpty()) {
            for (int i = 0; i < size; i++) {

                putPlayerBoard(i * 10, 2, playerBoard.get(i));

            }
        }

        int a = 60;

        display[a][a]=Color.ANSI_GREEN.escape()+"S";
        display[a+1][a]=Color.ANSI_GREEN.escape()+"P";
        display[a+2][a]=Color.ANSI_GREEN.escape()+"A";
        display[a+3][a]=Color.ANSI_GREEN.escape()+"W";
        display[a+4][a]=Color.ANSI_GREEN.escape()+"N";

        display[a+6][a]=Color.ANSI_GREEN.escape()+"S";
        display[a+7][a]=Color.ANSI_GREEN.escape()+"Q";
        display[a+8][a]=Color.ANSI_GREEN.escape()+"U";
        display[a+9][a]=Color.ANSI_GREEN.escape()+"A";
        display[a+10][a]=Color.ANSI_GREEN.escape()+"R";
        display[a+11][a]=Color.ANSI_GREEN.escape()+"E";
        display[a+12][a]=Color.ANSI_GREEN.escape()+"=";

        display[a+9][a+2]=Color.ANSI_RED.escape()+"R";
        display[a+10][a+2]=Color.ANSI_RED.escape()+"E";
        display[a+11][a+2]=Color.ANSI_RED.escape()+"D";
        display[a+12][a+2]=Color.ANSI_RED.escape()+": ";
        display[a+13][a+2]=Color.ANSI_GREEN.escape()+"*";



        display[a+9][a+4]=Color.ANSI_BLUE.escape()+"B";
        display[a+10][a+4]=Color.ANSI_BLUE.escape()+"L";
        display[a+11][a+4]=Color.ANSI_BLUE.escape()+"U";
        display[a+12][a+4]=Color.ANSI_BLUE.escape()+": ";
        display[a+13][a+4]=Color.ANSI_GREEN.escape()+"*";


        display[a+6][a+6]=Color.ANSI_YELLOW.escape()+"Y";
        display[a+7][a+6]=Color.ANSI_YELLOW.escape()+"E";
        display[a+8][a+6]=Color.ANSI_YELLOW.escape()+"L";
        display[a+9][a+6]=Color.ANSI_YELLOW.escape()+"L";
        display[a+10][a+6]=Color.ANSI_YELLOW.escape()+"O";
        display[a+11][a+6]=Color.ANSI_YELLOW.escape()+"W";
        display[a+12][a+6]=Color.ANSI_YELLOW.escape()+": ";
        display[a+13][a+6]=Color.ANSI_GREEN.escape()+"*";



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
