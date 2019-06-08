package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Encoder {

    public static ArrayList<Character> encodePlayerTargets(List<Player> targets){
        ArrayList<Character> targetsLite = new ArrayList<>();
        for (Player p: targets) {
            targetsLite.add(p.getCharacter());
        }
        return targetsLite;
    }

    public static int[] encodeSquareTargetsX(List<Square> targets){
        int[] targetsX = new int[targets.size()];
        for(int i = 0 ; i < targets.size(); i++){
            targetsX[i] = targets.get(i).getColumn();
        }
        return targetsX;
    }

    public static int[] encodeSquareTargetsY(List<Square> targets){
        int[] targetsY = new int[targets.size()];
        for(int i = 0 ; i < targets.size(); i++){
            targetsY[i] = targets.get(i).getRow();
        }
        return targetsY;
    }

    public static ArrayList<String> encodeWeaponsList(List<Weapon> weapons) {
        ArrayList<String> weaponsLite = new ArrayList<>();
        for (Weapon w:weapons){
            weaponsLite.add(w.getName());
        }
        return weaponsLite;
    }

    public static String[] encodeWeaponsIntoArray(List<Weapon> weapons){
        String[] weaponsLite = new String[weapons.size()];
        for (int i = 0; i < weapons.size(); i++){
            weaponsLite[i] = weapons.get(i).getName();
        }
        return weaponsLite;
    }

    public static String[] encodePowerUpsType (List<PowerUp> powerUps){
        String[] powerUpsType = new String[powerUps.size()];
        for (int i = 0; i < powerUps.size(); i++) {
            powerUpsType[i] = powerUps.get(i).getName();
        }
        return powerUpsType;
    }

    public static CubeColour[] encodePowerUpColour (List<PowerUp> powerUps){
        CubeColour[] powerUpsColour = new CubeColour[powerUps.size()];
        for (int i = 0; i < powerUps.size(); i++) {
            powerUpsColour[i] = powerUps.get(i).getColour();
        }
        return powerUpsColour;
    }

    public static Character[] encodeDamageTokenArrayList(List<DamageToken> damageTokensList){
        Character[] marksLite = new Character[damageTokensList.size()];
        for (int i = 0; i < damageTokensList.size(); i++){
            marksLite[i] = damageTokensList.get(i).getPlayer().getCharacter();
        }
        return marksLite;
    }

    public static Character[] encodeDamages (DamageToken[] damages, int numberOfDamages){
        Character[] damagesLite = new Character[numberOfDamages];
        for (int i = 0; i < numberOfDamages; i++){
            damagesLite[i] = damages[i].getPlayer().getCharacter();
        }
        return damagesLite;
    }
}
