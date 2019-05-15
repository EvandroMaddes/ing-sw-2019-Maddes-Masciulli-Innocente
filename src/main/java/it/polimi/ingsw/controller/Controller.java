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

           ?? implementare con un timer
            case StartGameEvent: {
                gameManager.startGame();
                break;
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

