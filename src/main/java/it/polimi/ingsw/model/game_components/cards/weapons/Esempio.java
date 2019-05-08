package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.OneOptionalEffectWeapon;

import java.util.ArrayList;

public class Esempio extends OneOptionalEffectWeapon {
    public Esempio(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost){
        super(colour,name,reloadCost,firstOptionalEffectCost);

    }


    @Override
    public void fire(ArrayList<Player> targets, Square destinazione ,int effettoScelto) {
        switch (scelta){
            case 1: eff1(targets, scelta, destinazione)
        }
    }


    {
        switch(eff)
    }

    public  void eff1();
    public void getTargetEff1;
    public  void effBase;

}
