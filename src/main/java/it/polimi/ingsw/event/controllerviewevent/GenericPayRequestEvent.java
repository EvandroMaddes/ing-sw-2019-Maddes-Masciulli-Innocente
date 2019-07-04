package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the payment of a generic cost
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class GenericPayRequestEvent extends ControllerViewEvent {
    /**
     * Are the ammo that the player can use (Red - Yellow - Blue)
     */
    private boolean[] usableAmmo;
    /**
     * Are the usable powerUps type
     */
    private String[] powerUpsType;
    /**
     * Are the usable powerUps colour
     */
    private CubeColour[] powerUpsColour;

    /**
     * @param user           is the player username
     * @param usableAmmo     are the ammo that the player can use (Red - Yellow - Blue)
     * @param powerUpsType   are the usable powerUps type
     * @param powerUpsColour are the usable powerUps colour
     */
    public GenericPayRequestEvent(String user, boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour) {
        super(user);
        this.usableAmmo = usableAmmo;
        this.powerUpsType = powerUpsType;
        this.powerUpsColour = powerUpsColour;
    }

    /**
     * performAction implementation: ask to the player haw he want to pay
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an GenericPayChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.genericPaymentChoice(usableAmmo, powerUpsType, powerUpsColour);
    }

    /**
     * Getter method
     *
     * @return the usable ammo
     */
    public boolean[] getUsableAmmo() {
        return usableAmmo;
    }

    /**
     * Getter method
     *
     * @return teh usable powerUps type
     */
    public String[] getPowerUpsType() {
        return powerUpsType;
    }

    /**
     * Getter method
     *
     * @return the usable powerUps colours
     */
    public CubeColour[] getPowerUpsColour() {
        return powerUpsColour;
    }
}
