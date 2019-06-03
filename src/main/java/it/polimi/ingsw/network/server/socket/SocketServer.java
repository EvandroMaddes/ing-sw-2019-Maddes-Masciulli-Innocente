package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.NetConfiguration;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;

public class SocketServer extends Thread implements ServerInterface {
    private ServerSocket serverSocket;
    private CopyOnWriteArrayList<SocketServerThread> socketList = new CopyOnWriteArrayList<>();
    private Queue<Event> messageQueue = new SynchronousQueue<>();
    private boolean gameCouldStart = false;



    @Override
    public void run(){
        runServer();
        while(!gameCouldStart){
            acceptClient();
        }
    }


    public ArrayList<String> getClientList() {
        ArrayList<String> clientUserList = new ArrayList<>();
        Iterator iterator = socketList.iterator();
        while(iterator.hasNext()){
            SocketServerThread currClientThread = (SocketServerThread) iterator.next();
            clientUserList.add(currClientThread.getClientUser());
        }
        return clientUserList;
    }

    @Override
    public void gameCouldStart() {
        gameCouldStart=true;
    }

    @Override
    public void updateUsername(String username, String newUser) {
        boolean isFirstOccurence = true;
        for (int i = socketList.size()-1; i >=0 ; i--) {
            SocketServerThread currSocketThread = socketList.get(i);
            if(currSocketThread.getClientUser().equalsIgnoreCase(username)){
                currSocketThread.setClientUser(newUser);
                return;
            }
        }
    }

    @Override
    public void runServer() {

        try{
        serverSocket = new ServerSocket(NetConfiguration.SOCKETSERVERPORTNUMBER);
        }catch(IOException e){
            CustomLogger.logException(e);
        }



    }

    @Override
    public void acceptClient() {
        if (socketList.size()==5) {
            gameCouldStart = true;
        }
        else {
            try {
                Socket clientSocket = serverSocket.accept();
                SocketServerThread clientSocketThread = new SocketServerThread(clientSocket);
                clientSocketThread.start();

                socketList.add(clientSocketThread);

            } catch (IOException e) {
                CustomLogger.logException(e);
            }
        }
    }

    @Override
    public void sendBroadcast(Event message) {
        for (SocketServerThread currThread: socketList) {
            currThread.sendMessage(message);
        }
    }
    //todo non si arresta run() prima che si chiudano i socket a seguito di Server.disconnect();
    @Override
    public void shutDown() {
        for (SocketServerThread currThread: socketList) {
            try{
                Socket currSocket = currThread.getClient();
                currThread.disconnect();
                currSocket.shutdownInput();
                currSocket.shutdownOutput();
                currSocket.close();
            }catch(IOException e){
                CustomLogger.logException(e);
            }
        }
    }

    @Override
    public void sendMessage(Event message) {

        for (int i = socketList.size()-1; i >= 0 ; i--) {
            if(socketList.get(i).getClientUser().equals(message.getUser())){

                socketList.get(i).sendMessage(message);
                return;
            }
        }

    }

    @Override
    public Event listenMessage() {
        Event currMessage;
        for (int i = 0; i < socketList.size() ; i++) {
            SocketServerThread currSocket = socketList.get(i);
            if(currSocket.getCurrMessage()!=null) {
                currMessage = currSocket.getCurrMessage();
                currSocket.resetMessage();
                return currMessage;
//                messageQueue.offer(currMessage);
//                currSocket.resetMessage();

            }
            //todo inoltre farà partire timer??
            else if (i == socketList.size()-1){
                i = -1;
            }
        }
        return null;




    }
}
