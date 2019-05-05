package it.polimi.ingsw.model.board;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
/**
 * @author Evandro Maddes
 */
public class Map {

    public final static int DIM_X = 4;
    public final static int DIM_Y = 3;

    private int mapNumber;
    private Map map;
    private ArrayList<SpawnSquare> spawnSquares;//Non serve visto che gli spawnSquare sono 3 e sono fissi

    private static Map ourInstance = new Map();

    public static Map getInstance()
    {
        return ourInstance;
    }

    private Map() {
    }


    /**
     * @param spawnSquares spawnSquare belonging at the current game
     */
    public void setSpawnSquares(ArrayList<SpawnSquare> spawnSquares) {
        this.spawnSquares = spawnSquares;
    }

    /**
     *
     * @param selectedLeftMap choice of the first part(left)
     * @param selectedRightMap choice of the second part(rigth)
     */
    public void createGround(String selectedLeftMap , String selectedRightMap ) {

        JsonParser parser = new JsonParser();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("map.json");
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject left = rootElement.getAsJsonObject().getAsJsonObject(selectedLeftMap);
        JsonObject right = rootElement.getAsJsonObject().getAsJsonObject(selectedRightMap);

        //creation of the left part
        ArrayList<Square> squares = new ArrayList<Square>();
        Square square0 = new BasicSquare(2, 0);
        squares.add(0, square0);
        Square square1 = new SpawnSquare(1, 0);
        squares.add(square1);
        Square square2;
        if (selectedLeftMap == "leftFirst") {

            square2 = new BasicSquare(0, 0);
        }else { square2 = null;}
        squares.add(square2);
        Square square3 = new BasicSquare(2, 1);
        squares.add(square3);
        Square square4 = new BasicSquare(1, 1);
        squares.add(square4);
        Square square5 = new BasicSquare(0, 1);
        squares.add(square5);

        int i = squares.size();//number of square of left part


        //creation of the right part
        Square square6 = new SpawnSquare(2, 2);
        squares.add(square6);
        Square square7 = new BasicSquare(1, 2);
        squares.add(square7);
        Square square8 = new BasicSquare(0, 2);
        squares.add(square8);
        Square square9;
        if (selectedRightMap == "rightFirst") {

            square9 = new BasicSquare(2, 3);
        }else{square9=null;}
        squares.add(square9);
        Square square10 = new BasicSquare(1, 3);
        squares.add(square10);
        Square square11 = new SpawnSquare(0, 3);
        squares.add(square11);



        addPropertyNearSquares(squares,right,(squares.size()-1),i-1);
        addPropertyNearSquares(squares,left,i-1,-1);
        addPropertyReachable(squares,right,(squares.size()-1),i-1);
        addPropertyReachable(squares,left,i-1,-1);


    }

    /**
     *create link between squares
     * @param squares all square belongs to the map
     * @param semiMapSelected right or left semi map
     * @param j starting point of the cycle
     * @param i ending point of the cycle
     * @throws UnsupportedOperationException
     */
    public void addPropertyNearSquares(ArrayList<Square> squares, JsonObject semiMapSelected, int j, int i)
    {

        for (int h=j; h > i; h--)//quadrati parte sinistra della mappa
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
     *create door of the map end set colour of every square
     * @param squares all square belongs to the map
     * @param semiMapSelected right or left semi map
     * @param j starting point of the cycle
     * @param i ending point of the cycle
     */
    public void addPropertyReachable(ArrayList<Square> squares, JsonObject semiMapSelected, int j, int i) {

        for ( int h=j; h>i ;h--) {
            if ( squares.get(h)!=null) {

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
