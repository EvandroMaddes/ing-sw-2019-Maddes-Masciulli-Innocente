package it.polimi.ingsw.event.coder;

import it.polimi.ingsw.event.view_select.ActionRequestEvent;
import it.polimi.ingsw.event.view_select.CardRequestEvent;
import it.polimi.ingsw.event.view_select.PlayerRequestEvent;
import it.polimi.ingsw.event.view_select.PositionRequestEvent;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

/**
 * @author Francesco Masciulli
 * implements the encoding of ViewSelect and ModelView Events
 */
public class ServerEncoder {
    /**
     * is the map between Character and its index in the boolean Array of PlayerRequest
     */
    private EnumMap<Character, Integer> characterIntegerMap = new EnumMap<>(Character.class);
    private Iterator iterator;

    /**
     * Constructor
     * set the EnumMap
     */
    public ServerEncoder(){
        characterIntegerMap.put(Character.D_STRUCT_OR,0);
        characterIntegerMap.put(Character.BANSHEE,1);
        characterIntegerMap.put(Character.DOZER,2);
        characterIntegerMap.put(Character.VIOLET,3);
        characterIntegerMap.put(Character.SPROG,4);
    }

    public EnumMap<Character, Integer> getCharacterIntegerMap() {
        return characterIntegerMap;
    }


    /**
     *
     * @param user is the Client User
     * @param targetPlayers is an ArrayList of the Players that could be chosen
     * @param targetsNumber is the number of targets that the user must select
     * @return
     */
    public PlayerRequestEvent encodePlayerRequestEvent(String user, ArrayList<Player> targetPlayers, int targetsNumber){
        Character currCharacter;
        ArrayList<Character> availablePlayers = new ArrayList<>();
        iterator = targetPlayers.iterator();


        while(iterator.hasNext()){
            currCharacter=((Player)iterator.next()).getCharacter();
            availablePlayers.add(currCharacter);

        }
        
        return new PlayerRequestEvent(user, availablePlayers, targetsNumber);
        
    }

    /**
     * todo RoundManager deve passare Actions, damageContext(sempre, vd ActionRequestEvent)
     * @param user
     * @param availableActions
     * @param damageContext
     * @return
     */
    public ActionRequestEvent encodeActionRequestEvent(String user, boolean[] availableActions, int damageContext){
        return new ActionRequestEvent(user, availableActions, damageContext);
    }

    /**
     *
     * @param availableCards
     * @param user
     * @return the encoded CardRequestEvent message
     */
    public CardRequestEvent encodeCardRequestEvent(String user, ArrayList<Card> availableCards) {
        ArrayList<String> cards = new ArrayList<>();
        ArrayList<CubeColour> colours = new ArrayList<>();
        //get(0) è una Card, dinamicamente Weapon o PowerUp, SimpleName è la classe
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



}
