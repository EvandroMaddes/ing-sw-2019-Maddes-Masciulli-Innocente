package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.*;

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
        switch (message.getType()) {

            case GameChoiceEvent: {
                gameManager = new GameManager((GameChoiceEvent) message);
                gameManager.buildGameBoard((GameChoiceEvent) message);
                break;
            }

            case PlayerChoiceEvent: {
                PlayerChoiceEvent msg = (PlayerChoiceEvent) message;
                switch (msg.getContext()) {
                    case "Character choice": {
                        gameManager.addPlayer(msg);
                        break;
                    }
                }
            }

            case StartGameEvent: {
                gameManager.startGame();
                break;
            }

            case ActionChoiceEvent: {
                ActionChoiceEvent msg = (ActionChoiceEvent) message;
                switch (msg.getAction()) {
                    case 0: {
                        gameManager.getCurrentRound().getActionManager().sendPossibleMoves();
                        break;
                    }
                    case 1: {
                        gameManager.getCurrentRound().getActionManager().sendPossibleGrabs();
                        break;
                    }
                    case 2: {
                        gameManager.getCurrentRound().getActionManager().sendPossibleWeapons();
                        break;
                    }
                }
            }

            case WeaponChoiceEvent: {
                WeaponChoiceEvent msg = (WeaponChoiceEvent) message;
                gameManager.getCurrentRound().getActionManager().weaponChoice(choosenWeapon);
                break;
            }

            case PositionChoiceEvent: {
                PositionChoiceEvent msg = (PositionChoiceEvent) message;
                switch (msg.getContext) {
                    case grab: {
                        gameManager.getCurrentRound().getActionManager().performGrab(msg);
                    }
                }
            }

            case CardChoiceEvent: {
                CardChoiceEvent msg = (CardChoiceEvent) message;
                switch (msg.getContext) {
                    case weaponGrab: {
                        gameManager.getCurrentRound().getActionManager().grabWeapon(msg);
                        break;
                    }
                    case weaponDiscard: {
                        gameManager.getCurrentRound().getActionManager().discardWeapon(msg);
                        break;
                    }
                }
            }
        }
    }
}

