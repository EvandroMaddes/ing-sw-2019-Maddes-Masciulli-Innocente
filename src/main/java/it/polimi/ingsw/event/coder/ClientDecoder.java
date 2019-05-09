package it.polimi.ingsw.event.coder;

import it.polimi.ingsw.event.view_select.CardRequestEvent;
import it.polimi.ingsw.event.view_select.PositionRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.PowerUp;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Francesco Masciulli
 * it implements the method that decode a ModelView or ViewSelect Event
 */
public class ClientDecoder {

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
    public static ArrayList<Square> decodePositionRequestEvent(PositionRequestEvent message){
        return null;
    }


}
