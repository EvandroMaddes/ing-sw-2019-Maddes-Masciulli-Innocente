package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.PlayerChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.KillShotTrack;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.game_components.cards.AmmoTilesDeck;
import it.polimi.ingsw.model.game_components.cards.PowerUpDeck;
import it.polimi.ingsw.model.game_components.cards.WeaponDeck;
import it.polimi.ingsw.model.player.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

public class GameManager {

    private RoundManager currentRound;
    private int playersReady;
    private final GameModel model;
    private boolean firstRoundPhase;

    public GameManager(GameChoiceEvent msg){
        playersReady = 0;
        firstRoundPhase = true;
        model = new GameModel (buildGameBoard(msg), new ArrayList<>());
    }

    public void getGameTrackPoints() {

    }

    public Player calculateWinner() {
        return null;
    }

    public void addPlayer (PlayerChoiceEvent message) {
        Player newPlayer = new Player(message.getUser(), message.getTargetPlayersOrder().get(0));
        if (model.getPlayers().size() == 0){
            newPlayer.setFirstPlayer();
        }
        model.getPlayers().add(newPlayer);
    }

    public GameBoard buildGameBoard(GameChoiceEvent msg){
        Map map;
        switch(msg.getMap()){
            case 0:{
                map = new Map("leftFirst","rightFirst" );
                break;
            }
            case 1:{
                map = new Map("leftFirst","rightSecond" );
                break;
            }
            case 2:{
                map = new Map("leftSecond","rightFirst" );
                break;
            }
            case 3:{
                map = new Map("leftSecond","rightSecond" );
                break;
            }
            default:{
                throw new InvalidParameterException();
            }
        }
        return new GameBoard(map, new WeaponDeck(), new AmmoTilesDeck(), new PowerUpDeck());
    }

    public void startGame(){
        playersReady++;
        if (playersReady == model.getPlayers().size() && playersReady >= 3 && playersReady <= 5) {
            manageRoundFlow();
        }
    }

    public Player getFirstPlayer() {
        Iterator iterator = model.getPlayers().iterator();
        for (Player p : model.getPlayers()) {
            if (p.isFirstPlayer())
                return p;
        }
        throw new RuntimeException();
    }

    public void manageRoundFlow(){
        Iterator playerIterator = model.getPlayers().iterator();
        Player lastPlayer;
        do{
            Player currentPlayer = (Player)playerIterator;
            if (firstRoundPhase){
                currentRound = new FirstRoundManager(model, currentPlayer);
            }
            else{
                currentRound = new RoundManager(model, currentPlayer);
            }
            lastPlayer = currentPlayer;
        } while (playerIterator.hasNext() || gameEnded());

        if(firstRoundPhase){
            firstRoundPhase = false;
        }

        if (!gameEnded()){
            manageRoundFlow();
        }
        else {
            manageFinalFrenzy(lastPlayer);
        }
    }

    public boolean gameEnded(){
        return ((KillShotTrack)model.getGameboard().getTrack()).checkEndTrack();
    }

    public void manageFinalFrenzy(Player lastPlayer){
        int firstFenzyPlayer = model.getPlayers().indexOf(lastPlayer) + 1;
        boolean firstPlayerPassed = false;

        if (firstFenzyPlayer > 4){
            firstFenzyPlayer = 0;
        }
        if (lastPlayer.isFirstPlayer()){
            firstPlayerPassed = true;
        }
        for (int player = firstFenzyPlayer - 4; player <= 4; player++) {
            if (firstPlayerPassed){
                currentRound = new FrenzyRoundManager(true);
            }
            else{
                currentRound = new FrenzyRoundManager(false);
            }
            if (model.getPlayers().get(player).isFirstPlayer()){
                firstPlayerPassed = true;
            }
        }

    }

}
