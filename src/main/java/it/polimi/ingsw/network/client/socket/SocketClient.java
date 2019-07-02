package it.polimi.ingsw.network.client.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.custom_exceptions.CustomConnectException;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

/**
 * This is the Socket Network implementation
 * @author Francesco Masciulli
 */
public class SocketClient implements ClientInterface {
    private String user;
    private String serverIPAddress;
    private int serverPort;
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean connected = false;

   public SocketClient(String user, String serverIPAddress) throws ConnectException{
        this.user=user;
        this.serverIPAddress=serverIPAddress;
        this.serverPort = NetConfiguration.SOCKETSERVERPORTNUMBER;
        connectClient();
   }

    /**
     * Getter method:
     * @return connected value
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * ClientInterface's reconnectClient() implementation
     * This method reconnect the client to the requested port, on the same server
     */

    @Override
    public void reconnectClient() {
        try{
            disconnectClient();
            connectClient();
        }catch(Exception e){
            CustomLogger.logException(e);
        }
    }

    /**
     * ClientInterface's Setter method implementation:
     * set serverPort attribute
     * @param serverPort is the server port number
     */
    @Override
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * ClientInterface method implementation:
     * this method handle the lookup and the creation of the client RemoteRegistry
     * calling the method on the server to add the Remote reference to this last created registry;
     * @throws ConnectException if couldn't connect properly
     */
    @Override
    public synchronized void connectClient() throws ConnectException{

        try{
            clientSocket = new Socket(serverIPAddress, serverPort);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream.writeUTF(user);
            outputStream.flush();
            connected = true;
        }catch(Exception e){
            CustomLogger.logException(e);
            throw new CustomConnectException();
        }
    }

    /**
     * Disconnect the client, closing the socket;
     * @throws Exception if couldn't close the socket properly
     */
    @Override
    public void disconnectClient() throws Exception {
       connected = false;
        clientSocket.close();

    }

    /**
     * this method update the username after a modification request
     * @param user is the old username
     * @param newUsername is the updated username
     */
    @Override
    public void changeUsername(String user, String newUsername) {
        if(!user.equalsIgnoreCase(newUsername)){
           this.user=newUsername;
        }
    }

    /**
     * ClientInterface's listenMessage implementation
     * @return the listened message, null if no message was retrieved
     */
    @Override
    public Event listenMessage() {
        Event message = null;
        while(message == null) {

            try {
                message = (Event) inputStream.readObject();
            }
            catch (EOFException| SocketException serverShutDown){
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

    /**
     * ClientInterface's sendMessage implementation
     * @param message is the message that must be sent
     */
    @Override
    public void sendMessage(Event message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        }catch(Exception e){
            CustomLogger.logException(e);
            try {
                disconnectClient();

            }catch (Exception disconnectionException){
                CustomLogger.logException(disconnectionException);
            }
        }
    }

}
