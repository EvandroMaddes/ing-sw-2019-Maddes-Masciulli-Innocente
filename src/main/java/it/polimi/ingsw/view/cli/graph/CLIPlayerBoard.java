package it.polimi.ingsw.view.cli.graph;

import com.sun.org.apache.xerces.internal.impl.io.ASCIIReader;
import it.polimi.ingsw.model.board.GameTrack;
import it.polimi.ingsw.model.player.Character;

public class CLIPlayerBoard {
    private String resource = Color.ANSI_BLACK_BACKGROUND.escape();
    private String user ;
    private Character character;
    private static final int MAXCOLUMN = 57;
    private static final int MAXROW = 4+1;
    private static final int MAXROW2 = 10;
    private String[][] playerBoard = new String[MAXCOLUMN][MAXROW2];

    public CLIPlayerBoard(String user, Character character){

        this.character= character;
        this.user = user;

    }

    public Character getCharacter() {
        return character;
    }

    public void createPlayerBoard() {

        for (int i = 0; i < MAXCOLUMN; i++) {
            playerBoard[i][0] = " ";
            playerBoard[i][1] = "═";
            playerBoard[i][MAXROW - 1] = "═";
        }
        playerBoard[0][0] = "★";
        playerBoard[1][0] = "USER: " + user + " is " + character.name();
        for (int i = 2; i < MAXROW - 1; i++) {
            playerBoard[0][i] = "║";
            for (int j = 1; j < MAXCOLUMN - 1; j++) {
                playerBoard[j][i] = " ";
            }
            playerBoard[MAXCOLUMN - 1][i] = "║";
        }

        for (int i = 8; i < MAXCOLUMN - 1; i = i + 4) {
            playerBoard[i][3] = "|";
            playerBoard[i][2] = "|";

        }
        playerBoard[2][3] = "D";
        playerBoard[3][3] = "A";
        playerBoard[4][3] = "M";
        playerBoard[5][3] = "A";
        playerBoard[6][3] = "G";
        playerBoard[7][3] = "E";
        playerBoard[2][2] = "M";
        playerBoard[3][2] = "A";
        playerBoard[4][2] = "R";
        playerBoard[5][2] = "K";
        playerBoard[6][2] = "S";

            playerBoard[0][1] = "╔";
            playerBoard[MAXCOLUMN - 1][1] = "╗";
            playerBoard[0][MAXROW - 1] = "╚";
            playerBoard[MAXCOLUMN - 1][MAXROW - 1] = "╝";

            for(int i=5; i<MAXROW2; i++){
                for (int j=0; j<MAXCOLUMN;j++){
                    playerBoard[j][i] =" ";
                }
            }

            for (int i=0; i<MAXCOLUMN; i++)
            {
                playerBoard[i][9]= "*";
            }
        playerBoard[2][5] = "︻┳═一 WEAPONS: ";
        playerBoard[2][6] = "♦ POWERUP: ";
        playerBoard[2][7] = "■ AMMO AVAILABLE: ";
        playerBoard[2][8] = "✞ SKULLS: ";

            for (int i = 0; i < MAXCOLUMN; i++) {
                for (int j = 1; j < MAXROW2; j++) {
                    playerBoard[i][j] = Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_WHITE.escape() + playerBoard[i][j];
                }
            }

    }

    public void markDamageUpdate(int damage, int mark){
        int i =10;
        while (damage>0 && i<MAXCOLUMN){
            if(playerBoard[i][3].contains(" ")) {

             playerBoard[i][3]="¤";
             damage--;

            } else{
                i = i+4;
             }
        }
        i=26;
        while (mark>0 && i<MAXCOLUMN){
            if(playerBoard[i][2].contains(" ")) {
                playerBoard[i][2]="¤";
                mark--;

            } else{
                i = i+4;
            }
        }

    }

    public void printPlayerBoard(){
        for (int row = 0; row < MAXROW2; row++) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape());
            for (int column =0; column < MAXCOLUMN; column++) {
                System.out.print(playerBoard[column][row]);
            }
        }
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_WHITE.escape());
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_WHITE.escape());

    }


    public void gadgetsUpdate(char type, String[] weapon){

        boolean done = false;
        int h=0, i=3,j=404;
        if(type =='W'){
            j=5;
        }else if (type=='P'){
            j=6;
        }else if(type=='A'){
            j=7;
        } else if(type == 'S')
            j=8;

        for(int row=3;row<MAXCOLUMN;row++){
            playerBoard[row][j] = " ";
        }


            while(!done){

                if(i< MAXCOLUMN && h<weapon.length){
                    playerBoard[i][j] = weapon[h];
                    playerBoard[i + 1][j] = "  ";
                    i = i+2;
                    h++;
                } else {
                    done = true;}
            }

    }





}



