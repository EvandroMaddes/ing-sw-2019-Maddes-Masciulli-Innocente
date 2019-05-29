package it.polimi.ingsw.event.model_view_event;


import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * represent the updatePlayer updated Position
 */
public class PositionUpdateEvent extends ModelViewEvent {

    private Character currCharacter;
    private int positionX;
    private int positionY;

    /**
     * Constructor
     * @param user the Client user
     * @param positionX  his next position X coordinate
     * @param positionY his next position Y coordinate
     */
    public PositionUpdateEvent(String user, int positionX, int positionY){
        super(user);
        this.positionX=positionX;
        this.positionY=positionY;

    }

    /**
     * serve il setter altrimenti l'attributo character è ereditato anche dalle altre classi
     * @param currCharacter
     */
    public void setCurrCharacter(Character currCharacter) {
        this.currCharacter = currCharacter;
    }

    public Character getCurrCharacter() {
        return currCharacter;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        //todo aggiorna la risorsa sul client

        return remoteView.positionUpdate(getCurrCharacter(),getPositionX(),getPositionY());
    }
}
