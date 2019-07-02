package it.polimi.ingsw.utils;

import java.net.ConnectException;

/**
 * Custom Exception thrown by ClientInterface implementations, implicate the client shut-down
 * @author Francesco Masciulli
 */
public class CustomConnectException extends ConnectException {
    public CustomConnectException() {
        super("Couldn't reach the server!");
    }
}
