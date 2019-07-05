package it.polimi.ingsw.event;

import java.io.Serializable;

/**
 * this is the abstract Class representing the messages between Server and Client
 *
 * @author Francesco Masciulli
 */
public abstract class Event implements Serializable {

    /**
     * Is the addressee's Username
     */
    private String user;

    /**
     * Constructor:
     *
     * @param user is the string representing the addressee,
     */
    public Event(String user) {
        this.user = user;
    }

    /**
     * Getter method
     *
     * @return the user value
     */
    public String getUser() {
        return user;
    }

}
