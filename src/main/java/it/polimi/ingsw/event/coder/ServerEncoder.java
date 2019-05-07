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
import it.polimi.ingsw.model.game_components.ammo.CubeAmmoTile;
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
    private Iterator iterator;

    /**
     * @param user
     * @param availableCards
     * @return the encoded CardRequestEvent message
     */
    public CardRequestEvent encodeCardRequestEvent(String user, ArrayList<Card> availableCards) {
        ArrayList<String> cards = new ArrayList<>();
        ArrayList<CubeColour> colours = new ArrayList<>();
        //get(0) è una Card, dinamicamente Weapon o PowerUp, SimpleName è il tipo dinamico della classe
        String type= availableCards.get(0).getClass().getSimpleName();
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
        int positionRow = updatedAmmoSquare.getRow();
        int positionColumn = updatedAmmoSquare.getColumn();
        String[] colours = new String[3];
        colours[0]= ammoTile.getFirstAmmo().getColour().toString();
        colours[1]= ammoTile.getSecondAmmo().getColour().toString();
        if(updatedAmmoSquare.getClass().getSimpleName()== "PowerUpAmmoTile"){
            colours[2]= "PowerUp";
        }
        else{
            colours[2]= ((CubeAmmoTile) updatedAmmoSquare.getAmmo()).getThirdAmmo().getColour().toString();
        }
        return new AmmoTileUpdateEvent(user, mapUpdate, positionColumn, positionRow, colours[0], colours[1], colours[2]);
    }

    public WeaponUpdateEvent encodeWeaponUpdateEvent(String user, SpawnSquare updatedWeaponSquare){
        int positionRow = updatedWeaponSquare.getRow();
        int positionColumn = updatedWeaponSquare.getColumn();
        iterator = updatedWeaponSquare.getWeapons().iterator();
        ArrayList<String> weaponsName = new ArrayList<>();
        while(iterator.hasNext()){
            weaponsName.add(((Weapon)iterator.next()).getName());
        }

        return new WeaponUpdateEvent(user, mapUpdate, positionColumn, positionRow, weaponsName);
    }

    public PositionUpdateEvent encodePositionUpdateEvent(String user, String updatedPlayer, Square updatedPosition){
        int positionRow = updatedPosition.getRow();
        int positionColumn = updatedPosition.getColumn();
        return new PositionUpdateEvent(user, mapUpdate, positionColumn,positionRow);
    }
}
