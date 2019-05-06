package it.polimi.ingsw.controller;



import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.GameModel;

import java.security.InvalidParameterException;
import java.util.Observer;

public class Controller implements Observer {

    private GameModel model;
    private GameController gameController;


    @Override
    /**
     *
     * @param message is the message that arrives from view with the map and mode choice
     * @throws InvalidParameterException if the choice are not allowed
     */
    public void update(Event message) throws InvalidParameterException {
        switch (message.getType()){
        }
    }

    public Controller (GameModel model){
        this.model = model;
    }

}
