package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.CharacterRequestEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.game_components.cards.AmmoTilesDeck;
import it.polimi.ingsw.model.game_components.cards.PowerUpDeck;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.WeaponDeck;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Set;

public class GameManager {

    private Controller controller;
    private RoundManager currentRound;
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
        playerTurn = -1;
        firstRoundPhase = true;
        finalFrenzyPhase = false;
        firsPlayerPlayed = false;
        lastPlayer = -2;
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

    public void characterSelect(){
        if (getModel().getPlayers().size() < controller.getUsersVirtualView().size()){
            ArrayList<Character> availableCharacter = new ArrayList<>();
            availableCharacter.add(Character.BANSHEE);
            availableCharacter.add(Character.D_STRUCT_OR);
            availableCharacter.add(Character.DOZER);
            availableCharacter.add(Character.SPROG);
            availableCharacter.add(Character.VIOLET);
            for (Player p: getModel().getPlayers()){
                availableCharacter.remove(p.getCharacter());
            }
            String[] usernames = new String[controller.getUsersVirtualView().size()];
            controller.getUsersVirtualView().keySet().toArray(usernames);
            controller.callView(new CharacterRequestEvent(usernames[getModel().getPlayers().size()], availableCharacter));
        }
        else
            startGame();
    }

    public void addPlayer (String user, Character character) {
        Player newPlayer = new Player(user, character);
        if (model.getPlayers().isEmpty()){
            newPlayer.setFirstPlayer();
        }
        model.addPlayer(newPlayer);
        characterSelect();
    }

    private void startGame(){
        SetUpObserverObservable.connect(getModel().getPlayers(), controller.getUsersVirtualView(), getModel().getGameboard());
        newRound();
    }

    /**
     * If the gameTrack is ended, set the final frenzy phase
     * Generate a new RoundManager and run it. The RoundManager type depends on three flags:
     * firstRoundPhase - enable only fo one round for player
     * finalFrenzyPhase - is enabled when the GameTrack end
     * firstPlayerPlayed - set to true only in the final frenzy, in the first player round
     */
    public void newRound(){
        refillMap();

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

    private void refillMap(){
        for(int x = 0; x < 4; x++){
            for (int y = 0; y < 3; y++){
                GameBoard gameBoard = controller.getGameManager().getModel().getGameboard();
                if (gameBoard.getMap().getSpawnSquares().contains(gameBoard.getMap().getSquareMatrix()[x][y])){
                    if (((SpawnSquare)gameBoard.getMap().getSquareMatrix()[x][y]).getWeapons().size() < 3 &&
                        !gameBoard.getWeaponDeck().getDeck().isEmpty())
                        ((SpawnSquare)gameBoard.getMap().getSquareMatrix()[x][y]).getWeapons().add((Weapon) gameBoard.getWeaponDeck().draw());
                }
                else
                    if (!((BasicSquare)gameBoard.getMap().getSquareMatrix()[x][y]).checkAmmo())
                        ((BasicSquare)gameBoard.getMap().getSquareMatrix()[x][y]).replaceAmmoTile((AmmoTile) gameBoard.getAmmoTilesDeck().draw());
            }
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

    public boolean isFirstRoundPhase() {
        return firstRoundPhase;
    }

    public boolean isFirsPlayerPlayed() {
        return firsPlayerPlayed;
    }
}
