package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.Map;

public class PowerUpRequestEvent extends ControllerViewEvent {
    //todo va tolto, si usano gli altri attributi
    Map<String, CubeColour> powerUp;

    private ArrayList<String> powerUpNames;
    private ArrayList<CubeColour> powerUpColours;

    public PowerUpRequestEvent(String user, ArrayList<String> powerUpNames, ArrayList<CubeColour> powerUpColours) {
        super(user);
        this.powerUpNames = powerUpNames;
        this.powerUpColours = powerUpColours;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        //todo chiama il metodo della view che gestisce la scelta, quest'ultimo ritornerà un messaggio; da cambiare return
        //todo sarà return remoteView.metodoGiusto();
        return null;
    }

    public ArrayList<String> getPowerUpNames() {
        return powerUpNames;
    }

    public ArrayList<CubeColour> getPowerUpColours() {
        return powerUpColours;
    }
}
