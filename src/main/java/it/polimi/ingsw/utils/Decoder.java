package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Decoder {

    public static ArrayList<PowerUp> decodePowerUps(Player powerUpsOwner, String[] powerUpType, CubeColour[] powerUpColour){
        ArrayList<PowerUp> decodedPowerUps = new ArrayList<>();
        for(int i = 0; i < powerUpType.length; i++){
            for (PowerUp p:powerUpsOwner.getPowerUps()){
                if (p.getName().equals(powerUpType[i]) && p.getColour() == powerUpColour[i]){
                    decodedPowerUps.add(p);
                    powerUpType[i] = "";
                }
            }
        }
        return decodedPowerUps;
    }

    public static Square decodeSquare (int squareX, int squareY, Map map){
        return map.getSquareMatrix()[squareX][squareY];
    }

    public static ArrayList<Object> decodePlayerListAsObject (List<Character> charactersList, List<Player> playerList){
        ArrayList<Object> decodedList = new ArrayList<>();
        for (Player p: playerList){
            if (charactersList.contains(p.getCharacter()))
                decodedList.add(p);
        }
        return decodedList;
    }

    public static Weapon decodePlayerWeapon (Player weaponOwner, String weaponName){
        for (int i = 0; i < weaponOwner.getNumberOfWeapons(); i++){
            if (weaponOwner.getWeapons()[i].getName().equals(weaponName))
                return weaponOwner.getWeapons()[i];
        }
        throw new UnsupportedOperationException("Il giocatore non possiede l'arma");
    }
}
