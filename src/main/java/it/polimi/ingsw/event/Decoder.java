package it.polimi.ingsw.event;

import it.polimi.ingsw.event.view_select.CardRequestEvent;
import it.polimi.ingsw.event.view_select.PlayerRequestEvent;
import it.polimi.ingsw.event.view_select.PositionRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;

public class Decoder {

    /**
     * it DON'T decode the targetsNumber, because is an int
     * @param message is the given PlayerRequestEvent
     * @param characterIntegerMap is the Encoder EnumMap
     * @return the targets Characters ArrayList
     */
    public ArrayList<Character> decodePlayerRequestEvent (PlayerRequestEvent message,EnumMap<Character, Integer> characterIntegerMap){
        ArrayList<Character> characters = new ArrayList<>();
        boolean[] targetPlayers = message.getTargetPlayers();
        for(int i=0; i<5; i++){
            if(targetPlayers[i]){
                characters.add(characterFromInt(characterIntegerMap,i));
            }
        }
        return characters;
    }

    /**
     * todo richiamare ricostruzione armi con json
     * @param message
     * @return
     */
    public ArrayList<Card> decodeCardRequestEvent(CardRequestEvent message){
        boolean isWeapon = message.getType().equals("Weapon");
        ArrayList<Card> cards = new ArrayList<>();
        Iterator cardsIterator = message.getCards().iterator();
        Iterator coloursIterator = message.getColour().iterator();
        while (cardsIterator.hasNext()){
                if(isWeapon){
                    String currWeapon= (String)cardsIterator.next();
                    //set da nome e Json

                }

                else{
                    cards.add(new PowerUp((CubeColour) coloursIterator.next(),(String)cardsIterator.next()));

                }
        }

        return cards;
    }

    /**
     * todo implementare la decodifica da coordinate
     * @param message
     * @return
     */
    public  ArrayList<Square> decodePositionRequestEvent(PositionRequestEvent message){
        return null;
    }

    /**
     *
     * @param characterIntegerMap is the Encoder CharacterIntegerMap (Character, Integer)
     * @param index is the Index that is represented by the Player in the Map
     * @return the Character mapped with the index
     */
    private Character characterFromInt(EnumMap<Character, Integer> characterIntegerMap, int index){
        Iterator iterator = characterIntegerMap.keySet().iterator();
        while(iterator.hasNext()){
            Character currCharacter = (Character) iterator.next();
            if(characterIntegerMap.get(currCharacter).intValue()==index){
                return currCharacter;
            }
        }
        return null;
    }
}
