package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;

import java.util.Observer;

/**
 * @author Francesco Masciulli
 * This interface will'be impelmented by the Virtual and Remote View
 * it extend Observer Interface, giving its methods implementation to the Concrete Views
 */
public interface ViewInterface extends Observer {

    public void setGame(Event event);
    public void getPlayer(Event event);
    public void setPlayer(Event event);
    public void updateGameBoard(Event event);
    public void startGame(Event event);
    public void askActionChoice(Event event);
    public void getActionChoice(Event event);
    public void showResult(Event event);
}
