package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.event.ErrorEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetConfiguration;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;

public class SocketServer extends Thread implements ServerInterface {
    private ServerSocket serverSocket;
    private CopyOnWriteArrayList<SocketServerThread> socketList = new CopyOnWriteArrayList<>();
    private BlockingQueue<Event> messageQueue = new ArrayBlockingQueue<>(5);
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
    public void runServer() {

        try{
        serverSocket = new ServerSocket(NetConfiguration.SOCKETSERVERPORTNUMBER);
        }catch(IOException e){
            CustomLogger.logException(e);
        }



    }

    //todo accetta un solo client
    @Override
    public void acceptClient() {
        if (socketList.size()==5) {
            gameCouldStart = true;
        } else {
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
    //todo non si arresta run() prima che si chiudano i socket
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
        for (SocketServerThread currentThread :socketList)
        {
            if(currentThread.getClientUser().equals(message.getUser())){

                currentThread.sendMessage(message);
            }
        }
    }

    @Override
    public Event listenMessage() {
        Event currMessage = null;
        for (SocketServerThread currSocket: socketList) {
            while (currMessage == null) {
                currMessage = currSocket.getCurrMessage();
                if (!messageQueue.contains(currMessage)) {

                    try {
                        messageQueue.put(currMessage);
                    } catch (Exception e) {
                        CustomLogger.logException(e);
                    }
                }
            }
        }
        try {
            return messageQueue.take();
        }catch(InterruptedException e){
            CustomLogger.logException(e);
            return null;
        }


    }
}
