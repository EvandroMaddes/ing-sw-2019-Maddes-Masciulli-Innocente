package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class View extends Observable implements Observer {

    private String user;
    private Event toController;

    /**
     * Every player has a his own view
     * @param user
     */
    public View(String user) {
        this.user = user;
    }


    /**
     *
     * @param toController
     */
    public void setToController(Event toController) {
        this.toController = toController;
    }

    /**
     *
     * @return
     */
    public Event getToController() {
        return toController;
    }






}
