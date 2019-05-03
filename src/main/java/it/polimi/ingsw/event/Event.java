package it.polimi.ingsw.event;

public abstract class Event {
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
}
