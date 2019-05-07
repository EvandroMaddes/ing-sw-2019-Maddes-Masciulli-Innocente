package it.polimi.ingsw.controller;



import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.model.GameModel;

import java.security.InvalidParameterException;
import java.util.Observer;

public class Controller implements Observer {

    private GameModel model;
    private GameManager gameManager;


    @Override
    /**
     *
     * @param message is the message that arrives from view with the map and mode choice
     * @throws InvalidParameterException if the choice are not allowed
     */
    public void update(Event message) throws InvalidParameterException {
        switch (message.getType()){
            case GameChoiceEvent:{
                gameManager = new GameManager( (GameChoiceEvent)message);
            }
        }
    }

    public Controller (GameModel model){
        this.model = model;
    }

}
