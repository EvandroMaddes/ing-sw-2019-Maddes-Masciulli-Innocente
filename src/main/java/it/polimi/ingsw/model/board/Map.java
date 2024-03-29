package it.polimi.ingsw.model.board;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * This class is the Map representation
 *
 * @author Evandro Maddes
 * @author Federico Innocente
 */
public class Map {

    /**
     * String that represent the bigger left semi-map
     */
    public static final String BIG_LEFT = "leftFirst";
    /**
     * String that represent the littler left semi-map
     */
    public static final String SMALL_LEFT = "leftSecond";
    /**
     * String that represent the bigger right semi-map
     */
    public static final String BIG_RIGHT = "rightFirst";
    /**
     * String that represent the littler right semi-map
     */
    public static final String SMALL_RIGHT = "rightSecond";
    /**
     * An ArrayList that contains all of the SpawnSquares
     */
    private ArrayList<SpawnSquare> spawnSquares = new ArrayList<>();
    /**
     * A Square matrix that contains the Map implementations square, set according with the game map
     */
    private Square[][] squareMatrix;
    /**
     * A number that identifies the chosen complete Map, composed by two semi-map
     */
    private int chosenMap;

    /**
     * Constructor, call a method that allocates the squareMatrix, and set the respectively chosenMap number
     *
     * @param leftMap  is the String that identifies the left semi-map
     * @param rightMap is the String that identifies the right semi-map
     */
    public Map(String leftMap, String rightMap) {
        this.squareMatrix = createGround(leftMap, rightMap);
        if (leftMap.equals(BIG_LEFT) && rightMap.equals(BIG_RIGHT))
            chosenMap = 0;
        else if (leftMap.equals(BIG_LEFT))
            chosenMap = 1;
        else if (leftMap.equals(SMALL_LEFT) && rightMap.equals(BIG_RIGHT))
            chosenMap = 2;
        else
            chosenMap = 3;
    }


    /**
     * Getter method:
     *
     * @return the squareMatrix
     */
    public Square[][] getSquareMatrix() {
        return squareMatrix;
    }

    /**
     * Getter method:
     *
     * @return an ArrayList that contains all of the SpawnSquares
     */
    public ArrayList<SpawnSquare> getSpawnSquares() {
        return spawnSquares;
    }

    /**
     * Getter method:
     *
     * @return the chosenMap number
     */
    public int getChosenMap() {
        return chosenMap;
    }

    /**
     * it creates ground and sets spawn square following the schema present in the resource Json
     *
     * @param selectedLeftMap  choice of the first part(left)
     * @param selectedRightMap choice of the second part(right)
     * @return the created map as a Square matrix
     */
    public Square[][] createGround(String selectedLeftMap, String selectedRightMap) {


        Square[][] squaresMatrix = new Square[3][4];
        JsonParser parser = new JsonParser();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/map.json");
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject left = rootElement.getAsJsonObject().getAsJsonObject(selectedLeftMap);
        JsonObject right = rootElement.getAsJsonObject().getAsJsonObject(selectedRightMap);

        //creation of the left part
        ArrayList<Square> squares = new ArrayList<>();

        Square square0 = new BasicSquare(0, 0);
        squares.add(0, square0);
        squaresMatrix[0][0] = square0;

        SpawnSquare square1 = new SpawnSquare(1, 0);
        squares.add(square1);
        squaresMatrix[1][0] = square1;
        getSpawnSquares().add(square1);

        Square square2;
        if (selectedLeftMap.equals(BIG_LEFT)) {

            square2 = new BasicSquare(2, 0);
        } else {
            square2 = null;
        }

        squares.add(square2);
        squaresMatrix[2][0] = square2;

        Square square3 = new BasicSquare(0, 1);
        squares.add(square3);
        squaresMatrix[0][1] = square3;

        Square square4 = new BasicSquare(1, 1);
        squares.add(square4);
        squaresMatrix[1][1] = square4;

        Square square5 = new BasicSquare(2, 1);
        squares.add(square5);
        squaresMatrix[2][1] = square5;


        int i = squares.size();//number of square of left part


        //creation of the right part
        SpawnSquare square6 = new SpawnSquare(0, 2);
        squares.add(square6);
        squaresMatrix[0][2] = square6;
        getSpawnSquares().add(square6);

        Square square7 = new BasicSquare(1, 2);
        squares.add(square7);
        squaresMatrix[1][2] = square7;

        Square square8 = new BasicSquare(2, 2);
        squares.add(square8);
        squaresMatrix[2][2] = square8;

        Square square9;
        if (selectedRightMap.equals(BIG_RIGHT)) {
            square9 = new BasicSquare(0, 3);

        } else {
            square9 = null;
        }
        squares.add(square9);
        squaresMatrix[0][3] = square9;

        Square square10 = new BasicSquare(1, 3);
        squares.add(square10);
        squaresMatrix[1][3] = square10;

        SpawnSquare square11 = new SpawnSquare(2, 3);
        squares.add(square11);
        squaresMatrix[2][3] = square11;
        getSpawnSquares().add(square11);


        addPropertyNearSquares(squares, right, (squares.size() - 1), i - 1);
        addPropertyNearSquares(squares, left, i - 1, -1);
        addPropertyReachable(squares, right, (squares.size() - 1), i - 1);
        addPropertyReachable(squares, left, i - 1, -1);

        if (selectedRightMap.equals(SMALL_RIGHT))
            square7.setSquareColour(square4.getSquareColour());

        return squaresMatrix;


    }

    /**
     * create link between squares according with the chosen map implementation
     *
     * @param squares         all square belongs to the map
     * @param semiMapSelected right or left semi map
     * @param j               starting point of the cycle
     * @param i               ending point of the cycle
     * @throws UnsupportedOperationException
     */
    private void addPropertyNearSquares(ArrayList<Square> squares, JsonObject semiMapSelected, int j, int i) {

        for (int h = j; h > i; h--)//quadrati parte sinistra della mappa
        {
            if (squares.get(h) != null) {
                Square north = null;
                Square south = null;
                Square east = null;
                Square west = null;

                try {
                    north = squares.get(semiMapSelected.getAsJsonObject(Integer.toString(h)).get("northSquare").getAsInt());//quadrato a nord dell'quadrato 0
                } catch (UnsupportedOperationException f) {

                    north = null;
                }
                try {

                    south = squares.get(semiMapSelected.getAsJsonObject(Integer.toString(h)).get("southSquare").getAsInt());
                } catch (UnsupportedOperationException f) {

                    south = null;
                }
                try {


                    east = squares.get(semiMapSelected.getAsJsonObject(Integer.toString(h)).get("eastSquare").getAsInt());
                } catch (UnsupportedOperationException f) {

                    east = null;
                }
                try {
                    west = squares.get(semiMapSelected.getAsJsonObject(Integer.toString(h)).get("westSquare").getAsInt());

                } catch (UnsupportedOperationException f) {

                    west = null;
                }

                squares.get(h).setNearSquares(north, south, east, west);
            }
        }
    }


    /**
     * create door of the map end set colour of every square
     *
     * @param squares         all square belongs to the map
     * @param semiMapSelected right or left semi map
     * @param j               starting point of the cycle
     * @param i               ending point of the cycle
     */
    private void addPropertyReachable(ArrayList<Square> squares, JsonObject semiMapSelected, int j, int i) {

        for (int h = j; h > i; h--) {
            if (squares.get(h) != null) {

                boolean northReachable;

                boolean southReachable;

                boolean eastReachable;

                boolean westReachable;

                northReachable = semiMapSelected.getAsJsonObject(Integer.toString(h)).get("northReachable").getAsBoolean();

                southReachable = semiMapSelected.getAsJsonObject(Integer.toString(h)).get("southReachable").getAsBoolean();

                try {
                    eastReachable = semiMapSelected.getAsJsonObject(Integer.toString(h)).get("eastReachable").getAsBoolean();
                } catch (UnsupportedOperationException e) {
                    eastReachable = squares.get(7).checkDirection(3);
                }

                westReachable = semiMapSelected.getAsJsonObject(Integer.toString(h)).get("westReachable").getAsBoolean();

                String colour = semiMapSelected.getAsJsonObject(Integer.toString(h)).get("colour").getAsString();
                squares.get(h).setSquareReachable(northReachable, southReachable, eastReachable, westReachable);
                squares.get(h).setSquareColour(colour);
            }
        }
    }


}
