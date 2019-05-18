package it.polimi.ingsw.network;


import it.polimi.ingsw.event.Event;

public interface NetworkHandler {

     void sendMessage(Event message);
     Event listenMessage();
}
