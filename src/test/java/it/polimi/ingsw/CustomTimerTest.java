package it.polimi.ingsw;

import it.polimi.ingsw.utils.CustomTimer;
import it.polimi.ingsw.utils.NetConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Date;

public class CustomTimerTest {


    @Test
    public void gameTimerTest(){
        CustomTimer gameTimer = new CustomTimer(NetConfiguration.STARTGAMETIMER);
        gameTimer.start();
        System.out.println("Timer started at:\t"+ LocalTime.now());
        try{
            gameTimer.join();

        }catch(InterruptedException e){
        }
        Assert.assertTrue(!gameTimer.isAlive());
        System.out.println("CustomTimer terminated!\nTimer interrupted at:\t"+ LocalTime.now());

    }
}
