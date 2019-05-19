package it.polimi.ingsw.event.coder;

import it.polimi.ingsw.event.view_select.CardRequestEvent;
import it.polimi.ingsw.event.view_select.PositionRequestEvent;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.WeaponDeck;
import it.polimi.ingsw.model.player.Player;


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

        boolean isWeapon = message.getCardType().equals("Weapon");
        ArrayList<Card> cards = new ArrayList<>();
        Iterator cardsIterator = message.getCards().iterator();
        Iterator coloursIterator = message.getColour().iterator();
        WeaponDeck weaponDeck = new WeaponDeck();

        while (cardsIterator.hasNext()){
                if(isWeapon) {
                    Weapon currWeapon;
                    currWeapon = weaponDeck.findWeapon((String) cardsIterator.next());

                    cards.add(currWeapon); //torna un riferimento all'arma passata
                    //todo alternative switch case(uno per arma ) oppure uccisione gattini(instanceOf())
                    // oppure rendere la classe weapon non astratta ma poi si dovrebbero cambiare troppe cose
                }

                else{
                    cards.add(new PowerUp((CubeColour) coloursIterator.next(),(String)cardsIterator.next()));

                }
        }

        return cards;
    }

    /**
     * from coordinates to ArrayList di square
     * @param message
     * @return
     */
    public  ArrayList<Square> decodePositionRequestEvent(PositionRequestEvent message){
        Square currentSquare;
        ArrayList<Square> possibleSquares = new ArrayList<Square>();
        int row,column;

        for (int i = 0; i< message.getPossiblePositionsY().size(); i++) {

            row = message.getPossiblePositionsX().get(i);
            column = message.getPossiblePositionsY().get(i);

            if ((row == 2 && column == 2) || (row ==1 && column ==0) || (row == 0 && column==3))
            {
                currentSquare = new SpawnSquare(row,column);
            }
            else {
                currentSquare = new BasicSquare(row, column);
            }

            possibleSquares.add(currentSquare);

        }

        return possibleSquares;
    }


}
