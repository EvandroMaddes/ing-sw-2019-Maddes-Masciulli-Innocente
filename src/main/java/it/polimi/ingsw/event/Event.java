package it.polimi.ingsw.event;

import it.polimi.ingsw.controller.Controller;

import java.io.Serializable;

/**@author Francesco Masciulli
 * this is the abstract Class representing the messages between Server and Client
 *
 */
public abstract class Event implements Serializable {
    private String user;

    /**
     *
     * @param user is the string representing the user client,
     *             it will be mapped by the controller with the Character chosen
     */
   public Event(String user){
       this.user=user;
   }

    /**
     * Getter method
     * @return the User indicated in the Event;
     */
    public String getUser() {
        return user;
    }

    /**
     * this setter method is called during the BroadcastSend of a message
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    public abstract void performAction(Controller controller);
}
