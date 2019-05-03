package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.cards.Card;

import java.util.ArrayList;

public class CardRequestEvent extends Event {

    private ArrayList<Card> cards;
    private String type;

    public CardRequestEvent(String user, ArrayList<Card> cards, String type){
        super(user);
        this.cards=cards;
        this.type=type;
    }
}
