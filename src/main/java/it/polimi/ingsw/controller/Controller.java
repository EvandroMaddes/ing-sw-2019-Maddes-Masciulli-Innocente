package it.polimi.ingsw.controller;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private GameController gameController;


    public boolean checkSyntax()
    {
        boolean i = true;

        return i;
    }

    @Override
    public void update(Observable o, Object arg)
    {

    }
}