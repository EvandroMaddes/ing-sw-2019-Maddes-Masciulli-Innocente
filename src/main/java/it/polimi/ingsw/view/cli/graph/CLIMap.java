package it.polimi.ingsw.view.cli.graph;

import java.util.ArrayList;
import java.util.Iterator;

import static it.polimi.ingsw.view.cli.graph.CLISquare.MAXSQUARECOLUMN;
import static it.polimi.ingsw.view.cli.graph.CLISquare.MAXSQUAREROW;

/**
 * It is the map
 *
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
public class CLIMap {
    private static final int MAXCOLUMN = 42;
    private static final int MAXROW = 17;
    /**
     * matrix that represent a map
     */
    private String[][] map = new String[MAXCOLUMN][MAXROW];

    /**
     * getter
     *
     * @return MAP
     */
    public String[][] getMap() {
        return map;
    }

    public CLIMap(int mapNumber) {
        for (int i = 1; i < MAXCOLUMN; i++) {
            map[i][0] = "═";
            map[i][MAXROW - 1] = "═";
        }
        for (int i = 1; i < MAXROW - 1; i++) {
            map[0][i] = "║";
            for (int j = 1; j < MAXCOLUMN - 1; j++) {
                map[j][i] = " ";
            }
            map[MAXCOLUMN - 1][i] = "║";
        }
        map[0][0] = "╔";
        map[MAXCOLUMN - 1][0] = "╗";
        map[0][MAXROW - 1] = "╚";
        map[MAXCOLUMN - 1][MAXROW - 1] = "╝";

        for (int i = 0; i < MAXCOLUMN; i++) {
            for (int j = 0; j < MAXROW; j++) {
                map[i][j] = Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_WHITE.escape() + map[i][j];
            }

        }

        ArrayList<CLISquare> squares = allocateSquares(mapNumber);
        populateMap(squares);

    }

    /**
     * it's called by the constructor, it create the correct map by passed number
     *
     * @param mapNumber number of selected map
     * @return list of square
     */
    private ArrayList<CLISquare> allocateSquares(int mapNumber) {
        ArrayList<CLISquare> cliSquares = new ArrayList<>();
        ArrayList<CLISquare> leftSquares = new ArrayList<>();
        ArrayList<CLISquare> rightSquares = new ArrayList<>();
        switch (mapNumber) {
            //map FirstLeft FirstRight
            case 0:
                leftSquares = allocateSemiMap(true, false, false);
                rightSquares = allocateSemiMap(true, true, false);
                break;
            case 1:
                leftSquares = allocateSemiMap(true, false, true);
                rightSquares = allocateSemiMap(false, true, true);
                break;

            case 2:
                leftSquares = allocateSemiMap(false, false, true);
                rightSquares = allocateSemiMap(true, true, true);
                break;
            case 3:
                leftSquares = allocateSemiMap(false, false, false);
                rightSquares = allocateSemiMap(false, true, false);
                break;
        }

        int count = 0;
        while (count < 5) {
            cliSquares.add(leftSquares.get(count));
            cliSquares.add(leftSquares.get(count + 1));
            cliSquares.add(rightSquares.get(count));
            cliSquares.add(rightSquares.get(count + 1));
            count = count + 2;

        }
        return cliSquares;
    }


    /**
     * Depending on the boolean given, it allocate the respectively game's semi-map
     *
     * @param isFirst           is true if must be allocated the first semi-map, false if second
     * @param isRight           is true if must be allocated the Right semi-map, false if is Left
     * @param isAlternatedOrder is true if is allocated map 1 or 2 (FirstLeft+SecondRight and SecondLeft+FirstRight)
     * @return
     */
    private ArrayList<CLISquare> allocateSemiMap(boolean isFirst, boolean isRight, boolean isAlternatedOrder) {
        ArrayList<CLISquare> cliSquares = new ArrayList<>();
        CLISquare currSquare;
        if (isFirst && !isRight) {
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
            if (isAlternatedOrder) {
                currSquare.eraseWall(false, true);
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
            currSquare.insertDoor(false, true);
            if (isAlternatedOrder) {
                currSquare.eraseWall(false, true);
            }
            cliSquares.add(currSquare);
        } else if (isFirst) {
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
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(true, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, false);
            currSquare.eraseWall(true, true);
            if (isAlternatedOrder) {
                currSquare.insertDoor(false, false);
            }
            currSquare.eraseWall(false, false);
            currSquare.insertDoor(true, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, false);
            currSquare.eraseWall(true, false);
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(false, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_YELLOW, true);
            currSquare.eraseWall(true, false);
            currSquare.eraseWall(false, false);
            cliSquares.add(currSquare);
        } else if (!isRight) {
            currSquare = new CLISquare(Color.ANSI_BLUE, false);
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_BLUE, false);
            currSquare.eraseWall(false, false);
            currSquare.eraseWall(false, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_RED, true);
            currSquare.eraseWall(false, true);
            currSquare.insertDoor(true, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_RED, false);


            if (!isAlternatedOrder) {
                currSquare.eraseWall(false, true);
            }
            currSquare.eraseWall(false, false);
            currSquare.insertDoor(true, true);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_BLACK, false);
            cliSquares.add(currSquare);

            currSquare = new CLISquare(Color.ANSI_WHITE, false);
            currSquare.insertDoor(false, true);
            if (!isAlternatedOrder) {
                currSquare.eraseWall(false, true);
            }
            currSquare.insertDoor(true, false);
            cliSquares.add(currSquare);
        } else {
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
     * This method insert in the map the right sequence of CLISquares, given in the ArrayList squares
     * the order follow the horizontal sequence of each map, constructed in the method allocateSquares()
     *
     * @param squares square to add
     */
    private void populateMap(ArrayList<CLISquare> squares) {
        int currColumn;
        int currRow = 1;
        Iterator iterator = squares.iterator();
        while (currRow < MAXROW - 2) {
            currColumn = 1;
            while (currColumn < MAXCOLUMN - 4) {
                if (iterator.hasNext()) {
                    String[][] square = ((CLISquare) iterator.next()).getSquareString();
                    for (int i = 0; i < MAXSQUARECOLUMN; i++) {
                        System.arraycopy(square[i], 0, map[currColumn + i], currRow, square[i].length);
                    }
                }
                currColumn = currColumn + MAXSQUARECOLUMN;
            }
            currRow = currRow + MAXSQUAREROW;
        }
    }


    /**
     * Set a CLIPrintableElement on the map's square identified by the coordinates (of it on the mapMatrix)
     *
     * @param element is the update element
     * @param coordX  column is the Square's column
     * @param coordY  row is the Square's row
     */
    public void updateResource(CLIPrintableElement element, int coordX, int coordY) {
        coordX = coordX * MAXSQUARECOLUMN + 2;
        coordY = coordY * MAXSQUAREROW + 2;
        int tempCoordX;
        int tempCoordY;
        if (element.isPlayer()) {
            removePlayer(element.getResource());
            tempCoordX = coordX;
            tempCoordY = coordY + 1;
            while (tempCoordY < coordY + MAXSQUAREROW) {

                while (tempCoordX < coordX + MAXSQUARECOLUMN - 1) {
                    if (map[tempCoordX][tempCoordY].contains(" ")) {
                        map[tempCoordX][tempCoordY] = element.getResource();
                        return;
                    } else {
                        tempCoordX = tempCoordX + 3;
                    }
                }

                tempCoordX = coordX + 2;
                tempCoordY = tempCoordY + 1;
            }
        } else if (!element.getResource().contains("A")) {
            map[coordX + 1][coordY] = " ";
            map[coordX + 3][coordY] = " ";
            map[coordX + 5][coordY] = " ";
        } else {
            String completeString = element.getResource();
            int resourceLenght = completeString.indexOf('A');
            map[coordX + 1][coordY] = completeString.substring(0, resourceLenght + 1);
            map[coordX + 3][coordY] = completeString.substring(resourceLenght + 1, resourceLenght * 2 + 2);
            map[coordX + 5][coordY] = completeString.substring(resourceLenght * 2 + 2);
        }

    }

    /**
     * It remove one player from the map
     *
     * @param player selected player
     */
    public void removePlayer(String player) {
        for (int i = 0; i < MAXCOLUMN; i++) {
            for (int j = 0; j < MAXROW; j++) {
                if (map[i][j].contains(player)) {
                    map[i][j] = Color.ANSI_BLACK_BACKGROUND.escape() + " ";
                }
            }
        }
    }

}

