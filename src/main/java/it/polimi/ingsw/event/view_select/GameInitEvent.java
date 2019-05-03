package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.board.Map;

import java.util.ArrayList;

public class GameInitEvent extends Event {

    private ArrayList<Map> mapList;
    private String mod;

    public GameInitEvent(String user, ArrayList<Map> avaibleMaps, String mod){
        super(user);
        this.mapList=avaibleMaps;
        this.mod=mod;
    }
}
