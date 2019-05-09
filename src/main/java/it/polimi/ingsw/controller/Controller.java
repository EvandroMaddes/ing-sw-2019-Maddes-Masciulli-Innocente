package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.ActionChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.PlayerChoiceEvent;

import java.security.InvalidParameterException;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private GameManager gameManager;


    @Override
    /**
     *
     * @param message is the message that arrives from view with the map and mode choice
     * @throws InvalidParameterException if the choice are not allowed
     */
    public void update(Observable o, Object message /*qui Object sarebbe Event e Observable non so come usarlo*/ ){
       messageHandler((Event) message);
    }

    private void messageHandler (Event message){
        switch (message.getType()){

            case GameChoiceEvent:{
                gameManager = new GameManager((GameChoiceEvent)message);
                gameManager.buildGameBoard((GameChoiceEvent)message);
                break;
            }

            case PlayerChoiceEvent:{
                PlayerChoiceEvent msg = (PlayerChoiceEvent)message;
                switch (msg.getContext()){
                    case "Character choice":{
                        gameManager.addPlayer(msg);
                    }
                }
                break;
            }

            case StartGameEvent:{
                gameManager.startGame();
                break;
            }

            case ActionChoiceEvent:{
                ActionChoiceEvent msg = (ActionChoiceEvent) message;
                switch (msg.getAction()){
                    case 0: {
                        gameManager.getCurrentRound().getActionManager().sendPossibleMove();
                        break;
                    }
                    case 1: {
                        gameManager.getCurrentRound().getActionManager().sendPossibleGrab();
                        break;
                    }
                    case 2; {
                        gameManager.getCurrentRound().getActionManager().sendPossibleWeapon();
                        break;
                    }
                }
            }


        }
    }


}
