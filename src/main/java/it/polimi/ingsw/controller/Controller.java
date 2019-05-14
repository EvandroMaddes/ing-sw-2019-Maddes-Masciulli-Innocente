package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private GameManager gameManager;


    @Override
    /**
     *
     * @param message is the message that arrives from view with the map and mode choice
     */
    public void update(Observable virtualView, Object message){
       ((ViewControllerEvent)message).performAction(this);
    }

    public void createGameManager(int mapChoice){
        gameManager = new GameManager(mapChoice);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    /*
    private void messageHandler (Event message){
        switch (message.getType()) {

    x        case GameChoiceEvent: {
                gameManager = new GameManager((GameChoiceEvent) message);
                gameManager.buildGameBoard((GameChoiceEvent) message);
                break;
            }

    x        case PlayerChoiceEvent: {
                PlayerChoiceEvent msg = (PlayerChoiceEvent) message;
                switch (msg.getContext()) {
                    case "Character choice": {
                        gameManager.addPlayer(msg);
                        break;
                    }

                }
            } // a qui sono stati modificati gli eventi

           ?? implementare con un timer
            case StartGameEvent: {
                gameManager.startGame();
                break;
            }

       x     case ActionChoiceEvent: {
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

            case PositionChoiceEvent: {
                PositionChoiceEvent msg = (PositionChoiceEvent) message;
                switch (msg.getContext()) {
                    case "grab": {
                        gameManager.getCurrentRound().getActionManager().performGrab(msg);
                        break;
                    }
                    case "move":{
                        gameManager.getCurrentRound().getActionManager().performMove(msg);
                        break;
                    }
                }
            }

            case CardChoiceEvent: {
                CardChoiceEvent msg = (CardChoiceEvent) message;
                switch (msg.getContext()) {
                    case "WeaponGrab": {
                        gameManager.getCurrentRound().getActionManager().grabWeapon(msg);
                        break;
                    }
                    case "WeaponDiscard": {
                        gameManager.getCurrentRound().getActionManager().discardWeapon(msg);
                        break;
                    }
                    case "SelectShotingWeapon":{
                        gameManager.getCurrentRound().getActionManager().weaponChoice(msg);
                        break;
                    }
                    case "PowerUpToRespawn":{
                        gameManager.getCurrentRound().spawn(msg);
                        break;
                    }
                    case "SelectPowerUpToUse":{

                    }
                    case "SelectPowerUpAsCube":{

                    }
                }
            }
        }
    }
    */
}

