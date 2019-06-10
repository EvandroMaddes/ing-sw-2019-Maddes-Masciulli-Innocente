package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class WinnerEvent extends ControllerViewEvent {
 private int point;
    public WinnerEvent(String user, int point) {
        super(user);
        this.point = point;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        //todo chiama il metodo della view che gestisce la scelta, quest'ultimo ritornerà un messaggio; da cambiare return
        //todo sarà return remoteView.metodoGiusto();
        return null;
    }
}
