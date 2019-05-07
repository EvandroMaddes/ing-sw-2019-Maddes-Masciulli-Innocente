package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.PlayerChoiceEvent;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<Player> players;
    private RoundManager currentRount;

    public GameManager(GameChoiceEvent message){
        players = new ArrayList<Player>();

        switch (message.getMap()){
            case ...:{

            }
        }
    }

    public void getGameTrackPoints() {

    }

    public ArrayList<Player> calculateWinner() {
        return null;
    }

    public boolean checkAviable( Character character)
    {

    }

    public void addPlayer (PlayerChoiceEvent message) {
        Player newPlayer = new Player(message.getUser(), message.getTargetPlayersOrder().get(0));
        players.add(newPlayer);
    }

    public void buildGameboard()
    {

    }

}
