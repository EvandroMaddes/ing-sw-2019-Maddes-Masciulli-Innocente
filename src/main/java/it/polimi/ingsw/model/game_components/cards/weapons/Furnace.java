package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class Furnace extends AlternateFireWeapon {

    public Furnace() {
        super(CubeColour.Red, "FURNACE",
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{});
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        ArrayList<Player> roomPlayers = ((Square)targets.get(0)).findRoomPlayers();
        for (Player p:roomPlayers){
            damage(p, 1);
            getDamagedPlayer().add(p);
        }
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Square> possibleTarget = new ArrayList<>();
        for (int i = 0; i < 4; i ++){
            if (getOwner().getPosition().checkDirection(i) && getOwner().getPosition().getNextSquare(i).getSquareColour() != getOwner().getPosition().getSquareColour() && !getOwner().getPosition().getNextSquare(i).findRoomPlayers().isEmpty())
                possibleTarget.add(getOwner().getPosition().getNextSquare(i));
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTarget), Encoder.encodeSquareTargetsY(possibleTarget));
    }

    @Override
    public boolean isUsableEffectOne() {
        return getEffectsEnable()[0] && getUsableEffect()[0] && ((TargetSquareRequestEvent)getTargetEffectOne()).getPossibleTargetsX().length != 0;
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        Square target = (Square)targets.get(0);
        for (Player p:target.getSquarePlayers()){
            damage(p, 1);
            mark(p, 1);
            getDamagedPlayer().add(p);
        }
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleTargets = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            if (getOwner().getPosition().checkDirection(i) && !getOwner().getPosition().getNextSquare(i).getSquarePlayers().isEmpty())
                possibleTargets.add(getOwner().getPosition().getNextSquare(i));
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    @Override
    public boolean isUsableEffectTwo() {
        return getEffectsEnable()[1] && getUsableEffect()[1] && ((TargetSquareRequestEvent)getTargetEffectOne()).getPossibleTargetsX().length != 0 && getOwner().canAffortCost(getSecondEffectCost());
    }
}
