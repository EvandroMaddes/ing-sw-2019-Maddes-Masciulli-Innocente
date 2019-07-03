package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.CharacterRequestEvent;
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

/**
 * Class to manage the game
 *
 * @author Federico Innocente
 */
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
    GameManager(Controller controller, int mapChoice) {
        this.controller = controller;
        playerTurn = -1;
        firstRoundPhase = true;
        finalFrenzyPhase = false;
        firsPlayerPlayed = false;
        lastPlayer = -2;
        model = new GameModel(buildGameBoard(mapChoice));
        currentPlayer = null;
    }

    /*
     * Methods for the GameBoard setup
     */

    /**
     * Build the GameBoard chosen for the game
     *
     * @return the game GameBoard
     */
    private GameBoard buildGameBoard(int mapChoice) {
        Map map;
        switch (mapChoice) {
            case 0:
                map = new Map(Map.BIG_LEFT, Map.BIG_RIGHT);
                break;
            case 1:
                map = new Map(Map.BIG_LEFT, Map.SMALL_RIGHT);
                break;
            case 2:
                map = new Map(Map.SMALL_LEFT, Map.BIG_RIGHT);
                break;
            case 3:
                map = new Map(Map.SMALL_LEFT, Map.SMALL_RIGHT);
                break;
            default:
                throw new InvalidParameterException();
        }
        return new GameBoard(new KillShotTrack(), map, new WeaponDeck(), new AmmoTilesDeck(), new PowerUpDeck());
    }

    /**
     * Check for the squares with no AmmoTile or not full of Weapons
     */
    public void refillMap() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 4; y++) {
                GameBoard gameBoard = getModel().getGameboard();
                if (gameBoard.getMap().getSpawnSquares().contains(gameBoard.getMap().getSquareMatrix()[x][y]))
                    refillWeapons(x, y);
                else if ((gameBoard.getMap().getSquareMatrix()[x][y] != null) &&
                        !((BasicSquare) gameBoard.getMap().getSquareMatrix()[x][y]).checkAmmo())
                    refillAmmoTile(x, y);
            }
        }
    }

    /**
     * Add the missing weapons to the selected SpawnSquare.
     * If no more weapons are available, don't add anything
     *
     * @param row    is the square's row
     * @param column is the square's column
     */
    private void refillWeapons(int row, int column) {
        GameBoard gameBoard = getModel().getGameboard();
        ArrayList<Weapon> newSpawnSquareWeapons = new ArrayList<>();
        while (((SpawnSquare) gameBoard.getMap().getSquareMatrix()[row][column]).getWeapons().size() < 3 &&
                newSpawnSquareWeapons.size() + ((SpawnSquare) gameBoard.getMap().getSquareMatrix()[row][column]).getWeapons().size() < 3 &&
                !gameBoard.getWeaponDeck().getDeck().isEmpty()) {
            Weapon newWeapon = (Weapon) gameBoard.getWeaponDeck().draw();
            if (newWeapon != null)
                newSpawnSquareWeapons.add(newWeapon);
        }
        if (!newSpawnSquareWeapons.isEmpty())
            ((SpawnSquare) gameBoard.getMap().getSquareMatrix()[row][column]).addWeapon(newSpawnSquareWeapons);
    }

    /**
     * Add an AmmoTile to the selected BasicSquare, which is already checked to not have one
     *
     * @param row    is the square's row
     * @param column is the square's column
     */
    private void refillAmmoTile(int row, int column) {
        GameBoard gameBoard = getModel().getGameboard();
        AmmoTile newAmmoTile = (AmmoTile) gameBoard.getAmmoTilesDeck().draw();
        if (newAmmoTile.isPowerUpTile())
            newAmmoTile.setPowerUp((PowerUp) getModel().getGameboard().getPowerUpDeck().draw());
        ((BasicSquare) gameBoard.getMap().getSquareMatrix()[row][column]).replaceAmmoTile(newAmmoTile);
    }



    /*
     * Method for add players to the game
     */

    /**
     * Scroll every player connected to the game and ask one by one to choose their Character.
     * A check on already picked character is done to not allowed players to pick the same character
     */
    void characterSelect() {
        if (getModel().getPlayers().size() < controller.getUsersVirtualView().size()) {
            ArrayList<Character> availableCharacter = new ArrayList<>();
            availableCharacter.add(Character.BANSHEE);
            availableCharacter.add(Character.D_STRUCT_OR);
            availableCharacter.add(Character.DOZER);
            availableCharacter.add(Character.SPROG);
            availableCharacter.add(Character.VIOLET);
            for (Player p : getModel().getPlayers()) {
                availableCharacter.remove(p.getCharacter());
            }
            String[] usernames = new String[controller.getUsersVirtualView().size()];
            controller.getUsersVirtualView().keySet().toArray(usernames);
            controller.callView(new CharacterRequestEvent(usernames[getModel().getPlayers().size()], availableCharacter));
        } else
            startGame();
    }

    /**
     * Create the new player into the game model of the game
     *
     * @param user      is the player username
     * @param character is the chosen character
     */
    public void addPlayer(String user, Character character) {
        Player newPlayer = new Player(user, character);
        if (model.getPlayers().isEmpty()) {
            newPlayer.setFirstPlayer();
        }
        model.addPlayer(newPlayer);
        characterSelect();
    }



    /*
     * Methods to manage the game flow
     */

    /**
     * Call the setting of the observers and start the game
     */
    void startGame() {
        SetUpObserverObservable.connect(getDisconnectionManager().getGamePlayers(), controller.getUsersVirtualView(), getModel());
        newRound();
    }

    /**
     * Generate a new RoundManager for the next player based on the game state and run it.
     * If the player who should play the round is in the disconnection queue, call his remove from the game.
     */
    public void newRound() {
        refillMap();
        if (lastPlayer > getModel().getPlayers().size())
            lastPlayer = getModel().getPlayers().size();

        if (gameEnded() && !isFinalFrenzyPhase())
            setFinalFrenzyPhase();

        if (playerTurn < 0 || !getDisconnectionManager().getDisconnectedPlayers().contains(currentPlayer))
            playerTurn++;
        if (playerTurn >= model.getPlayers().size()) {
            firstRoundPhase = false;
            playerTurn = 0;
            if (finalFrenzyPhase)
                firsPlayerPlayed = true;
        }
        currentPlayer = model.getPlayers().get(playerTurn);

        if (getDisconnectionManager().getDisconnectingQueue().contains(currentPlayer)) {
            manageDisconnectedPlayer();
        } else {
            if (firstRoundPhase)
                currentRound = new FirstRoundManager(controller, model.getPlayers().get(playerTurn));
            else
                currentRound = new RoundManager(controller, model.getPlayers().get(playerTurn));
            currentRound.manageRound();
        }
    }

    /**
     * Set the final frenzy phase and the player who will play the last round
     */
    private void setFinalFrenzyPhase() {
        finalFrenzyPhase = true;
        lastPlayer = playerTurn;
        getModel().getGameboard().setFinalFrenzy();
    }

    /**
     * This method is called when the player who should play the new round is in the disconnection queue: remove the player from the game.
     * If only 2 or less players are still connected, terminate the game; if they are 3 or more, generate the round for the next player.
     */
    private void manageDisconnectedPlayer() {
        getDisconnectionManager().removePlayer(model.getPlayers().get(playerTurn));
        playerTurn--;
        if (playerTurn < 0)
            playerTurn = model.getPlayers().size() - 1;
        getDisconnectionManager().roundFlowManaging();
    }

    /**
     * Check if the game is over
     *
     * @return true if the game is over
     */
    private boolean gameEnded() {
        return (model.getGameboard().getGameTrack()).checkEndTrack();
    }

    /**
     * Set the game over flag and send the winnig message to the players
     */
    void endGame() {
        String endGameMessage = calculateWinner();
        model.endGame(endGameMessage);
    }

    /*
     * Methods to determinate the winner
     */

    /**
     * Calculate the winner
     *
     * @return the message to send to the players
     */
    private String calculateWinner() {
        giveEndGamePoints();

        ArrayList<Player> drawPlayers = new ArrayList<>();
        boolean draw = false;
        Player winner = null;
        int winningPoints = 0;
        for (Player p : model.getPlayers()) {
            if (p.getPoints() > winningPoints) {
                winner = p;
                winningPoints = p.getPoints();
                draw = false;
            } else if (p.getPoints() == winningPoints) {
                drawPlayers.clear();
                if (winner != null)
                    drawPlayers.add(winner);
                boolean tockenFound = false;
                for (DamageToken d : ((KillShotTrack) model.getGameboard().getGameTrack()).getTokenTrack()) {
                    if (!tockenFound && (d.getPlayer() == winner || d.getPlayer() == p)) {
                        winner = d.getPlayer();
                        tockenFound = true;
                        drawPlayers.clear();
                    } else if (!drawPlayers.contains(d.getPlayer()))
                        drawPlayers.add(d.getPlayer());
                }
                if (!tockenFound)
                    draw = true;
            }
        }
        if (!draw && winner != null)
            return winner.getCharacter().toString() + " (" + winner.getUsername() + ") win with " + winner.getPoints() + " points!";
        else {
            return generateDrawMessage(drawPlayers);
        }
    }

    /**
     * Generate the message in case of a tie
     *
     * @param drawPlayers are the players who tie the game
     * @return the message to send to the players
     */
    private String generateDrawMessage(ArrayList<Player> drawPlayers) {
        String drawMessage;
        drawMessage = "Draw ";
        if (!drawPlayers.isEmpty()) {
            drawMessage = drawMessage.concat("of ");
            for (Player p : drawPlayers) {
                drawMessage = drawMessage.concat(p.getCharacter().toString() + " (" + p.getUsername() + "), ");
            }
            drawMessage = drawMessage.concat("with " + drawPlayers.get(0).getPoints() + " points");
        }
        return drawMessage;
    }

    /**
     * Give to the players the points on the player boards at their actual state when the game is over
     */
    private void giveEndGamePoints() {
        ArrayList<Player> playerList;
        if (disconnectionManager != null)
            playerList = getDisconnectionManager().getGamePlayers();
        else
            playerList = getModel().getPlayers();
        for (Player p : playerList) {
            collectPlayerBoardPoints(p);
        }
        model.getGameboard().getGameTrack().collectGameTrackPoints();
    }

    /**
     * Collect the points on a player board
     *
     * @param evaluatedPlayer is the player which player board is evaluated
     */
    void collectPlayerBoardPoints(Player evaluatedPlayer) {
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
        for (int j = 0; j < evaluatedPlayer.getPlayerBoard().getDamageAmount(); j++) {
            DamageToken d = evaluatedPlayer.getPlayerBoard().getDamageReceived()[j];
            int i = 0;
            while (damageDealed[i] != 0 && damageDealer[i] != d.getPlayer())
                i++;
            if (damageDealed[i] == 0)
                damageDealer[i] = d.getPlayer();
            damageDealed[i]++;
        }

        for (int i = 0; i < damageDealed.length && damageDealed[i] != 0; i++) {
            int max = 0;
            for (int j = 0; j < damageDealed.length && damageDealed[j] != 0; j++) {
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



    /*
     * Getter Methods
     */

    /**
     * Getter method
     *
     * @return the GameModel associated with the game
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * Getter method
     *
     * @return the current round manager
     */
    public RoundManager getCurrentRound() {
        return currentRound;
    }

    /**
     * Getter method
     *
     * @return the player who is currently playing the round
     */
    int getPlayerTurn() {
        return playerTurn;
    }

    /**
     * Getter method
     *
     * @return the final frenzy phase flag
     */
    public boolean isFinalFrenzyPhase() {
        return finalFrenzyPhase;
    }

    /**
     * Getter method
     *
     * @return the value associated with the last player
     */
    int getLastPlayer() {
        return lastPlayer;
    }

    /**
     * Getter method
     *
     * @return the first round phase flag
     */
    public boolean isFirstRoundPhase() {
        return firstRoundPhase;
    }

    /**
     * Getter method
     *
     * @return the first player played flag
     */
    public boolean isFirsPlayerPlayed() {
        return firsPlayerPlayed;
    }

    /**
     * Getter method for the disconnection manager. If it is not instanced, generate one and return it.
     *
     * @return the disconnection manager
     */
    public DisconnectionManager getDisconnectionManager() {
        if (this.disconnectionManager == null)
            disconnectionManager = new DisconnectionManager(controller);
        return disconnectionManager;
    }



    /*
     * Setter Methods
     */

    /**
     * Setter method
     * Set the first round phase flag to false
     */
    private void setFirstRoundPhase() {
        firstRoundPhase = false;
    }

    /**
     * Setter method
     *
     * @param playerTurn is the player who is playing the current round
     */
    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
        setFirstRoundPhase();
    }

    /**
     * Setter method
     *
     * @param roundManager is the new RoundManager
     */
    public void setCurrentRound(RoundManager roundManager) {
        this.currentRound = roundManager;
    }
}
