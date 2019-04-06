package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;
import java.util.ArrayList;

public class GameController {

    private GameController gameController;
    private ArrayList<Player> players;
    private GameBoard gameBoard;
    private RoundController currentRount;

    private GameController()
    {

    }

    public GameController instance()
    {
        GameController i = null;

        return i;
    }

    public void inizialize()
    {

    }

    public void getGameTrackPoints()
    {

    }

    public ArrayList<Player> winner()
    {
        ArrayList<Player> i = null;

        return i;
    }

    public boolean checkAviable( Player player, Character character)
    {
        boolean i = true;

        return i;
    }

    public void addPlayer (Player player, Character character)
    {

    }

    public void buldGameboard()
    {

    }

}
