package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.network.server.ServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIServer {
  /*  private ArrayList<RemoteClient> clientList;


    @Override
    public void runServer() {
        clientList = new ArrayList<>();
        while(clientList.size()<=5) {
            acceptClient();
        }
    }

    @Override
    public void acceptClient() {
        try {
            clientList.add(new RemoteClient());
            Naming.rebind("RMIServer"+clientList.size(), clientList.get(0));
        }
        catch(RemoteException remoteException){

        }
        catch(MalformedURLException malformedURLException){


        }
    }

    @Override
    public void sendBroadcast() {

    }

    @Override
    public void shutDown() {

    }

   */
}
