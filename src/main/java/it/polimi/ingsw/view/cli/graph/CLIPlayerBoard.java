package it.polimi.ingsw.view.cli.graph;


import it.polimi.ingsw.model.player.Character;

import java.util.Map;

public class CLIPlayerBoard {
    private String resource = Color.ANSI_BLACK_BACKGROUND.escape();
    private String user ;
    private Character character;
    private Map<Character,String> mapCharacterNameColors;
    private static final int MAXCOLUMN = 57;
    private static final int MAXROW = 4+1;
    private static final int MAXROW2 = 12;
    private String[][] playerBoard = new String[MAXCOLUMN][MAXROW2];

    /**
     * constructor: it sets user, character map between character and color
     * @param user username
     * @param character character choose
     * @param mapCharacterNameColors use to set the correct color escape
     */
    public CLIPlayerBoard(String user, Character character, Map<Character, String> mapCharacterNameColors){

        this.character= character;
        this.user = user;
        this.mapCharacterNameColors = mapCharacterNameColors;
        createPlayerBoard();

    }

    /**
     * getter
     * @return PLAYERBOARD
     */
    public String[][] getPlayerBoard() {
        return playerBoard;
    }

    /**
     * getter
     * @return CHARACTER
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * It create a new playerBoard
     */
    private void createPlayerBoard() {
        String currColorEscape = Color.ANSI_BLACK_BACKGROUND.escape() + mapCharacterNameColors.get(character);
        for (int i = 0; i < MAXCOLUMN; i++) {
            playerBoard[i][0] =  " ";
            playerBoard[i][1] = "═";
            playerBoard[i][MAXROW - 1] = "═";
        }
        playerBoard[0][0] = "★";
        playerBoard[1][0] = "USER: " + user + " is " + currColorEscape + character.name();
        for (int i = 2; i < MAXROW - 1; i++) {
            playerBoard[0][i] =  "║";
            for (int j = 1; j < MAXCOLUMN - 1; j++) {
                playerBoard[j][i] = " ";
            }
            playerBoard[MAXCOLUMN - 1][i] ="║";
        }

        for (int i = 8; i < MAXCOLUMN - 1; i = i + 4) {
            playerBoard[i][3] = "|";
            playerBoard[i][2] = "|";

        }
        playerBoard[2][3] ="D";
        playerBoard[3][3] ="A";
        playerBoard[4][3] ="M";
        playerBoard[5][3] ="A";
        playerBoard[6][3] ="G";
        playerBoard[7][3] ="E";
        playerBoard[2][2] ="M";
        playerBoard[3][2] ="A";
        playerBoard[4][2] ="R";
        playerBoard[5][2] ="K";
        playerBoard[6][2] ="S";

            playerBoard[0][1] =  "╔";
            playerBoard[MAXCOLUMN - 1][1] = "╗";
            playerBoard[0][MAXROW - 1] =  "╚";
            playerBoard[MAXCOLUMN - 1][MAXROW - 1] =  "╝";

            for(int i=5; i<MAXROW2; i++){
                for (int j=0; j<MAXCOLUMN;j++){
                    playerBoard[j][i] =" ";
                }
            }

            for (int i=0; i<MAXCOLUMN; i++)
            {
                playerBoard[i][9]=  "*";
            }
        playerBoard[2][5] = "︻┳═一 WEAPONS: ";
        playerBoard[2][6] = "♦ POWERUP: ";
        playerBoard[2][7] = "■ AMMO AVAILABLE: ";
        playerBoard[2][8] = "✞ SKULLS: ";

            for (int i = 0; i < MAXCOLUMN; i++) {
                for (int j = 1; j < MAXROW2; j++) {
                    playerBoard[i][j] = currColorEscape + playerBoard[i][j];
                }
            }

    }

    /**
     * It updates number of damages on the playerBoard
     * @param damage number of damage
     * @param hittingCharacter Character who hits it (use to find correct color)
     * @param x column
     */
    public void damageUpdate(int damage, Character hittingCharacter, int x){
        countUpdate(damage,hittingCharacter,x,3);

    }

    /**
     * It updates number of damages on the playerBoard
     * @param mark number of damage
     * @param hittingCharacter Character who hits it (use to find correct color)
     * @param x column
     */
    public void markUpdate (int mark, Character hittingCharacter, int x){
        countUpdate(mark,hittingCharacter,x,2);
    }

    /**
     * it updates number of element of on passed
     * @param toAdd number of gadgets to add
     * @param hittingCharacter Character who hits it (use to find correct color)
     * @param x column
     * @param y row
     */
    private void countUpdate(int toAdd, Character hittingCharacter, int x, int y){
        String currColorEscape = Color.ANSI_BLACK_BACKGROUND.escape() + mapCharacterNameColors.get(hittingCharacter);
        while (toAdd>0 && toAdd<MAXCOLUMN){

            playerBoard[x][y]= currColorEscape + "¤";
            toAdd--;

        }
    }


    /**
     * it updates ammo, weapon and powerUp of one playerboards
     * @param type A: ammo;
     *             W: weapon;
     *             P: powerUp;
     * @param gadgets name of available gadjets of one playerBoard
     */
    public void gadgetsUpdate(char type, String[] gadgets){

        boolean done = false;
        int h = 0, i = 3, j = 404;
        if (type == 'W') {
            j = 5;
        } else if (type == 'P') {
            j = 6;
        } else if (type == 'A') {
            j = 7;
        }
        for (int row = 3; row < MAXCOLUMN; row++) {
            playerBoard[row][j] = " ";
        }


        while (!done) {

            if (i < MAXCOLUMN && h < gadgets.length) {
                playerBoard[i][j] = gadgets[h];
                playerBoard[i + 1][j] = "  ";
                i = i + 2;
                h++;
            } else {
                done = true;
            }
        }


        }

    /**
     * it cleans (fill in with white space) passed row of the playerboard
     * @param row selected row
     */
    public void clean(int row){
        int i=3;
        if(row==2||row==3){
            i=10;
        }
        for(int h=0;h<8;h++){

            playerBoard[i][row] = Color.ANSI_BLACK_BACKGROUND.escape() + " ";
            if(row==2||row==3){
                i=i+4;
            }else {
            i=i+2;}
        }
    }

    /**
     * it updates nummber of skulls on playerBoard
     * @param skullNumber number of skull
     */
    public void skullUpdate(int skullNumber){
        int i=3;
        for(int rep=0;rep<skullNumber;rep++){
             playerBoard[i][8] =Color.ANSI_RED.escape() + "☠";
             i=i+2;
            }
        }


    // TODO: 25/06/2019 da eliminare
    public void printPlayerBoard(){
        for (int row = 0; row < MAXROW2; row++) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape());
            for (int column =0; column < MAXCOLUMN; column++) {
                System.out.print(playerBoard[column][row]);
            }
        }

    }
}



