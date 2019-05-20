package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.CLIMap;
import it.polimi.ingsw.view.cli.graph.Color;
import it.polimi.ingsw.view.cli.graph.Title;

import java.util.Scanner;
//todo deve resettare i colori del terminale!
public class CLI extends RemoteView {

    private Scanner inputScanner = new Scanner(System.in);
    private CLIMap map = new CLIMap();

/*    public CLI(String user) {
        super(user);
    }


 */
    @Override
    public String[] gameInit() {
        Title.printTitle();
        String[] userInput = new String[3];
        userInput[0] = CLIHandler.printAndRead("Insert your Username:");
        setUser(userInput[0]);
        userInput[1]="";
        while(!(userInput[1].equalsIgnoreCase("RMI")||userInput[1].equalsIgnoreCase("SOCKET"))) {
            userInput[1] = CLIHandler.printAndRead("Choose one of the available connection, type:\n\nRMI\tor\tSOCKET\n");
        }
        userInput[2] = CLIHandler.printAndRead("Insert the Server IP Address:");
        return userInput;
    }

    @Override
    public void printScreen() {
        map.plot();
    }
}
