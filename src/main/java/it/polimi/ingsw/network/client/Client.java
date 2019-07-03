package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.LoginMain;


/**
 * It's the Client main class, it will start the selected interface
 *
 * @author Francesco Masciulli
 */
public class Client {

    public static void main(String[] args) {

        RemoteView remoteViewImplementation;
        String gameInterface;
        try {
            gameInterface = args[0];
        } catch (IndexOutOfBoundsException e) {
            //Default: CLI, if no arguments are passed to main()
            gameInterface = "CLI";
        }

        if (gameInterface.equalsIgnoreCase("GUI")) {
            LoginMain.guiMain();
        } else {
            remoteViewImplementation = new CLI();
            remoteViewImplementation.startInterface();
        }
    }


}
