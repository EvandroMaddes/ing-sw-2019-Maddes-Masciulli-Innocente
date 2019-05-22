package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.view.cli.graph.Color;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIHandler {
    private static final Scanner inputScanner = new Scanner(System.in);

    public static String printAndRead(String printedString){
        String returnedString = "";
        System.out.print(Color.RESET.escape());
        while(returnedString.isEmpty()) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_GREEN.escape()+"\n"+printedString+"\t");
            System.out.flush();
            returnedString =inputScanner.nextLine();
        }
        return returnedString;
    }

    public static String arrayPrintAndRead(ArrayList<String> printedStrings){
        String returnedString = "";
        for (String currObject: printedStrings) {
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+currObject+"\t\t");
            System.out.flush();
        }
        while(returnedString.isEmpty()) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_GREEN.escape()+"\nSelect from " +
                    "available choices:\t");
            System.out.flush();
            returnedString =inputScanner.nextLine();
        }
        return returnedString;
    }

}
