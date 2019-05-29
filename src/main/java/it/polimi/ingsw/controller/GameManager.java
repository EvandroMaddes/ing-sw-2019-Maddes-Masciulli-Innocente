package it.polimi.ingsw.controller;

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

    private Controller controller;
    private RoundManager currentRound;
    private int playersReady;
    private final GameModel model;
    private int playerTurn;
    private boolean finalFrenzyPhase;
    private boolean firstRoundPhase;
    private boolean firsPlayerPlayed;
    private int lastPlayer;

    /**
     *
     */
    public GameManager(Controller controller, int mapChoice){
        this.controller = controller;
        playersReady = 0;
        playerTurn = -1;
        firstRoundPhase = true;
        finalFrenzyPhase = false;
        firsPlayerPlayed = false;
        lastPlayer = -2;
        //todo notificare la creazione della map?
        model = new GameModel (buildGameBoard(mapChoice));
    }

    public GameModel getModel() {
        return model;
    }

    /**
     *
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
            newRound();
        }
    }

    /**
     * If the gameTrack is ended, set the final frenzy phase
     * Generate a new RoundManager and run it. The RoundManager type depends on three flags:
     * firstRoundPhase - enable only fo one round for player
     * finalFrenzyPhase - is enabled when the GameTrack end
     * firstPlayerPlayed - set to true only in the final frenzy, in the first player round
     */
    public void newRound(){
        if(gameEnded())
            setFinalFrenzyPhase();

        playerTurn++;
        if (playerTurn == model.getPlayers().size()) {
            firstRoundPhase = false;
            playerTurn = 0;
            if (finalFrenzyPhase)
                firsPlayerPlayed = true;
        }

        if (firstRoundPhase){
            currentRound = new FirstRoundManager(controller, model,this, model.getPlayers().get(playerTurn));
        }
        else if (finalFrenzyPhase){
            currentRound = new FrenzyRoundManager(controller, model, this, model.getPlayers().get(playerTurn), firsPlayerPlayed);
        }
        else
            currentRound = new RoundManager(controller, model, this, model.getPlayers().get(playerTurn));

        currentRound.manageRound();
    }

    public boolean gameEnded(){
        return (model.getGameboard().getGameTrack()).checkEndTrack();
    }

    public void setFinalFrenzyPhase() {
        if (!finalFrenzyPhase){
            finalFrenzyPhase = true;
            lastPlayer = playerTurn;
        }
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

    public int getPlayerTurn() {
        return playerTurn;
    }

    public boolean isFinalFrenzyPhase() {
        return finalFrenzyPhase;
    }

    public int getLastPlayer() {
        return lastPlayer;
    }
}
