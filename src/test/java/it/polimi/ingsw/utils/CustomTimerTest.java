package it.polimi.ingsw.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class CustomTimerTest {


    @Test
    public void gameTimerTest(){
        CustomTimer gameTimer = new CustomTimer(1);
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
