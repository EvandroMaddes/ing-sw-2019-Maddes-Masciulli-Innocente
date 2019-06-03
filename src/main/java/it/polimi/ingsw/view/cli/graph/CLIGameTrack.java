package it.polimi.ingsw.view.cli.graph;

public class CLIGameTrack {
    private static final int MAXCOLUMN = 32;
    private static final int MAXROW = 3;
    private String[][] track = new String[MAXCOLUMN][MAXROW];


    public void createGameTrack(){
        for(int i=0; i<MAXROW; i++){
            for (int j=0; j<MAXCOLUMN; j++){
                track[j][i]=" ";
            }
        }
        track[1][0]="GAMETRACK : ";
        track[3][0]="BASIC MODE";

        for(int h = 2;h<MAXCOLUMN; h = h+4){
            track[h][1]= " ☠";
        }

        for (int lastLine =0; lastLine<MAXCOLUMN; lastLine++){
            track[lastLine][2] ="﹎";
        }
    }

    public void removeSkull(int damageToken, String colorEscape){
        boolean done= false;
        int column = 2;
        while (!done && column<MAXCOLUMN){
            if(track[column][1].contains("☠")){
                track[column][1] = "|"+colorEscape+" ▼";
                if(damageToken==2){
                    track[column+1][1]=colorEscape+ "▼";
                }
                done = true;
            }
            column= column+4;

        }

    }


    public void printGameTrack(){
        for(int i=0; i<MAXROW; i++){
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape());
            for (int j=0; j<MAXCOLUMN; j++){
                System.out.print(Color.ANSI_BLACK_BACKGROUND.escape());
                System.out.print(Color.ANSI_GREEN.escape()+track[j][i]);
            }

        }
        System.out.print("\n \n \n");
    }
}
