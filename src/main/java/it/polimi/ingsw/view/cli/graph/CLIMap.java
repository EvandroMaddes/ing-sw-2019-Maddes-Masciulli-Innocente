package it.polimi.ingsw.view.cli.graph;

public class CLIMap {
    private static final int MAXCOLUMN = 22;
    private static final int MAXROW = 15;
    private String[][] map = new String[MAXCOLUMN][MAXROW];

    public CLIMap(){
        for (int i = 1; i < MAXCOLUMN; i++) {
            map[i][0]="═";
            //squareElement
            for (int j = 1; j < MAXROW-1; j++) {
                map[i][j] = " ";
            }
            map[i][14]="═";
        }
        for (int i = 1; i <MAXROW -1; i++) {
            map[0][i] ="║";
            //squareElement
            for (int j = 1; j < MAXCOLUMN-1; j++) {
                map[j][i] = " ";
            }
            map[21][i] = "║";
        }
        map[0][0] = "╔";
        map[MAXCOLUMN-1][0] = "╗";
        map[0][MAXROW-1] = "╚";
        map[MAXCOLUMN-1][MAXROW-1]= "╝";
    }

    public void plot() {
        System.out.print( Color.ANSI_GREEN.escape());
        for (int row = 0; row < MAXROW; row++) {
            System.out.println();
            for (int column = 0; column < MAXCOLUMN; column++) {
                System.out.print(map[column][row]);
            }
        }

    }


}
