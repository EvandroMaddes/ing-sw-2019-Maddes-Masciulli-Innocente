package it.polimi.ingsw.network;

import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.network.server.rmi.RemoteClient;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

public class RMIClientTest {
    private RMIClient testedClient;



    @Test
    public void testConnection(){

        try {
            ServerInterface testServer = new RemoteClient();
        }catch (RemoteException rmtException){
            System.out.println("Not able to start Server");
        }
        try {
            testedClient = new RMIClient("TestUser");
        }
        catch(RemoteException rmtException) {
            System.out.println("Not able to connect!");
        }
    }

}
