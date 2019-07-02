package it.polimi.ingsw.network;


import it.polimi.ingsw.event.Event;

/**
 * Interface handling the messages exchange on the network
 * @author Francesco Masciulli
 */
public interface NetworkHandler {
     /**
      * Send the given message
      * @param message is the message that must be sent
      */
     void sendMessage(Event message);

     /**
      * listen a new message, that is returned
      * @return the message listened
      */
     Event listenMessage();
}
