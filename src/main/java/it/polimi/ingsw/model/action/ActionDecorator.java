package it.polimi.ingsw.model.action;

import it.polimi.ingsw.model.player.Player;


public abstract class ActionDecorator implements ActionComponent {

    private Action action;


    public ActionDecorator(Action action)
    {
        this.action = action;
    }

}
