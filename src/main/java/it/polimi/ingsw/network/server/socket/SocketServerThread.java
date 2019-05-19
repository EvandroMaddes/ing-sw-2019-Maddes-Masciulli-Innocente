package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.utils.CustomLogger;


import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class SocketServerThread extends Thread implements NetworkHandler {
    private Socket client;
    private String clientUser;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Event currMessage;
    private boolean connected;

    public SocketServerThread(Socket socket){
        this.client = socket;
        connected = true;
        try {
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
            while(clientUser == null){
                clientUser = inputStream.readUTF();

            }

        }catch(IOException e){
            CustomLogger.logException(e);
        }
    }

    public void disconnect(){
        connected=false;
    }

    public Event getCurrMessage() {
        return currMessage;
    }

    public String getClientUser() {
        return clientUser;
    }

    public Socket getClient() {
        return client;
    }

    @Override
    public void run() {
        currMessage = null;
        while(connected){
            currMessage = listenMessage();

            //if msg=logout->connected=false;
        }

    }

    /**
     *
     * @param message
     */
    @Override
    public void sendMessage(Event message) {
        try {
            //currMessage = message;
            outputStream.writeObject(message);
            outputStream.flush();
        }catch(Exception e){
            CustomLogger.logException(e);
        }

    }

    @Override
    public Event listenMessage() {
        Event actualMessage;
        try {
            actualMessage = (Event) inputStream.readObject();
            if(!actualMessage.equals(currMessage)){
                currMessage = actualMessage;
            }
        }
        catch (SocketException|EOFException socketClosed){
            disconnect();
        }
        catch(Exception e){CustomLogger.logException(e);}


        return currMessage;
    }

}
