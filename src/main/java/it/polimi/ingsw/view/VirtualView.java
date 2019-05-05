package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.GameModel;

import java.util.Observer;
import java.util.Observable;

public class VirtualView extends Observable implements ViewInterface {

    private GameModel gameModel;
    private Event currentEvent;

    //Implementing the ViewInterface methods

    /**
     * Set the Game's Map and Mod after the First Connected Player Choice
     * @param event is the Player choice of the Game's Map and Mod
     */
    @Override
    public void setGame(Event event){

    }

    /**
     * Give, to the asking user, the list of the avaiable (Not yet chosen) character
     * @param event contain an ArrayList of Player
     */
    @Override
    public void getPlayer(Event event) {

    }

    /**
     *Set the Character chosen by the user
     * @param event contain the Player chosen
     */
    @Override
    public void setPlayer(Event event) {

    }

    /**
     *Give the last update GameBoard
     * @param event contains the Updated GameBoard
     */
    @Override
    public void updateGameBoard(Event event) {

    }

    /**
     * Give , with a Signal (boolean), the intention of starting the game
     * @param event contains the boolean signal
     */
    @Override
    public void startGame(Event event) {

    }

    /**
     *Ask to the user a choice on his possible Action or Weapon that could fire or Square where he could move
     * @param event contains ArrayList of Action, Weapon or Square
     */
    @Override
    public void askActionChoice(Event event) {

    }

    /**
     * Communicates the chosen Action, Weapon or Square
     * @param event is an Action, Weapon or Square
     */
    @Override
    public void getActionChoice(Event event) {

    }

    /**
     *it give, at the end of the match, the Players and their ranking
     * @param event is an ArrayList of Player
     */
    @Override
    public void showResult(Event event) {

    }


    //Implementing Observer method update() (for each EVENT??
    // oppure for a single event, analyzing the concrete class name and Switch??)

    @Override
    public void update(Observable observable, Object arg )
    {

    }

    //Overriding the Observable methods
    //stesso ragionamento di sopra
    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
    @Override
    public void notifyObservers(Object arg) {

        super.notifyObservers(arg);
    }
}
