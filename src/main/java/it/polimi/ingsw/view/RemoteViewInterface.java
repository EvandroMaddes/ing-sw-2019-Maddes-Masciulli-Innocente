package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;

public interface RemoteViewInterface {

    public Event getToVirtualView();
    public void setToVirtualView(Event toServer);
    public void toVirtualView();
    public void fromVirtualView();
    public String[] gameInit();

}
