package it.polimi.ingsw.view.cli.graph;

public class CLIMap {
    //un carattere verticale per ogni due orizzontali
    private static final int MAXCOLUMN = 44;
    private static final int MAXROW = 17;
    private String[][] map = new String[MAXCOLUMN][MAXROW];

    public CLIMap(){
        for (int i = 1; i < MAXCOLUMN; i++) {
            map[i][0]="═";
            map[i][MAXROW-1]="═";
        }
        for (int i = 1; i <MAXROW -1; i++) {
            map[0][i] ="║";
            //squareElement
            for (int j = 1; j < MAXCOLUMN-1; j++) {
                map[j][i] = "@";
            }
            map[MAXCOLUMN-1][i] = "║";
        }
        map[0][0] = "╔";
        map[MAXCOLUMN-1][0] = "╗";
        map[0][MAXROW-1] = "╚";
        map[MAXCOLUMN-1][MAXROW-1]= "╝";
    }

    public void plot() {

        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_WHITE.escape());
        for (int row = 0; row < MAXROW; row++) {
            System.out.println();
            for (int column = 0; column < MAXCOLUMN; column++) {
                System.out.print(map[column][row]);
            }
        }

    }


}
