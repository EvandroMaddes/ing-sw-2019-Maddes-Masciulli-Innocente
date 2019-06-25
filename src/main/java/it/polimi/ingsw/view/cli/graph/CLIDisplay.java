package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;
import java.util.ArrayList;



public class CLIDisplay {


    private static final int COLUMN = 150;
    private static final int ROW =77;
    private CLIMap map;
    private CLIGameTrack gameTrack;
    private ArrayList<CLIPlayerBoard> playerBoard = new ArrayList<>();
    private String[][] display = new String[COLUMN][ROW];

    /**
     *It fills in display with all white spaces
     */
    public CLIDisplay(){
        for(int i=0; i<ROW;i++) {//ROW
            for (int j = 0; j < COLUMN; j++) {//COLUMN
                display[j][i] = Color.ANSI_BLACK_BACKGROUND.escape()+" ";
            }
        }
    }

    /**
     * It puts in display: map, gameTrack and playerBoard and also some information
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

        display[a][a]=Color.ANSI_GREEN.escape()+"S";
        display[a+1][a]=Color.ANSI_GREEN.escape()+"Q";
        display[a+2][a]=Color.ANSI_GREEN.escape()+"U";
        display[a+3][a]=Color.ANSI_GREEN.escape()+"A";
        display[a+4][a]=Color.ANSI_GREEN.escape()+"R";
        display[a+5][a]=Color.ANSI_GREEN.escape()+"E";

        display[a][a+1]=Color.ANSI_RED.escape()+"R";
        display[a+1][a+1]=Color.ANSI_RED.escape()+"E";
        display[a+2][a+1]=Color.ANSI_RED.escape()+"D";
        display[a+3][a+1]=Color.ANSI_RED.escape()+": ";
        display[a+7][a+1]=Color.ANSI_GREEN.escape()+"*";



        display[a][a+2]=Color.ANSI_BLUE.escape()+"B";
        display[a+1][a+2]=Color.ANSI_BLUE.escape()+"L";
        display[a+2][a+2]=Color.ANSI_BLUE.escape()+"U";
        display[a+3][a+2]=Color.ANSI_BLUE.escape()+"E";
        display[a+4][a+2]=Color.ANSI_BLUE.escape()+": ";
        display[a+7][a+2]=Color.ANSI_GREEN.escape()+"*";


        display[a][a+3]=Color.ANSI_YELLOW.escape()+"Y";
        display[a+1][a+3]=Color.ANSI_YELLOW.escape()+"E";
        display[a+2][a+3]=Color.ANSI_YELLOW.escape()+"L";
        display[a+3][a+3]=Color.ANSI_YELLOW.escape()+"L";
        display[a+4][a+3]=Color.ANSI_YELLOW.escape()+"O";
        display[a+5][a+3]=Color.ANSI_YELLOW.escape()+"W";
        display[a+6][a+3]=Color.ANSI_YELLOW.escape()+": ";
        display[a+7][a+3]=Color.ANSI_GREEN.escape()+"*";

        display[a][a+6] = Color.ANSI_GREEN.escape()+"MAP LEGEND";
        display[a][a+7] = Color.ANSI_GREEN.escape()+"A : ammo";
        display[a][a+8] = Color.ANSI_GREEN.escape()+"P : powerUp";
        display[a][a+9] = Color.ANSI_BLUE.escape()+"B : BANSHEE";
        display[a][a+10] = Color.ANSI_WHITE.escape()+"D : DOZER";
        display[a][a+11] = Color.ANSI_YELLOW.escape()+"D : D_STRUCT_OR";
        display[a][a+12] = Color.ANSI_GREEN.escape()+"S : SPROG";
        display[a][a+13] = Color.ANSI_PURPLE.escape()+"V : VIOLET";

    }

    /**
     * Add playerBoard on the display
     * @param row where add player board
     * @param column where add player board
     * @param playerBoard player board to add
     */
    private void putPlayerBoard(int row, int column, CLIPlayerBoard playerBoard) {
        for (int i =row; i < 10+row; i++) {//ROW
            for (int j = column; j < 57+column; j++) {//COLUMN
                display[j][i] = playerBoard.getPlayerBoard()[j - column][i -row];
            }
        }
    }

    /**
     * getter
     * @return MAP
     */
    public CLIMap getMap() {
        return map;
    }

    /**
     * getter
     * @return GAMETRACK
     */
    public CLIGameTrack getGameTrack() {
        return gameTrack;
    }


    /**
     * it finds the playerBoard of passed character
     * @param currCharacter passed character
     * @return one player board
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
     * setter
     * @param newGameTrack GAMETRACK
     */
    public void setGameTrack(CLIGameTrack newGameTrack){
    this.gameTrack = newGameTrack;
    }

    /**
     * setter
     * @param map MAP
     */
    public void setMap(CLIMap map) {
        this.map = map;
    }

    /**
     * It adds one by one player to array
     * @param newPlayerBoard player board to add
     */
    public void setPlayerBoard(CLIPlayerBoard newPlayerBoard) {

        this.playerBoard.add(newPlayerBoard);
    }

    /**
     * Set available weapon on spawn square
     * @param column where set weapon
     * @param row where set weapon
     * @param weapons weapon to add
     */
    public void weaponsSpawnSquare(int column, int row, String[] weapons){
        int h=0;
        int j=0;

                //RED
        if (column==0 && row==1){
            j=61;
        }else //BLUE
            if (column==2 && row==0){
            j=62;
        }else //YELLOW
            if( column==3 && row==2){
            j=63;
        }

        for (int i =70; i<73; i++ ) {
            if(h<weapons.length && weapons[h]!=null) {
                display[i][j] = Color.ANSI_GREEN.escape()+weapons[h]+" * ";
                h++;
            }
        }
    }


    /**
     * It prints on screen the display
     */
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
