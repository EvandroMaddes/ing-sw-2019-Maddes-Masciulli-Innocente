package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.model_view_event.ModelViewEvent;

import java.util.Observable;
import java.util.Observer;

public abstract class RemoteView implements RemoteViewInterface{

    private String user;
    private Event toVirtualView;





    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public Event getToVirtualView() {
        return toVirtualView;
    }

    /**
     *
     * @param toServer
     */
    public void setToVirtualView(Event toServer) {
        this.toVirtualView = toServer;
    }

    /**
     * send a messaage to virtual view
     */
    public void toVirtualView(){
       //todo metodi che dal client inviano al server passando il messaggio toVirtualView
   }

    /**
     *
     */
   public void fromVirtualView(){
        //todo riceve un messagio dalla virtual view e chiama metodi coder(?)
   }



}
