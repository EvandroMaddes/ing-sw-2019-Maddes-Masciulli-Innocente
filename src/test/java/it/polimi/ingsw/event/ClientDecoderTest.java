package it.polimi.ingsw.event;

import it.polimi.ingsw.event.coder.ClientDecoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class ClientDecoderTest {

    private ClientDecoder testedClientDecoder;
    private String user;
    private Iterator iterator;

    @Before
    public void setUp(){
        user="TestUser";
    }

}
