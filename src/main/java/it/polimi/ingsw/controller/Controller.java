package it.polimi.ingsw.controller;

import com.sun.xml.internal.rngom.parse.host.Base;
import it.polimi.ingsw.model.action.ActionComponent;
import it.polimi.ingsw.model.action.Reload;
import it.polimi.ingsw.model.game_components.cards.BaseFightAction;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.Weapon;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private GameController gameController;


    public boolean checkSyntax()
    {
        boolean i = true;

        return i;
    }


    //update su Event, con switch case gestisco il contenuto del Event
    @Override
    public void update(Observable o, Object arg)
    {
      /*  switch (arg) {
            case arg.equals("Quit"):
        } */

    }
}
