package it.polimi.ingsw.utils;

import java.net.ConnectException;

public class CustomConnectException extends ConnectException {
    public CustomConnectException() {
        super("Couldn't reach the server!");
    }
}
