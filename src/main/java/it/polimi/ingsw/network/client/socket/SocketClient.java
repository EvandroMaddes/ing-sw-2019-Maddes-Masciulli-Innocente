package it.polimi.ingsw.network.client.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.*;
import java.net.Socket;

/**
 * @author Francesco Masciulli
 * This is the Socket Network implementation
 */
public class SocketClient implements NetworkHandler, ClientInterface {
    private String user;
    private String serverIPAddress;
    private int serverPort;
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

   public SocketClient(String user, String serverIPAddress){
        this.user=user;
        this.serverIPAddress=serverIPAddress;
        this.serverPort = NetConfiguration.SOCKETSERVERPORTNUMBER;
        connectClient();
   }

    @Override
    public void reconnectClient() {
        try{
            disconnectClient();
            connectClient();
        }catch(Exception e){
            CustomLogger.logException(e);
        }
    }

    @Override
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public synchronized void connectClient(){

        try{
            clientSocket = new Socket(serverIPAddress, serverPort);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream.writeUTF(user);
            outputStream.flush();
        }catch(Exception e){
            CustomLogger.logException(e);
        }
    }

    @Override
    public void disconnectClient() throws Exception {
       clientSocket.close();

    }

    @Override
    public void changeUsername(String user, String newUsername) {
        if(!user.equalsIgnoreCase(newUsername)){
           this.user=newUsername;
        }
    }

    @Override
    public Event listenMessage() {
        Event message = null;
        while(message == null) {

            try {
                message = (Event) inputStream.readObject();
            }
            catch (EOFException serverShutDown){
                try {
                    disconnectClient();
                }catch (Exception e){
                    CustomLogger.logException(e);
                }
            }
            catch (Exception e) {
                CustomLogger.logException(e);
            }
        }
        return message;
    }

    @Override
    public void sendMessage(Event message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        }catch(Exception e){
            CustomLogger.logException(e);
        }
    }

}
