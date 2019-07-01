package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.CharacterRequestEvent;
import it.polimi.ingsw.event.controller_view_event.WinnerEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.game_components.cards.*;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class GameManager {

    private Controller controller;
    private RoundManager currentRound;
    private final GameModel model;
    private int playerTurn;
    private boolean finalFrenzyPhase;
    private boolean firstRoundPhase;
    private boolean firsPlayerPlayed;
    private int lastPlayer;
    private DisconnectionManager disconnectionManager;
    private Player currentPlayer;

    /**
     *
     */
    GameManager(Controller controller, int mapChoice){
        this.controller = controller;
        playerTurn = -1;
        firstRoundPhase = true;
        finalFrenzyPhase = false;
        firsPlayerPlayed = false;
        lastPlayer = -2;
        model = new GameModel (buildGameBoard(mapChoice));
        currentPlayer = null;
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
                map = new Map(Map.BIG_LEFT,Map.BIG_RIGHT );
                break;
            }
            case 1:{
                map = new Map(Map.BIG_LEFT,Map.SMALL_RIGHT );
                break;
            }
            case 2:{
                map = new Map(Map.SMALL_LEFT,Map.BIG_RIGHT );
                break;
            }
            case 3:{
                map = new Map(Map.SMALL_LEFT,Map.SMALL_RIGHT );
                break;
            }
            default:{
                throw new InvalidParameterException();
            }
        }
        return new GameBoard(new KillShotTrack(), map, new WeaponDeck(), new AmmoTilesDeck(), new PowerUpDeck());
    }

    public void refillMap(){

        for(int x = 0; x < 3; x++){
            for (int y = 0; y < 4; y++){
                GameBoard gameBoard = getModel().getGameboard();
                if (gameBoard.getMap().getSpawnSquares().contains(gameBoard.getMap().getSquareMatrix()[x][y])){
                    ArrayList<Weapon> newSpawnSquareWeapons = new ArrayList<>();
                    while (((SpawnSquare)gameBoard.getMap().getSquareMatrix()[x][y]).getWeapons().size() < 3 &&
                            newSpawnSquareWeapons.size() + ((SpawnSquare)gameBoard.getMap().getSquareMatrix()[x][y]).getWeapons().size() < 3 &&
                            !gameBoard.getWeaponDeck().getDeck().isEmpty()) {
                        Weapon newWeapon = (Weapon) gameBoard.getWeaponDeck().draw();
                        if (newWeapon != null)
                            newSpawnSquareWeapons.add(newWeapon);
                    }
                    if (!newSpawnSquareWeapons.isEmpty())
                        ((SpawnSquare)gameBoard.getMap().getSquareMatrix()[x][y]).addWeapon(newSpawnSquareWeapons);
                }
                else if ((gameBoard.getMap().getSquareMatrix()[x][y] != null) &&
                        !((BasicSquare)gameBoard.getMap().getSquareMatrix()[x][y]).checkAmmo()) {
                    AmmoTile newAmmoTile = (AmmoTile) gameBoard.getAmmoTilesDeck().draw();
                    if (newAmmoTile.isPowerUpTile())
                        newAmmoTile.setPowerUp((PowerUp) getModel().getGameboard().getPowerUpDeck().draw());
                    ((BasicSquare) gameBoard.getMap().getSquareMatrix()[x][y]).replaceAmmoTile(newAmmoTile);
                }
            }
        }
    }

    void characterSelect(){
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

    public void startGame(){
        SetUpObserverObservable.connect(getDisconnectionManager().getGamePlayers(), controller.getUsersVirtualView(), getModel());
        newRound();
    }

    /**
     * If the gameTrack is ended, set the final frenzy phase
     * Generate a new RoundManager and run it. The RoundManager type depends on three flags:
     * firstRoundPhase - enable only for one round for player
     * finalFrenzyPhase - is enabled when the GameTrack end
     * firstPlayerPlayed - set to true only in the final frenzy, in the first player round
     */
    public void newRound(){
        refillMap();
        if (lastPlayer > getModel().getPlayers().size())
            lastPlayer = getModel().getPlayers().size();

        if(gameEnded() && !isFinalFrenzyPhase())
            setFinalFrenzyPhase();

        if (currentPlayer == null || !getDisconnectionManager().getDisconnectedPlayers().contains(currentPlayer))
            playerTurn++;
        if (playerTurn >= model.getPlayers().size()) {
            firstRoundPhase = false;
            playerTurn = 0;
            if (finalFrenzyPhase)
                firsPlayerPlayed = true;
        }
        currentPlayer = model.getPlayers().get(playerTurn);

        if (getDisconnectionManager().getDisconnectingQueue().contains(currentPlayer)){
            getDisconnectionManager().removePlayer(model.getPlayers().get(playerTurn));
            playerTurn--;
            if (playerTurn < 0)
                playerTurn = model.getPlayers().size() - 1;
            if (model.getPlayers().size() >= 3)
                newRound();
            else
                endGame();
        }
        else {
            if (firstRoundPhase) {
                currentRound = new FirstRoundManager(controller, model.getPlayers().get(playerTurn));
            } else
                currentRound = new RoundManager(controller, model.getPlayers().get(playerTurn));
            currentRound.manageRound();
        }
    }

    private boolean gameEnded(){
        return (model.getGameboard().getGameTrack()).checkEndTrack();
    }

    public void setFinalFrenzyPhase() {
        finalFrenzyPhase = true;
        lastPlayer = playerTurn;
        getModel().getGameboard().setFinalFrenzy();
    }

    /**
     *
     */
    private String calculateWinner() {
        giveEndGamePoints();

        ArrayList<Player> drawPlayers = new ArrayList<>();
        boolean draw = false;
        Player winner = null;
        int winningPoints = 0;
        for (Player p : model.getPlayers()) {
            if(p.getPoints() > winningPoints) {
                winner = p;
                winningPoints = p.getPoints();
                draw = false;
            }
            else if (p.getPoints() == winningPoints){
                drawPlayers.clear();
                drawPlayers.add(winner);
                boolean tockenFound = false;
                for (DamageToken d: ((KillShotTrack)model.getGameboard().getGameTrack()).getTokenTrack()) {
                    if (!tockenFound && (d.getPlayer() == winner || d.getPlayer() == p)) {
                        winner = d.getPlayer();
                        tockenFound = true;
                        drawPlayers.clear();
                    }
                    else if (!drawPlayers.contains(d.getPlayer()))
                        drawPlayers.add(d.getPlayer());
                }
                if (!tockenFound)
                    draw = true;
            }
        }
        if (!draw && winner != null)
            return winner.getCharacter().toString() + " (" + winner.getUsername() + ") win with " + winner.getPoints() + " points!";
        else{
            String drawMessage = new String();
            drawMessage = "Draw ";
            if (!drawPlayers.isEmpty())
                drawMessage += "of ";
            for (Player p: drawPlayers) {
                drawMessage += p.getCharacter().toString() + " ";
            }
            if (!drawPlayers.isEmpty())
                drawMessage += "with " + drawPlayers.get(0).getPoints() + " points";
            return drawMessage;
        }
    }

    public RoundManager getCurrentRound() {
        return currentRound;
    }

    int getPlayerTurn() {
        return playerTurn;
    }

    public boolean isFinalFrenzyPhase() {
        return finalFrenzyPhase;
    }

    int getLastPlayer() {
        return lastPlayer;
    }

    public boolean isFirstRoundPhase() {
        return firstRoundPhase;
    }

    public boolean isFirsPlayerPlayed() {
        return firsPlayerPlayed;
    }

    public DisconnectionManager getDisconnectionManager() {
        if (this.disconnectionManager == null)
            disconnectionManager = new DisconnectionManager(controller);
        return disconnectionManager;
    }

    private void giveEndGamePoints(){
        ArrayList<Player> playerList;
        if (disconnectionManager != null)
            playerList = getDisconnectionManager().getGamePlayers();
        else
            playerList = getModel().getPlayers();
        for (Player p:playerList) {
            collectPlayerBoardPoints(p);
        }
        model.getGameboard().getGameTrack().collectGameTrackPoints();
    }

    void collectPlayerBoardPoints(Player evaluatedPlayer){
        ArrayList<Player> playersList;
        if (disconnectionManager != null)
            playersList = getDisconnectionManager().getGamePlayers();
        else
            playersList = getModel().getPlayers();

        int[] damageDealed = new int[playersList.size()];
        Player[] damageDealer = new Player[playersList.size()];

        for (int i = 0; i < damageDealed.length; i++)
            damageDealed[i] = 0;

        if (evaluatedPlayer.getPlayerBoard().getDamageAmount() == 0)
            return;
        //aggiunge il punto del primo danno
        evaluatedPlayer.getPlayerBoard().getDamageReceived()[0].getPlayer().addPoints(1);

        //aggiunge i punti in base a chi ha fatto più danni e al numero di teschi
        for (int j = 0; j < evaluatedPlayer.getPlayerBoard().getDamageAmount(); j++){
            DamageToken d = evaluatedPlayer.getPlayerBoard().getDamageReceived()[j];
            int i = 0;
            while (damageDealed[i] != 0 && damageDealer[i] != d.getPlayer() )
                i++;
            if (damageDealed[i] == 0)
                damageDealer[i] = d.getPlayer();
            damageDealed[i]++;
        }

        for(int i = 0; i < damageDealed.length && damageDealed[i] != 0; i++){
            int max = 0;
            for(int j = 0; j < damageDealed.length && damageDealed[j] != 0; j++) {
                if (damageDealed[j] > max && damageDealed[j] <= 12)
                    max = damageDealed[j];
            }
            int currentMaxDamager = 0;
            while (damageDealed[currentMaxDamager] != max && damageDealed[currentMaxDamager] != 0)
                currentMaxDamager++;
            damageDealed[currentMaxDamager] = 100;

            if (evaluatedPlayer.getPlayerBoard().getSkullsNumber() + i < PlayerBoard.POINTS.length)
                damageDealer[currentMaxDamager].addPoints(PlayerBoard.POINTS[i + evaluatedPlayer.getPlayerBoard().getSkullsNumber()]);
            else
                damageDealer[currentMaxDamager].addPoints(1);
        }
    }

    public void endGame(){
        String endGameMessage = calculateWinner();
        model.endGame(endGameMessage);
    }

    public void setCurrentRound(RoundManager roundManager){
        this.currentRound = roundManager;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
        setFirstRoundPhase(false);
    }

    public void setFirstRoundPhase(boolean firstRoundPhase){
        this.firstRoundPhase = firstRoundPhase;
    }
}
