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

    public CLIPlayerBoard(String user, Character character, Map<Character, String> mapCharacterNameColors){

        this.character= character;
        this.user = user;
        this.mapCharacterNameColors = mapCharacterNameColors;
        createPlayerBoard();

    }

    public Character getCharacter() {
        return character;
    }

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

    public void damageUpdate(int damage, Character hittingCharacter, int i){

        String currColorEscape = Color.ANSI_BLACK_BACKGROUND.escape() + mapCharacterNameColors.get(hittingCharacter);
        while (damage>0 && i<MAXCOLUMN){
             playerBoard[i][3]= currColorEscape + "¤";
             damage--;

        }

    }

    public void markUpdate (int mark, Character hittingCharacter, int i){
        String currColorEscape = Color.ANSI_BLACK_BACKGROUND.escape() + mapCharacterNameColors.get(hittingCharacter);
        while (mark>0 && i<MAXCOLUMN){

            playerBoard[i][2]= currColorEscape + "¤";
            mark--;

        }
    }




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

    public void clean(int j){
        int i=3;
        if(j==2||j==3){
            i=10;
        }
        for(int h=0;h<8;h++){

            playerBoard[i][j] =Color.ANSI_BLACK_BACKGROUND.escape() + " ";
            if(j==2||j==3){
                i=i+4;
            }else {
            i=i+2;}
        }
    }

        public void skullUpdate(int skullNumber){
        int i=3;
        for(int rep=0;rep<skullNumber;rep++){
             playerBoard[i][8] =Color.ANSI_RED.escape() + "☠";
             i=i+2;
            }
        }


    public String[][] getPlayerBoard() {
        return playerBoard;
    }
}



