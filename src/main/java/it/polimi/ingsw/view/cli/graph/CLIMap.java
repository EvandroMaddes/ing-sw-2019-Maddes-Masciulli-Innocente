package it.polimi.ingsw.view.cli.graph;


import java.util.ArrayList;
import java.util.Iterator;

import static it.polimi.ingsw.view.cli.graph.CLISquare.MAXSQUARECOLUMN;
import static it.polimi.ingsw.view.cli.graph.CLISquare.MAXSQUAREROW;

//todo è stata implementata PrinatbleElement per creare stringhe dei player e delle ammo, occhio alle coordinate su map
public class CLIMap {
    //un carattere verticale per ogni due orizzontali
    private static final int MAXCOLUMN = 42;
    private static final int MAXROW = 17;
    private String[][] map = new String[MAXCOLUMN][MAXROW];



    public CLIMap(int mapNumber){
        for (int i = 1; i < MAXCOLUMN; i++) {
            map[i][0]="═";
            map[i][MAXROW-1]="═";
        }
        for (int i = 1; i <MAXROW -1; i++) {
            map[0][i] ="║";
            for (int j = 1; j < MAXCOLUMN-1; j++) {
                map[j][i] = " ";
            }
            map[MAXCOLUMN-1][i] = "║";
        }
        map[0][0] = "╔";
        map[MAXCOLUMN-1][0] = "╗";
        map[0][MAXROW-1] = "╚";
        map[MAXCOLUMN-1][MAXROW-1]= "╝";

        for (int i = 0; i < MAXCOLUMN; i++) {
            for (int j = 0; j < MAXROW; j++) {
                map[i][j] = Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_WHITE.escape()+map[i][j];
            }

        }

        ArrayList<CLISquare> squares = allocateSquares(mapNumber);
        populateMap(squares);

    }

    /**
     * called by the constructor, depending on the mapNumber given
     * @param mapNumber
     * @return
     */
    private ArrayList<CLISquare> allocateSquares(int mapNumber){
        ArrayList<CLISquare> cliSquares = new ArrayList<>();
        ArrayList<CLISquare> leftSquares = new ArrayList<>();
        ArrayList<CLISquare> rightSquares = new ArrayList<>();
        switch(mapNumber){
           //map FirstLeft FirstRight
            case 0:
                leftSquares = allocateSemiMap(true,false, false);
                rightSquares = allocateSemiMap(true, true, false);
                break;
            case 1:
                leftSquares = allocateSemiMap(false,false, true);
                rightSquares = allocateSemiMap(true,true, true);
                break;
            case 2:
                leftSquares = allocateSemiMap(true,false, true);
                rightSquares = allocateSemiMap(false,true, true);
                break;
            case 3:
                leftSquares = allocateSemiMap(false,false, false);
                rightSquares = allocateSemiMap(false,true, false);
                break;
        }

        int count = 0;
        while(count<5){
            cliSquares.add(leftSquares.get(count));
            cliSquares.add(leftSquares.get(count+1));
            cliSquares.add(rightSquares.get(count));
            cliSquares.add(rightSquares.get(count+1));
            count = count + 2;

        }
        return cliSquares;
    }

    private ArrayList<CLISquare> allocateSemiMap(boolean isFirst, boolean isRight, boolean isAlternatedOrder){
        ArrayList<CLISquare> cliSquares = new ArrayList<>();
        CLISquare currSquare;
        if(isFirst&&!isRight){
            currSquare = new CLISquare(Color.ANSI_RED, false);
            currSquare.eraseWall(true, true);
            currSquare.insertDoor(false, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_BLUE, false);
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(false, false);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_RED, true);
            currSquare.eraseWall(true, false);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_PURPLE, false);
            if(isAlternatedOrder){
                currSquare.eraseWall(false,true);
            }
            currSquare.insertDoor(true, false);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_WHITE, false);
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(true, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_WHITE, false);
            currSquare.eraseWall(false, false);
            currSquare.insertDoor(true, false);
            currSquare.insertDoor(false,true);
            if(isAlternatedOrder){
                currSquare.eraseWall(false,true);
            }
            cliSquares.add(currSquare);
        }
        else if (isFirst){
            currSquare = new CLISquare(Color.ANSI_BLUE, true);
            currSquare.eraseWall(false, false);
            currSquare.insertDoor(false, true);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_GREEN, false);
            currSquare.insertDoor(false, false);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, false);
            currSquare.eraseWall(true, true);
            currSquare.eraseWall(false,true);
            currSquare.insertDoor(true,false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, false);
            currSquare.eraseWall(true, true);
            if(isAlternatedOrder){
                currSquare.insertDoor(false,false);
            }
            currSquare.eraseWall(false,false);
            currSquare.insertDoor(true, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, false);
            currSquare.eraseWall(true, false);
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(false, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, true);
            currSquare.eraseWall(true, false);
            currSquare.eraseWall(false,false);
            cliSquares.add(currSquare);
        }
        else if(!isRight){
            currSquare = new CLISquare(Color.ANSI_BLUE, false);
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_BLUE, false);
            currSquare.eraseWall(false, false);
            currSquare.eraseWall(false,true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_RED, true);
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(true, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_RED, false);


            if(!isAlternatedOrder){
                currSquare.eraseWall(false, true);
            }
            currSquare.eraseWall(false, false);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_BLACK, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_WHITE, false);
            currSquare.insertDoor(false,true);
            if(!isAlternatedOrder){
                currSquare.eraseWall(false, true);
            }
            currSquare.insertDoor(true, false);
            cliSquares.add(currSquare);
        }
        else{
            currSquare = new CLISquare(Color.ANSI_BLUE, true);
            currSquare.eraseWall(false, false);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_BLACK, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_PURPLE, false);
            currSquare.eraseWall(false, false);
            currSquare.insertDoor(true, false);
            currSquare.insertDoor(false, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, false);
            currSquare.eraseWall(true, true);
            currSquare.insertDoor(false, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_WHITE, false);
            currSquare.eraseWall(false, false);
            currSquare.insertDoor(false, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, true);
            currSquare.eraseWall(true, false);
            currSquare.insertDoor(false, false);
            cliSquares.add(currSquare);

        }
        return cliSquares;
    }



    /**
     * this method insert in the map the right sequence of CLISquares, given in the ArrayList squares
     * the order follow the horizontal sequence of each map, constructed in the method allocateSquares()
     * @param squares
     */
    private void populateMap(ArrayList<CLISquare> squares){
        int currColumn;
        int currRow = 1;
        Iterator iterator = squares.iterator();
        while(currRow<MAXROW-2){
            currColumn = 1;
            while(currColumn<MAXCOLUMN-4) {
                if(iterator.hasNext()) {
                    String[][] square = ((CLISquare) iterator.next()).getSquareString();
                    for (int i = 0; i < MAXSQUARECOLUMN; i++) {
                        System.arraycopy(square[i], 0, map[currColumn + i], currRow, square[i].length);
                    }
                }
                currColumn=currColumn+MAXSQUARECOLUMN;
            }
            currRow=currRow+MAXSQUAREROW;
        }
    }



    public void plot() {

        for (int row = 0; row < MAXROW; row++) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape());
            for (int column = 0; column < MAXCOLUMN; column++) {
                System.out.print(map[column][row]);
            }
        }

    }
}

