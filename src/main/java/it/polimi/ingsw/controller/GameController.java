package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class GameController {

    private ArrayList<Player> players;
    private GameModel model;
    private RoundController currentRount;

    public GameController() {
    }

    public void getGameTrackPoints()
    {

    }

    public ArrayList<Player> calculateWinner() {
        return null;
    }

    public boolean checkAviable( Player player, Character character)
    {
        boolean i = true;

        return i;
    }

    public void addPlayer (Player player, Character character)
    {

    }

    public void buildGameboard()
    {

    }

}
