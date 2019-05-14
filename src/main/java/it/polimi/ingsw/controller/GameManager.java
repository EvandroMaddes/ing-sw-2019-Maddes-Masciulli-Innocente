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
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;

import java.security.InvalidParameterException;

public class GameManager {

    private RoundManager currentRound;
    private int playersReady;
    private final GameModel model;
    private boolean firstRoundPhase;
    private int finalFrenzyPhase;

    /**
     *
     * @param msg is the message from the client with the map preferences
     */
    public GameManager(int mapChoice){
        playersReady = 0;
        firstRoundPhase = true;
        finalFrenzyPhase = 0;
        //todo notificare la creazione della map?
        model = new GameModel (buildGameBoard(mapChoice));
    }

    /**
     *
     * @param msg is the message from the client with the map preferences
     * @return the builded map
     */
    private GameBoard buildGameBoard(int mapChoice){
        Map map;
        switch(mapChoice){
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
        return new GameBoard(new KillShotTrack(),map, new WeaponDeck(), new AmmoTilesDeck(), new PowerUpDeck());
    }

    //todo aggiungere un controllo sul personaggio gia scelto
    public void addPlayer (String user, Character character) {
        Player newPlayer = new Player(user, character);
        if (model.getPlayers().isEmpty()){
            newPlayer.setFirstPlayer();
        }
        model.addPlayer(newPlayer);
    }

    //todo lo start della partita dovrebbe essere gestito da un timer
    public void startGame(){
        playersReady++;
        if (playersReady == model.getPlayers().size() && playersReady >= 3 && playersReady <= 5) {
            currentRound = new FirstRoundManager(model, model.getPlayers().get(0));
            currentRound.manageRound();
        }
    }

    public void newRound(){
        if (firstRoundPhase){
            currentRound = new FirstRoundManager(model, model.getPlayers().get(0));
        }
        else if (finalFrenzyPhase != 0){
            currentRound = new FrenzyRoundManager(model, model.getPlayers().get(finalFrenzyPhase), afterFirstPlayer(finalFrenzyPhase));
        }
        else
            currentRound = new RoundManager(model, model.getPlayers().get(0));

        currentRound.manageRound();
    }

    public boolean afterFirstPlayer(int currentPlayer){
        for (Player p: model.getPlayers()) {
            if (p.isFirstPlayer())
                return model.getPlayers().indexOf(p) <= currentPlayer;
        }
        throw new RuntimeException();
    }

    public boolean gameEnded(){
        return ((KillShotTrack)model.getGameboard().getGameTrack()).checkEndTrack();
    }


    /**
     *
     * @return winner player. In case of draw, return null
     */
    public Player calculateWinner() {
        Player winner = null;
        boolean draw = false;
        for (Player p : model.getPlayers()) {
            if(winner == null || p.getPoints() > winner.getPoints() ) {
                winner = p;
                draw = false;
            }
            else if (p.getPoints() == winner.getPoints()){
                for (DamageToken d: ((KillShotTrack)model.getGameboard().getGameTrack()).getTokenTrack()){
                    if (d.getPlayer() == winner)
                        break;
                    else if (d.getPlayer() == p){
                        winner = p;
                        draw = true;
                        break;
                    }
                }
            }
        }
        if (!draw)
            return winner;
        else{
            return null;
        }
    }


    public RoundManager getCurrentRound() {
        return currentRound;
    }
}
