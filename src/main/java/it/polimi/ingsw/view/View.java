package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameModel;

import java.util.Observer;
import java.util.Observable;

public class View extends Observable implements Observer {

    private GameModel gameModel;


    public void getPlayer()
    {

    }

    public void setPlayer()
    {

    }

    public void getPlayerChoice()
    {

    }

    public void setMap()
    {

    }

    public void setMode()
    {

    }

    public void setActionChoice()
    {

    }

    public void showResult()
    {

    }

    @Override
    public void update(Observable observable, Object arg )
    {

    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

}
