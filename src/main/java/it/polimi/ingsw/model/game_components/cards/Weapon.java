package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.cards.Card;

import java.util.ArrayList;

public class Weapon extends Card {

    private ArrayList<AmmoCube> reloadCost;
    private boolean loaded;

    public void fire()
    {

    }

    public void  isVisible()
    {

    }

    public boolean payCube(ArrayList<AmmoCube> cubeList)
    {
        boolean i=true;
        return i;
    }


}
