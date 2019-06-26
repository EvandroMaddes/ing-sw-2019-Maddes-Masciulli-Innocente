package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class WinnerEvent extends ControllerViewEvent {
    private int point;
    private boolean draw;

    public WinnerEvent(String user, int point, boolean draw) {
        super(user);
        this.point = point;
        this.draw = draw;
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.winnerUpdate(getUser(),point);
    }

    public int getPoint() {
        return point;
    }

    public boolean isDraw() {
        return draw;
    }
}
