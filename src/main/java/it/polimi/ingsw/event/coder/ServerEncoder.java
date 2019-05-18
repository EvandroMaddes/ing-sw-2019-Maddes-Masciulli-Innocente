package it.polimi.ingsw.event.coder;

import it.polimi.ingsw.event.model_view_event.AmmoTileUpdateEvent;
import it.polimi.ingsw.event.model_view_event.PositionUpdateEvent;
import it.polimi.ingsw.event.model_view_event.WeaponUpdateEvent;
import it.polimi.ingsw.event.view_select.CardRequestEvent;
import it.polimi.ingsw.event.view_select.PositionRequestEvent;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.Weapon;


import java.util.*;

/**
 * @author Francesco Masciulli
 * implements the encoding of ViewSelect and ModelView Events, whenever is needed;
 */
public class ServerEncoder {

    private final String mapUpdate = "MAPUPDATE";
    private int[] coordinates;
    private Iterator iterator;

    /**
     * @param user
     * @param availableCards
     * @return the encoded CardRequestEvent message
     */
    public CardRequestEvent encodeCardRequestEvent(String user, ArrayList<Card> availableCards, String type) {
        ArrayList<String> cards = new ArrayList<>();
        ArrayList<CubeColour> colours = new ArrayList<>();
        iterator= availableCards.iterator();
        while(iterator.hasNext()){
            Card currCard = (Card)iterator.next();
            cards.add(currCard.getName());
            colours.add(currCard.getColour());
        }
        return new CardRequestEvent(user, cards, type, colours);
    }

    /**
     *
     * @param user
     * @param squares
     * @return
     */
    public PositionRequestEvent encodePositionRequestEvent(String user, ArrayList<Square> squares){
        ArrayList<Integer> coordinatesX = new ArrayList<>();
        ArrayList<Integer> coordinatesY = new ArrayList<>();
        iterator = squares.iterator();
        while(iterator.hasNext()){
            Square currSquare = (Square) iterator.next();
            coordinatesX.add(currSquare.getColumn());
            coordinatesY.add(currSquare.getRow());
        }
        return new PositionRequestEvent(user, coordinatesX, coordinatesY);
    }

    /**
     * Given the user and the updated Square, encodes it in the right message
     *  must set the Event's UPDATEDRESOURCE = mapUpdate
     * @param user the Client user
     * @param updatedAmmoSquare the updated BasicSquare
     * @return the AmmoTileUpdateEvent message
     */
    public AmmoTileUpdateEvent encodeAmmoTileUpdateEvent(String user, BasicSquare updatedAmmoSquare){
        AmmoTile ammoTile = updatedAmmoSquare.getAmmo();
        evaluateSquareIndex(updatedAmmoSquare);
        String[] colours = new String[3];
        colours[0]= ammoTile.getAmmoCubes()[0].getColour().toString();
        colours[1]= ammoTile.getAmmoCubes()[1].getColour().toString();
        if(ammoTile.isPowerUpTile()){
            colours[2]= "PowerUp";
        }
        else{
            colours[2]= ammoTile.getAmmoCubes()[2].getColour().toString();
        }
        return new AmmoTileUpdateEvent(user, true, coordinates[0],coordinates[1], colours[0], colours[1], colours[2]);
    }

    public WeaponUpdateEvent encodeWeaponUpdateEvent(String user, SpawnSquare updatedWeaponSquare){
        evaluateSquareIndex(updatedWeaponSquare);
        iterator = updatedWeaponSquare.getWeapons().iterator();
        ArrayList<String> weaponsName = new ArrayList<>();
        while(iterator.hasNext()){
            weaponsName.add(((Weapon)iterator.next()).getName());
        }

        return new WeaponUpdateEvent(user, mapUpdate, coordinates[0],coordinates[1], weaponsName);
    }

    public PositionUpdateEvent encodePositionUpdateEvent(String user, String updatedPlayer, Square updatedPosition){
        evaluateSquareIndex(updatedPosition);
        return new PositionUpdateEvent(user, coordinates[0],coordinates[1]);
    }

    /**
     * a method that find the Square column (int[0]) and row (int[1])
     * @param square
     * @return a vector that indicates Square's Column and Row
     */
    private void evaluateSquareIndex(Square square){
        coordinates = new int[2];
        coordinates[0] = square.getColumn();
        coordinates[1] = square.getRow();

    }
}
