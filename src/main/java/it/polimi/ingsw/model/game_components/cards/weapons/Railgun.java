package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class Railgun extends AlternateFireWeapon {

    public Railgun() {
        super(CubeColour.Yellow, "RAILGUN",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{});
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        damage((Player)targets.get(0), 3);
        getDamagedPlayer().add((Player)targets.get(0));
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getCardinalTargets();
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        damage((Player)targets.get(0), 2);
        getDamagedPlayer().add((Player)targets.get(0));
        if (targets.size() > 1) {
            damage((Player) targets.get(1), 2);
            getDamagedPlayer().add((Player)targets.get(1));
        }
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> possibleTargets = getCardinalTargets();
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 2);
    }

    private ArrayList<Player> getCardinalTargets(){
        ArrayList<Player> targets = new ArrayList<>();
        targets.addAll(getOwner().getPosition().getSquarePlayers());
        targets.remove(getOwner());
        for (int direction = 0; direction < 4; direction++){
            Square actualPosition = getOwner().getPosition();
            while (actualPosition.getNextSquare(direction) != null) {
                actualPosition = actualPosition.getNextSquare(direction);
                targets.addAll(actualPosition.getSquarePlayers());
            }
        }
        return  targets;
    }
}



