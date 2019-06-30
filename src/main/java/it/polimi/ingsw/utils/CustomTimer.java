package it.polimi.ingsw.utils;

import java.util.concurrent.TimeUnit;

public class CustomTimer extends Thread {

    private int gameWaitingTime;

    /**
     * Constructor
     * @param gameWaitingTime is the Timer time unit, expressed in SECONDS
     */
    public CustomTimer(int gameWaitingTime){
        this.gameWaitingTime = gameWaitingTime;


    }

    /**
     * This method start the sleep of this thread, for the gameWaitingTime Seconds, and then interrupt it;
     * checking the isAlive() value, the Lobby.main() could see if the Timer is elapsed
     */
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(gameWaitingTime);
        }catch (InterruptedException e){
            interrupt();
        }
        interrupt();
    }




}
