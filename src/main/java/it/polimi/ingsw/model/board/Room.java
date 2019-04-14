package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 */
public class Room {
    private ArrayList<Square> squares;
    private String roomColour;

    /**
     * chiama il metodo getplayer su ogni quadrato
     * @param playersGame who are playing
     * @return i giocatori prendeti in una stanza
     */
    public ArrayList<Player> getRoomPlayers(ArrayList<Player> playersGame)
    {   int i=0;
        ArrayList<Player> playersRoom = null;
        while (i < squares.size()) {
             playersRoom.addAll(squares.get(i).getSquarePlayers(playersGame));
            i++;
        }
        return playersRoom;
    }

    /**
     *
     * @param squares sqares belonging at the room
     */
    public void setSquares(ArrayList<Square> squares) {
        this.squares = squares;
    }

    /**
     * @param roomColour colour of the room
     */
    public void setRoomColour(String roomColour) {
        this.roomColour = roomColour;
    }
}
