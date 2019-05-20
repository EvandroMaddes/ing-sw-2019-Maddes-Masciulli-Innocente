package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.cli.title.Color;

import java.util.Scanner;

public class CLIHandler {
    private static final Scanner inputScanner = new Scanner(System.in);

    public static String printAndRead(String printedString){
        String returnedString = "";
        while(returnedString.isEmpty()) {
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape());
            System.out.println("\n"+printedString+"\t");
            returnedString =inputScanner.nextLine();
        }
        return returnedString;

    }
}
