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

    public void setClientUser(String clientUser) {
        this.clientUser = clientUser;
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
            outputStream.writeObject(message);
            outputStream.flush();
        }catch(Exception e){
            CustomLogger.logException(e);
        }

    }

    @Override
    public Event listenMessage() {
        try{
            Event actualMessage =(Event) inputStream.readObject();
            return actualMessage;
        }
        catch (SocketException|EOFException socketClosed){
            disconnect();
        }
        catch (Exception e){CustomLogger.logException(e);}
        return currMessage;
    }

    public void resetMessage(){
        currMessage = null;
    }

}
